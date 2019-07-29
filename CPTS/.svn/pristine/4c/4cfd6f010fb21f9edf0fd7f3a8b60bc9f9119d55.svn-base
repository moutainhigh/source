package com.dchip.cloudparking.web.serviceImp;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import com.dchip.cloudparking.web.utils.ExcelImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IAccountRepository;
import com.dchip.cloudparking.web.iRepository.ICashApplyRecordRepository;
import com.dchip.cloudparking.web.iRepository.IOrderRepository;
import com.dchip.cloudparking.web.iService.IOrderService;
import com.dchip.cloudparking.web.model.po.Account;
import com.dchip.cloudparking.web.model.po.CashApplyRecord;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.po.qpo.QAppointment;
import com.dchip.cloudparking.web.po.qpo.QCashApplyRecord;
import com.dchip.cloudparking.web.po.qpo.QCompany;
import com.dchip.cloudparking.web.po.qpo.QCompanyBalanceWay;
import com.dchip.cloudparking.web.po.qpo.QOrder;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.po.qpo.QParkingInfo;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class OrderServiceImp extends BaseService implements IOrderService{
	@Autowired
	private IAccountRepository accountRepository;
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private ICashApplyRecordRepository cashApplyRecordRepository;
	
	//财务报表
	@Override
	public Object getOrderList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer accountId = user.getAccountId();
		Optional<Account> accountOptional = accountRepository.findById(accountId);
		
		List<Map<String, Object>> data = new ArrayList<>();	
		QParking qParking = QParking.parking; // 停车场
		QCompany qCompany = QCompany.company; // 公司
		QOrder qOrder = QOrder.order; //缴费帐单
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车信息
		QAppointment qAppointment=QAppointment.appointment;
		
		List<Predicate> predicates = new ArrayList<>();
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			Integer companyId = account.getCompanyId();
			String userName = account.getUserName();
			if (!userName.equals("admin")) {
				predicates.add(qParking.companyId.eq(companyId));
			}
		}
		//车牌号码非空处理//2018-12-20 hrd
//		predicates.add(qParkingInfo.licensePlate.isNotNull());
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchParking") != null) {
					//停车场名称
					predicates.add(qParking.parkName.like("%"+map.get("searchParking").toString()+"%"));
				}else if(map.get("searchStartTime") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String startTime = map.get("searchStartTime").toString();
					try {
						predicates.add(qOrder.payTime.after(sdf.parse(startTime)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else if (map.get("searchEndTime") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String endTime = map.get("searchEndTime").toString();
					try {
						predicates.add(qOrder.payTime.before(sdf.parse(endTime)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else if (map.get("payType") != null) {
					predicates.add(qOrder.type.eq(Integer.parseInt(map.get("payType").toString())));
				}else if (map.get("payStatus") != null) {
					predicates.add(qOrder.status.eq(Integer.parseInt(map.get("payStatus").toString())));
				}else if (map.get("isTransfer") != null) {
					predicates.add(qOrder.isTransfer.eq(Integer.parseInt(map.get("isTransfer").toString())));
				}
			}
		}
		
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "id":		
			sort = new Sort("id",direction.toString(),qOrder);
			break;
		case "companyName":		
			sort = new Sort("name",direction.toString(),qCompany);
			break;
		default:
			sort = new Sort(sortName,direction,qOrder);
			break;
		}
		
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qCompany.name,qParking.parkName,qParkingInfo.licensePlate,qAppointment.licensePlate,qOrder.fee,qOrder.finalFee,qOrder.status,qOrder.type
						,qOrder.payTime,qOrder.isTransfer)
				.from(qOrder)
				.leftJoin(qParkingInfo).on(qParkingInfo.id.eq(qOrder.parkingInfoId))
				.leftJoin(qAppointment).on(qAppointment.id.eq(qOrder.appointmentId))
				.leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode).or(qParking.id.eq(qAppointment.parkingId)))
				.leftJoin(qCompany).on(qCompany.id.eq(qParking.companyId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.limit(pageSize).offset(pageNum*pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyName", tuple.get(qCompany.name));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("parkingInfoLicensePlate", tuple.get(qParkingInfo.licensePlate));
			map.put("appointmentLicensePlate", tuple.get(qAppointment.licensePlate));
			map.put("fee", tuple.get(qOrder.fee));
			map.put("finalFee", tuple.get(qOrder.finalFee));
			map.put("status", tuple.get(qOrder.status));
			map.put("type", tuple.get(qOrder.type));
			map.put("payTime", tuple.get(qOrder.payTime));
			map.put("isTransfer", tuple.get(qOrder.isTransfer));
			data.add(map);
		}	
		//jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);//添加主体数据
		result.put("totalElements", queryResults.getTotal());//添加总条数
		result.put("code", 0);
        return result;
	}
	
	//提现列表
	@Override
	public Object getWithdrawData(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer accountId = user.getAccountId();
		Optional<Account> accountOptional = accountRepository.findById(accountId);
		
		QCashApplyRecord qCashApplyRecord=QCashApplyRecord.cashApplyRecord;
		QCompany qCompany = QCompany.company; 
		QCompanyBalanceWay qCompanyBalanceWay=QCompanyBalanceWay.companyBalanceWay;
		
		List<Predicate> predicates = new ArrayList<>();
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			Integer companyId = account.getCompanyId();
			String userName = account.getUserName();
			if (!userName.equals("admin")) {
				predicates.add(qCashApplyRecord.companyId.eq(companyId));
			}
		}
		
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchCompany")!=null) {
					//搜索公司
					predicates.add(qCompany.name.like("%"+map.get("searchCompany").toString()+"%"));
				}
			}
		}
		
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		sort = new Sort(sortName,direction,qCashApplyRecord);
		
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qCashApplyRecord.id,qCashApplyRecord.free,qCashApplyRecord.moneyStatus,
						qCashApplyRecord.handleTime,qCashApplyRecord.remark,qCashApplyRecord.applyStatus,
						qCashApplyRecord.createTime,qCashApplyRecord.orderIds,qCashApplyRecord.companyId,
						qCompany.name,qCompanyBalanceWay.bankNum,qCompanyBalanceWay.bankHolder)
				.from(qCashApplyRecord)
				.leftJoin(qCompany).on(qCompany.id.eq(qCashApplyRecord.companyId))
				.leftJoin(qCompanyBalanceWay).on(qCompanyBalanceWay.companyId.eq(qCashApplyRecord.companyId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.limit(pageSize).offset(pageNum*pageSize);
		
		List<Tuple> tuples = jPAQuery.fetchResults().getResults();
		
		List<Map<String, Object>> data=new ArrayList<>();
		for (Tuple tuple : tuples) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyName", tuple.get(qCompany.name));
			//银行卡账号
			map.put("bankNum", tuple.get(qCompanyBalanceWay.bankNum));
			map.put("bankHolder", tuple.get(qCompanyBalanceWay.bankHolder));
			map.put("companyId", tuple.get(qCashApplyRecord.companyId));
			map.put("orderIds", tuple.get(qCashApplyRecord.orderIds));
			map.put("createTime", tuple.get(qCashApplyRecord.createTime));
			map.put("applyStatus", tuple.get(qCashApplyRecord.applyStatus));
			map.put("remark", tuple.get(qCashApplyRecord.remark));
			map.put("handleTime", tuple.get(qCashApplyRecord.handleTime));
			map.put("moneyStatus", tuple.get(qCashApplyRecord.moneyStatus));
			map.put("free", tuple.get(qCashApplyRecord.free));
			map.put("id", tuple.get(qCashApplyRecord.id));
			data.add(map);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);//添加主体数据
		result.put("totalElements", jPAQuery.fetchResults().getTotal());//添加总条数
		result.put("code", 0);
        return result;
	}

	@Override
	public RetKit applyWithdrawal(String orderIds,String fee) {
		UserAuthentic userAuthentic = (UserAuthentic)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CashApplyRecord cashApplyRecord = new CashApplyRecord();
		cashApplyRecord.setCompanyId(userAuthentic.getCompanyId());
		cashApplyRecord.setOrderIds(orderIds);
		cashApplyRecord.setFree(new BigDecimal(fee));
		cashApplyRecord.setCreateTime(new Date());
		cashApplyRecord.setApplyStatus(0);
		cashApplyRecord.setMoneyStatus(0);
		cashApplyRecordRepository.save(cashApplyRecord);
		return RetKit.ok();
	}

	@Override
	public RetKit getWithdrawalInfo() {
		return RetKit.okData(getData());
	}
	
	//获取可提现的数据
	@Override
	public Map<String, Object> getData(){
		UserAuthentic userAuthentic = (UserAuthentic)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return orderRepository.getData(userAuthentic.getCompanyId());
	}
	
	@Override
	public RetKit checkWithdraw(String cashApplyRecordId, String moneyStatus, String applyStatus) {
		Optional<CashApplyRecord> optionalCashApplyRecord = cashApplyRecordRepository.findById(Integer.parseInt(cashApplyRecordId));
		if(!optionalCashApplyRecord.isPresent()) {
			return RetKit.fail("找不到数据");
		}
		CashApplyRecord cashApplyRecord = optionalCashApplyRecord.get();
		cashApplyRecord.setHandleTime(new Date());
		if(applyStatus.equals("0")) {
			cashApplyRecord.setApplyStatus(1);
		}else if(moneyStatus.equals("0")){
			cashApplyRecord.setMoneyStatus(1);
		}
		cashApplyRecordRepository.save(cashApplyRecord);
		return RetKit.ok();
	}

	@Override
	public RetKit getParkingIncomeData(String companyId) {
		return RetKit.okData(orderRepository.getOrderData(companyId));
	}

	@Override
	public RetKit getCardIncomeData(String companyId) {
		return RetKit.okData(orderRepository.getCardData(companyId));
	}

	@Override
	public Object getDetail(String orderIds, Integer pageSize, Integer pageNum, String sortName, String direction) {
		List<String> results  = Arrays.asList(orderIds.split(","));
		List<Integer> resultsInt = new ArrayList<Integer>();
		CollectionUtils.collect(results, new Transformer() {
			   @Override
			   public Object transform(Object o) {
			      return Integer.valueOf(o.toString());
			   }
			}, resultsInt);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> data = new ArrayList<>();	
		QParking qParking = QParking.parking; // 停车场
		QCompany qCompany = QCompany.company; // 公司
		QOrder qOrder = QOrder.order; //缴费帐单
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车信息
		QAppointment qAppointment=QAppointment.appointment;

		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "id":		
			sort = new Sort("id",direction.toString(),qOrder);
			break;
		case "payTime":		
			sort = new Sort("payTime",direction.toString(),qOrder);
			break;
		default:
			sort = new Sort(sortName,direction,qOrder);
			break;
		}
		
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qCompany.name,qParking.parkName,qParkingInfo.licensePlate,qAppointment.licensePlate,
						qOrder.fee,qOrder.finalFee,qOrder.status,qOrder.type
						,qOrder.payTime,qOrder.isTransfer)
				.from(qOrder)
				.leftJoin(qParkingInfo).on(qParkingInfo.id.eq(qOrder.parkingInfoId))
				.leftJoin(qAppointment).on(qAppointment.id.eq(qOrder.appointmentId))
				.leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode).or(qParking.id.eq(qAppointment.parkingId)))
				.leftJoin(qCompany).on(qCompany.id.eq(qParking.companyId))
				.where(qOrder.id.in(resultsInt))
				.orderBy(sort.getOrderSpecifier())
				.limit(pageSize).offset(pageNum*pageSize);
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyName", tuple.get(qCompany.name));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("parkingInfoLicensePlate", tuple.get(qParkingInfo.licensePlate));
			map.put("appointmentLicensePlate", tuple.get(qAppointment.licensePlate));
			map.put("fee", tuple.get(qOrder.fee));
			map.put("finalFee", tuple.get(qOrder.finalFee));
			map.put("status", tuple.get(qOrder.status));
			map.put("type", tuple.get(qOrder.type));
			map.put("payTime", tuple.get(qOrder.payTime));
			map.put("isTransfer", tuple.get(qOrder.isTransfer));
			data.add(map);
		}
		
		//jqgrid数据拼装
		result.put("content", data);//添加主体数据
		result.put("totalElements", queryResults.getTotal());//添加总条数
		result.put("code", 0);
		return result;		
	}

	@Override
	public void exportApplyCashExcel(List<Map<String, Object>> para, HttpServletRequest request, HttpServletResponse response){
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer accountId = user.getAccountId();
		Optional<Account> accountOptional = accountRepository.findById(accountId);

		QCashApplyRecord qCashApplyRecord=QCashApplyRecord.cashApplyRecord;
		QCompany qCompany = QCompany.company;
		QCompanyBalanceWay qCompanyBalanceWay=QCompanyBalanceWay.companyBalanceWay;

		List<Predicate> predicates = new ArrayList<>();
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			Integer companyId = account.getCompanyId();
			String userName = account.getUserName();
			if (!userName.equals("admin")) {
				predicates.add(qCashApplyRecord.companyId.eq(companyId));
			}
		} 
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("searchCompany")!=null) {
					//搜索公司
					predicates.add(qCompany.name.like("%"+map.get("searchCompany").toString()+"%"));
				}
			}
		}
//		predicates.add(qCashApplyRecord.applyStatus.eq(1));
//		predicates.add(qCashApplyRecord.moneyStatus.eq(1));
		//动态排序,需要使用自定义的Sort类
		String sortName = "id";
		String direction = "desc";
		Sort sort = new Sort(sortName,direction,qCashApplyRecord);

		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qCashApplyRecord.id,qCashApplyRecord.free,qCashApplyRecord.moneyStatus,
						qCashApplyRecord.handleTime,qCashApplyRecord.remark,qCashApplyRecord.applyStatus,
						qCashApplyRecord.createTime,qCashApplyRecord.orderIds,qCashApplyRecord.companyId,
						qCompany.name,qCompanyBalanceWay.bankNum,qCompanyBalanceWay.bankHolder)
				.from(qCashApplyRecord)
				.leftJoin(qCompany).on(qCompany.id.eq(qCashApplyRecord.companyId))
				.leftJoin(qCompanyBalanceWay).on(qCompanyBalanceWay.companyId.eq(qCashApplyRecord.companyId))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier());

		List<Tuple> tuples = jPAQuery.fetchResults().getResults();

		List<LinkedList<Object>> dataList = new ArrayList<>();
		String sheetName = "提现申请列表";
		String fileName = "提现申请报表";
		String[] headers = { "公司名称", "公司银行卡账号", "银行卡持有人", "提交时间", "申请状态", "处理时间", "资金状态", "提现金额" };
		LinkedList<Object> linkedList;
		for (Tuple tuple : tuples) {
			linkedList = new LinkedList<>();
			linkedList.add(tuple.get(qCompany.name));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankNum));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankHolder));
//			linkedList.add(tuple.get(qCashApplyRecord.companyId));
//			linkedList.add(tuple.get(qCashApplyRecord.orderIds));
			linkedList.add(tuple.get(qCashApplyRecord.createTime));
			linkedList.add(tuple.get(qCashApplyRecord.applyStatus));
//			linkedList.add(tuple.get(qCashApplyRecord.remark));
			linkedList.add(tuple.get(qCashApplyRecord.handleTime));
			linkedList.add(tuple.get(qCashApplyRecord.moneyStatus));
			linkedList.add(tuple.get(qCashApplyRecord.free));
//			linkedList.add(tuple.get(qCashApplyRecord.id));
			dataList.add(linkedList);
		}

		ExcelImportUtils.excelExport(headers, dataList, sheetName, fileName).export(request, response);
	}
	
	@Override
	public void exportExcelCompany(String orderIds, HttpServletRequest request, HttpServletResponse response){
		UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer accountId = user.getAccountId();
		Optional<Account> accountOptional = accountRepository.findById(accountId);

		QCashApplyRecord qCashApplyRecord=QCashApplyRecord.cashApplyRecord;
		QCompany qCompany = QCompany.company;

		QParking qParking = QParking.parking; // 停车场
		QOrder qOrder = QOrder.order; //缴费帐单
		QParkingInfo qParkingInfo = QParkingInfo.parkingInfo;//停车信息
		QAppointment qAppointment=QAppointment.appointment;
		List<String> results  = Arrays.asList(orderIds.split(","));
		List<Integer> resultsInt = new ArrayList<Integer>();
		CollectionUtils.collect(results, new Transformer() {
			   @Override
			   public Object transform(Object o) {
			      return Integer.valueOf(o.toString());
			   }
			}, resultsInt);
		
		List<Predicate> predicates = new ArrayList<>();
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			Integer companyId = account.getCompanyId();
			String userName = account.getUserName();
			if (!userName.equals("admin")) {
				predicates.add(qCashApplyRecord.companyId.eq(companyId));
			}
		}

		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qParking.parkName,qParkingInfo.licensePlate,qOrder.finalFee,qOrder.status,qOrder.type,qOrder.payTime)
				.from(qOrder)
				.leftJoin(qParkingInfo).on(qParkingInfo.id.eq(qOrder.parkingInfoId))
				.leftJoin(qAppointment).on(qAppointment.id.eq(qOrder.appointmentId))
				.leftJoin(qParking).on(qParking.parkCode.eq(qParkingInfo.parkCode).or(qParking.id.eq(qAppointment.parkingId)))
				.leftJoin(qCompany).on(qCompany.id.eq(qParking.companyId))
				.where(qOrder.id.in(resultsInt));
		
		List<Tuple> tuples = jPAQuery.fetchResults().getResults();

		List<LinkedList<Object>> dataList = new ArrayList<>();
		String sheetName = "提现申请列表";
		String fileName = "提现申请报表";
		String[] headers = { "停车场名称", "车牌号码", "支付金额", "支付时间", "支付类型", "支付状态" };
		LinkedList<Object> linkedList;
		for (Tuple tuple : tuples) {
			linkedList = new LinkedList<>();
			String typeName ;
			String statusName;
			//linkedList.add(tuple.get(qCompany.name));
			linkedList.add(tuple.get(qParking.parkName));
			linkedList.add(tuple.get(qParkingInfo.licensePlate));
			//linkedList.add(tuple.get(qAppointment.licensePlate));
			//linkedList.add(tuple.get(qOrder.fee));
			linkedList.add(tuple.get(qOrder.finalFee));
			linkedList.add(tuple.get(qOrder.payTime));
			if(tuple.get(qOrder.type) == 1) {
				typeName = "支付宝";
			}else if(tuple.get(qOrder.type) == 2) {
				typeName = "微信";
			}else{
				typeName = "余额";
			}
			linkedList.add(typeName);
			if(tuple.get(qOrder.status)==0) {
				statusName = "未支付";
			}else if(tuple.get(qOrder.status)==1) {
				statusName = "已支付";
			}else {
				statusName = "支付失败";
			}
			linkedList.add(statusName);
			//linkedList.add(tuple.get(qOrder.isTransfer));
			dataList.add(linkedList);
		}
		

		ExcelImportUtils.excelExport(headers, dataList, sheetName, fileName).export(request, response);
	}

}
