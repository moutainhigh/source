package com.dchip.cloudparking.web.serviceImp;


import com.dchip.cloudparking.web.iRepository.IAccountRepository;
import com.dchip.cloudparking.web.iRepository.ICardLicensePlateRepository;
import com.dchip.cloudparking.web.iRepository.ICardRepository;
import com.dchip.cloudparking.web.iRepository.IMonthlyCardRepository;
import com.dchip.cloudparking.web.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.web.iService.IMonthlyCardService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.Card;
import com.dchip.cloudparking.web.model.po.CardLicensePlate;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.CardVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QCard;
import com.dchip.cloudparking.web.po.qpo.QCardLicensePlate;
import com.dchip.cloudparking.web.po.qpo.QMonthlyCardPay;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.utils.DateUtil;
import com.dchip.cloudparking.web.utils.ExcelImportUtils;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class MonthlyCardServiceImp extends BaseService implements IMonthlyCardService {

	@Autowired
	private IMonthlyCardRepository monthlyCardRepository;
	@Autowired
	private ICardRepository cardRepository;
	@Autowired
	private ICardLicensePlateRepository cardLicensePlateRepository;
	@Autowired
	private IAccountRepository accountRepository;
	@Autowired
	private IParkingUserRepository parkingUserRepository;
	
	@Override
	public Object getCardList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		List<Map<String, Object>> listData = new ArrayList<>();
		QCard qCard = QCard.card;
		QCardLicensePlate qCardLicensePlate = QCardLicensePlate.cardLicensePlate;
		QParking qParking = QParking.parking;
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer accountId = user.getAccountId();
		Optional<Account> accountOptional = accountRepository.findById(accountId);
		ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(user.getUsername());

		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("searchParkingName") != null && StrKit.notBlank(map.get("searchParkingName").toString())) {
					predicates.add(qParking.parkName.like("%" + map.get("searchParkingName").toString() + "%"));
				}
				if (map.get("searchUserName") != null && StrKit.notBlank(map.get("searchUserName").toString())) {
					predicates.add(qCard.carOwnerName.like("%" + map.get("searchUserName").toString() + "%"));
				}
				if (map.get("searchCardCode") != null && StrKit.notBlank(map.get("searchCardCode").toString())) {
					predicates.add(qCard.cardCode.like("%" + map.get("searchCardCode").toString() + "%"));
				}
				if (map.get("searchLicensePlate") != null
						&& StrKit.notBlank(map.get("searchLicensePlate").toString())) {
					predicates.add(
							qCardLicensePlate.lisencePlate.like("%" + map.get("searchLicensePlate").toString() + "%"));
				}
				if (map.get("searchExpiryTimeString") != null
						&& StrKit.notBlank(map.get("searchExpiryTimeString").toString())) {
					Date date = DateUtil.dateToStamp(map.get("searchExpiryTimeString").toString(),
							"yyyy-MM-dd HH:mm:ss");
					date.setTime(date.getTime() + 1000);
					predicates.add(qCard.expiryTime.before(date));
				}
			}
		}

		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			if (account.getType() == 2) {
				predicates.add(qParking.companyId.eq(user.getCompanyId()));
			}
		}

		OrderSpecifier<?> orderBy = null;
		if (sortName != null && sortName.equals("cardCode")) {
			if (direction.equalsIgnoreCase("desc")) {
				orderBy = qCard.cardCode.desc();
			} else {
				orderBy = qCard.cardCode.asc();
			}
		} else {
			orderBy = qCard.id.desc();
		}
		// 查询结果
		if(user.getRoleType()==3) {
			predicates.add(qParking.id.eq(parkingUser.getParkingId()));
		}
		
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qCard.id, qCard.parkingId, qParking.parkName, qCard.cardCode,qCardLicensePlate.moreCarType,
						qCardLicensePlate.moreCarLisencePlate,qCardLicensePlate.lisencePlate, qCard.carOwnerName, qCard.expiryTime, 
						qCardLicensePlate.isMain,qCard.max, qCard.type, qCard.remark, qCard.phone, qCard.address,qCard.isFixedSpace)
				.from(qCard, qParking, qCardLicensePlate)
				.where(qCard.parkingId.eq(qParking.id), qCardLicensePlate.cardId.eq(qCard.id))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				// .orderBy(qCard.id.desc())
				.orderBy(orderBy).offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();

		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("cardId", tuple.get(qCard.id));
			map.put("parkingId", tuple.get(qCard.parkingId));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("cardCode", tuple.get(qCard.cardCode));
			map.put("licensePlate", tuple.get(qCardLicensePlate.lisencePlate));
			map.put("carOwnerName", tuple.get(qCard.carOwnerName));
			map.put("expiryTime", tuple.get(qCard.expiryTime));
			map.put("isMain", tuple.get(qCardLicensePlate.isMain));
			map.put("max", tuple.get(qCard.max));
			map.put("type", tuple.get(qCard.type));
			map.put("remark", tuple.get(qCard.remark));
			map.put("phone", tuple.get(qCard.phone));
			map.put("address", tuple.get(qCard.address));
			map.put("isFixedSpace", tuple.get(qCard.isFixedSpace));
			map.put("moreCarType", tuple.get(qCardLicensePlate.moreCarType));
			map.put("moreCarLisencePlate", tuple.get(qCardLicensePlate.moreCarLisencePlate));
			listData.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	@Override
	public Object getMonthlyCardPayList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {

		List<Map<String, Object>> listData = new ArrayList<>();
		QCard qCard = QCard.card;// 月卡
		QCardLicensePlate qCardLicensePlate = QCardLicensePlate.cardLicensePlate;// 月卡车牌绑定表
		QParking qParking = QParking.parking;// 停车场
		QMonthlyCardPay qMonthlyCardPay = QMonthlyCardPay.monthlyCardPay;// 月卡缴费记录表
		List<Predicate> predicates = new ArrayList<>();
        
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ParkingUser parkingUser = parkingUserRepository.findParkingUserByUserName(user.getUsername());
		Integer accountId = user.getAccountId();
		Optional<Account> accountOptional = accountRepository.findById(accountId);
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			if (account.getType() == 2) {
				predicates.add(qParking.companyId.eq(user.getCompanyId()));
			}
		}
		predicates.add(qCard.id.isNotNull());
		if(user.getRoleType()==3) {
			predicates.add(qParking.id.eq(parkingUser.getParkingId()));
		}
		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qMonthlyCardPay.id, qMonthlyCardPay.paymentMoney, qMonthlyCardPay.paymentTime,
						qMonthlyCardPay.paymentWay, qCard.id, qCardLicensePlate.lisencePlate, qCard.carOwnerName,
						qCard.expiryTime, qParking.parkName)
				.from(qMonthlyCardPay).leftJoin(qCard).on(qCard.id.eq(qMonthlyCardPay.monthlyCardId))
				.leftJoin(qCardLicensePlate).on(qCardLicensePlate.cardId.eq(qCard.id)).leftJoin(qParking)
				.on(qParking.id.eq(qCard.parkingId)).where(predicates.toArray(new Predicate[predicates.size()]))
				.groupBy(qMonthlyCardPay.id).orderBy(qCard.expiryTime.desc()).offset(pageNum * pageSize)
				.limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();

		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("paymentMoney", tuple.get(qMonthlyCardPay.paymentMoney));
			map.put("paymentTime", tuple.get(qMonthlyCardPay.paymentTime));
			map.put("paymentWay", tuple.get(qMonthlyCardPay.paymentWay));
			map.put("monthlyCardId", tuple.get(qCard.id));
			map.put("licensePlate", tuple.get(qCardLicensePlate.lisencePlate));
			map.put("carOwnerName", tuple.get(qCard.carOwnerName));
			map.put("expiryTime", tuple.get(qCard.expiryTime));
			map.put("parkName", tuple.get(qParking.parkName));
			listData.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	public RetKit cardImport(String fileName, MultipartFile mfile, Integer parkingId, HttpServletResponse response) {
		// 初始化输入流
		InputStream is = null;
		try {
			File tempFile = null;
			if (mfile.equals("") || mfile.getSize() <= 0) {
				mfile = null;
			} else {
				is = mfile.getInputStream();
				tempFile = new File(mfile.getOriginalFilename());
			}

			// 根据版本选择创建Workbook的方式
			Workbook wb = null;
			// 根据文件名判断文件是2003版本还是2007版本
			if (ExcelImportUtils.isExcel2007(fileName)) {
				wb = WorkbookFactory.create(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			// 根据excel里面的内容读取知识库信息
			return readExcelValue(wb, tempFile, parkingId, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return RetKit.ok();
	}

	/**
	 * 解析Excel里面的数据
	 */
	private RetKit readExcelValue(Workbook wb, File tempFile, Integer parkingId, HttpServletResponse response) {

		// 错误信息接收器
		String errorMsg = "";
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		int totalRows = sheet.getPhysicalNumberOfRows();
		// 总列数
		int totalCells = 0;
		// 得到Excel的列数(前提是有行数)，从第二行算起
		if (totalRows >= 2 && sheet.getRow(1) != null) {
			totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
		}

		if (parkingId == null || parkingId == 0) {
			return RetKit.fail("停车场不存在，导入失败！");
		}

		List<CardVo> list = new LinkedList<>();
		List<Row> errorList = new LinkedList<>();
		Set<String> cardCodeSet = new HashSet<>();
		// 循环Excel行数,从第二行开始。标题不入库
		for (int r = 2; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			CardVo cardVo = new CardVo();
			try {
				cardVo.setParkingId(parkingId);

				try{
					cardVo.setCardCode(row.getCell(0).getStringCellValue().trim());
				}catch (Exception e){
					try{
						cardVo.setCardCode(((long)row.getCell(0).getNumericCellValue())+"");//可以为数字
					}catch (Exception e2){
					}
				}

				cardVo.setLicensePlate(row.getCell(1).getStringCellValue().trim().toUpperCase());
				cardVo.setCarOwnerName(row.getCell(2).getStringCellValue().trim());
				cardVo.setExpiryTime(row.getCell(3).getDateCellValue());
				cardVo.setIsMain(row.getCell(4).getStringCellValue().trim());
				cardVo.setMax((int) row.getCell(5).getNumericCellValue());
				cardVo.setType(row.getCell(6).getStringCellValue().trim());
				if(row.getCell(7) != null ){
					try{
						cardVo.setRemark(row.getCell(7).getStringCellValue().trim());//可以为空
					}catch (Exception e){
						try{
							cardVo.setRemark(row.getCell(7).getNumericCellValue()+"");//可以为数字
						}catch (Exception e2){
						}
					}
				}

				//尝试读取字符串类型 手机号码：某一行第9列
				try{
					cardVo.setPhone(row.getCell(8).getStringCellValue().trim());
				}catch (Exception e){
				}
				//尝试读取长整型类型 手机号码：某一行第9列
				try{
					cardVo.setPhone(""+(long)row.getCell(8).getNumericCellValue());
				}catch (Exception e){
				}
				//地址:某一行第10列
				cardVo.setAddress(row.getCell(9).getStringCellValue().trim());

				//是否固定车位:某一行第11列
				cardVo.setIsFixedSpace(row.getCell(10).getStringCellValue().trim());

				if (StrKit.isBlank(cardVo.getCardCode())) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 1 列，卡编号不能为空，导入失败！");
				}
				if (cardCodeSet.contains(cardVo.getCardCode())) {
					continue;
				}
				cardCodeSet.add(cardVo.getCardCode());
				if (StrKit.isBlank(cardVo.getLicensePlate())) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 2 列，车牌号不能为空，导入失败！");
				}
				if (StrKit.isBlank(cardVo.getCarOwnerName())) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 3 列，车主姓名不能为空，导入失败！");
				}
				if (cardVo.getExpiryTime() == null) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 4 列，到期时间不能为空，导入失败！");
				}
				// if(cardVo.getIsMain() == 1){
				// return RetKit.fail("第 5 列，是否主车牌有误，导入失败！");
				// }
				if (cardVo.getMax() < 1) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 6 列，同组车支持最大数不能小于1，导入失败！");
				}
				if (cardVo.getType() > 3 || cardVo.getType() < 0) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 7 列，卡类型有误，导入失败！");
				}

				if (cardVo.getPhone() == null) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 9 列，手机号为空，导入失败！");
				}
				if (StrKit.isBlank(cardVo.getAddress())) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 10 列，地址为空，导入失败！");
				}
				if (cardVo.getIsFixedSpace() == null) {
					// errorList.add(row);
					// continue;
					return RetKit.fail("第 11 列，是否固定车位为空，导入失败！");
				}
				list.add(cardVo);
			} catch (Exception e) {
				// errorList.add(row);
				// continue;
				return RetKit.fail("第 " + (r+1) + " 行数据有误，导入失败！");
			}
		}

		//将 cardCode 数组拼接起来，一行搞定查询
		List<Map<String, Integer>> cardCodeIdMaps = cardLicensePlateRepository.findCardCodeIdMaps(parkingId, Arrays.asList(cardCodeSet.toArray()));

		//将 cardCode 和 card 表的id一个个对应起来
		Map<String, Integer> cardCodeIdMap = new HashMap<>();
		for (Map<String, Integer> item : cardCodeIdMaps) {
			cardCodeIdMap.put(item.get("cardCode")+"", Integer.parseInt(item.get("id")+""));
		}

		// 循环Excel的列
		for (int i = 0; i < list.size(); i++) {
			CardVo item = list.get(i);
			Card card = new Card();
			CardLicensePlate cardLicensePlate = new CardLicensePlate();

			String cardCode = item.getCardCode();
			if (cardCodeIdMap.get(cardCode) != null) {
				card.setId(cardCodeIdMap.get(cardCode));
			}
			card.setParkingId(parkingId);
			card.setCardCode(cardCode);
			card.setCarOwnerName(item.getCarOwnerName());
			card.setExpiryTime(item.getExpiryTime());
			card.setMax(item.getMax());
			card.setType(item.getType());
			card.setRemark(item.getRemark());
			card.setPhone(item.getPhone());
			card.setAddress(item.getAddress());
			card.setIsFixedSpace(item.getIsFixedSpace());
			card = cardRepository.saveAndFlush(card);

			cardLicensePlate.setCardId(card.getId());
			cardLicensePlate.setIsMain(item.getIsMain());
			cardLicensePlate.setLisencePlate(item.getLicensePlate());
			cardLicensePlateRepository.saveAndFlush(cardLicensePlate);
		}
		// 删除上传的临时文件
		if (tempFile.exists()) {
			tempFile.delete();
		}
		return RetKit.ok("导入成功!");
	}

	@Override
	public RetKit delMonthlyCard(Integer monthlyCardId) {
		if (monthlyCardId == null) {
			return RetKit.fail("月卡ID不能为null!");
		}
		monthlyCardRepository.deleteById(monthlyCardId);
		return RetKit.ok();
	}

	@Override
	public RetKit addCardInfo(CardVo vo,String secondLicensePlate,String thirdLicensePlate) {
		if (vo == null) {
			return RetKit.fail("月卡不能为null!");
		}
		Card card = CardVo.getCardByVo(vo);
		card.setExpiryTime(DateUtil.dateToStamp(vo.getExpiryTimeString(), "yyyy年MM月dd日 HH时mm分ss秒"));
		CardLicensePlate plate = CardVo.getCardLicensePlateByVo(vo,secondLicensePlate,thirdLicensePlate);
		try {
			/**
			 * 验证月卡添加表单
			 */
			if (card.getParkingId() == null || card.getParkingId() == 0) {
				return RetKit.fail("停车场不存在，添加失败！");
			}
			if (card.getCardCode().trim().equals("")) {
				return RetKit.fail("车牌不能为空,添加失败！");
			}
			/**
			 * 名字是可能重复的，但是不能为空
			 */
			if (card.getCarOwnerName().trim().equals("")) {
				return RetKit.fail("车主姓名不能为空，添加失败！");
			}
			if (card.getExpiryTime() == null) {
				return RetKit.fail("到期时间不能为空，添加失败！");
			}
			if (card.getMax() < 1) {
				return RetKit.fail("同组车支持最大数不能小于1，添加失败！");
			}
			if (card.getType() < 1 || card.getType() > 4) {
				return RetKit.fail("卡类型有误，添加失败！");
			}
			Map<String, Object> voMap = cardLicensePlateRepository.findCardVoMap(vo.getParkingId(), vo.getCardCode());
			if (voMap.get("cardId") != null && !voMap.get("cardId").toString().equals("")) {
				if (voMap != null && StrKit.notBlank(voMap.get("parkingId").toString())
						&& vo.getParkingId() == Integer.parseInt(voMap.get("parkingId").toString())) {
					// card.setId(Integer.parseInt(voMap.get("id")+""));
					return RetKit.fail("该停车场月卡已被使用，添加失败！");
				}
			}
			card = cardRepository.saveAndFlush(card);
			plate.setCardId(card.getId());
			cardLicensePlateRepository.save(plate);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail("数据格式有误，添加失败！");
		}
	}

	@Override
	public RetKit editCardInfo(CardVo vo,String secondLicensePlate,String thirdLicensePlate) {
		if (vo == null) {
			return RetKit.fail("月卡不能为null!");
		}
		Card card = CardVo.getCardByVo(vo);
		if (card.getId() == null) {
			return RetKit.fail("月卡id不能为空!");
		}
		card.setExpiryTime(DateUtil.dateToStamp(vo.getExpiryTimeString(), "yyyy年MM月dd日 HH时mm分ss秒"));
		card.setPhone(vo.getPhone());
		card.setAddress(vo.getAddress());
		//CardLicensePlate plate = CardVo.getCardLicensePlateByVo(vo,secondLicensePlate,thirdLicensePlate);
		try {
			Integer plateId = cardLicensePlateRepository.findIdByLicensePlate(vo.getLicensePlate());
			CardLicensePlate cardLicensePlate = cardLicensePlateRepository.findById(plateId).get();
			cardLicensePlate.setMoreCarType(vo.getMoreCarType());
			if (secondLicensePlate != "" || thirdLicensePlate != "") {
				ArrayList<String> moreLicensePlate = new ArrayList<String>();
				moreLicensePlate.add(secondLicensePlate);
				moreLicensePlate.add(thirdLicensePlate);
				cardLicensePlate.setMoreCarLisencePlate(StringUtils.strip(moreLicensePlate.toString(),"[]"));
			}
			//plate.setId(plateId);
			//plate.setCardId(card.getId());
			cardRepository.saveAndFlush(card);
			cardLicensePlateRepository.save(cardLicensePlate);
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	@Override
	public RetKit delCardLicensePlate(Integer cardId, String licensePlate) {
		try {
			List<CardLicensePlate> targetPlates = cardLicensePlateRepository.findByCardId(cardId);
			if (targetPlates.size() == 1) {
				cardLicensePlateRepository.deleteCardLicensePlate(cardId, licensePlate);
				cardRepository.deleteById(targetPlates.get(0).getCardId());
			} else {
				cardLicensePlateRepository.deleteCardLicensePlate(cardId, licensePlate);
				// cardRepository.deleteById(targetPlates.get(0).getCardId());
			}
			return RetKit.ok();
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * @author ZYY 月卡车牌查重
	 */
	@Override
	public RetKit checkCardLicensePlate(String parkingId, String licensePlate) {
		Integer countNum = cardLicensePlateRepository.checkCardLicensePlate(parkingId, licensePlate);
		if (countNum > 0) {
			return RetKit.fail("同一停车场不能添加重复的车牌号码");
		}
		return RetKit.ok();
	}
}
