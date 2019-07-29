package com.dchip.cloudparking.web.serviceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IFinanceRepository;
import com.dchip.cloudparking.web.iService.IFinanceService;
import com.dchip.cloudparking.web.model.po.Finance;
import com.dchip.cloudparking.web.po.qpo.QCompany;
import com.dchip.cloudparking.web.po.qpo.QCompanyBalanceWay;
import com.dchip.cloudparking.web.po.qpo.QFinance;
import com.dchip.cloudparking.web.po.qpo.QParking;
import com.dchip.cloudparking.web.utils.ExcelImportUtils;
import com.dchip.cloudparking.web.utils.RetKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class FinanceServiceImp extends BaseService implements IFinanceService {

	@Autowired
	private IFinanceRepository financeRepository;

	@Override
	public Object getFinanceList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		List<Map<String, Object>> listData = new ArrayList<>();

		QFinance qFinance = QFinance.finance; // 财务
		QParking qParking = QParking.parking; // 停车场
		QCompany qCompany = QCompany.company; // 公司
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;

		QueryResults<Tuple> queryResults = getFinanceListTuple(pageSize, pageNum, sortName, direction, para)
				.fetchResults();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("financeId", tuple.get(qFinance.id));
			
			map.put("chargeTime", sdf.format(tuple.get(qFinance.chargeTime)));
			map.put("totalAmount", tuple.get(qFinance.totalAmount));
			map.put("status", tuple.get(qFinance.status));
			map.put("createTime", tuple.get(qFinance.createTime));
			map.put("name", tuple.get(qCompany.name));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("provinceName", tuple.get(qParking.provinceName));
			map.put("cityName", tuple.get(qParking.cityName));
			map.put("areaName", tuple.get(qParking.areaName));
			map.put("location", tuple.get(qParking.location));
			map.put("calculateDay", tuple.get(qParking.calculateDay));
			map.put("bankCard", tuple.get(qCompanyBalanceWay.bankCard));
			map.put("bankHolder", tuple.get(qCompanyBalanceWay.bankHolder));
			map.put("bankName", tuple.get(qCompanyBalanceWay.bankName));
			map.put("bankNum", tuple.get(qCompanyBalanceWay.bankNum));
			map.put("financeType", tuple.get(qFinance.type));
			listData.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	private JPAQuery<Tuple> getFinanceListTuple(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		QFinance qFinance = QFinance.finance; // 财务
		QParking qParking = QParking.parking; // 停车场
		QCompany qCompany = QCompany.company; // 公司
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;

		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();

		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				// 搜索参数
				if (map.get("searchParking") != null) {
					// 停车场名称
					predicates.add(qParking.parkName.like("%" + map.get("searchParking").toString() + "%"));
				} else if (map.get("searchCompany") != null) {
					predicates.add(qCompany.name.like("%" + map.get("searchCompany").toString() + "%"));
				} else if (map.get("financeType") != null) {
					if (Integer.parseInt(map.get("financeType").toString()) == 1) {
						predicates.add(qFinance.type.eq(BaseConstant.FinanceType.orderType.getTypeValue()));
					} else if (Integer.parseInt(map.get("financeType").toString()) == 2) {
						predicates.add(qFinance.type.eq(BaseConstant.FinanceType.monthlyCardType.getTypeValue()));
					}
				}
			}
		}
		predicates.add(qFinance.status.notIn(BaseConstant.FinanceStatus.trueFlag.getTypeValue()));

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qFinance.id, qFinance.chargeTime, qFinance.status, qFinance.createTime, qCompany.name, qFinance.type,
						qParking.parkName, qParking.provinceName, qParking.cityName, qFinance.totalAmount,
						qParking.cityName, qParking.areaName, qParking.location, qParking.calculateDay,
						qCompanyBalanceWay.bankCard, qCompanyBalanceWay.bankHolder, qCompanyBalanceWay.bankName,
						qCompanyBalanceWay.bankNum)
				.from(qFinance).leftJoin(qCompany).on(qCompany.id.eq(qFinance.companyId)).leftJoin(qParking)
				.on(qParking.id.eq(qFinance.parkingId)).leftJoin(qCompanyBalanceWay)
				.on(qCompanyBalanceWay.id.eq(qFinance.companyBalcanceWayId))
				.where(predicates.toArray(new Predicate[predicates.size()]));
		if (pageSize == null && pageNum == null) {
			return jPAQuery;
		} else {
			return jPAQuery.offset(pageNum * pageSize).limit(pageSize);
		}
	}

	public void financeExcel(List<Map<String, Object>> para, HttpServletRequest request, HttpServletResponse response) {
		List<LinkedList<Object>> dataList = new ArrayList<>();

		QFinance qFinance = QFinance.finance; // 财务
		QParking qParking = QParking.parking; // 停车场
		QCompany qCompany = QCompany.company; // 公司
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;

		QueryResults<Tuple> queryResults = getFinanceListTuple(null, null, null, null, para).fetchResults();

		String[] headers = { "类型", "停车场名称", "年月", "公司名称", "省", "市", "区", "地址", "入账银行卡号", "入账银行持卡人", "入账银行名称", "入账银行编号", "金额",
				"月结日" };
		LinkedList<Object> linkedList;

		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			linkedList = new LinkedList<>();
			if (tuple.get(qFinance.type) == 1) {
				linkedList.add("停车消费");
			} else if (tuple.get(qFinance.type) == 2) {
				linkedList.add("月卡缴费");
			} else {
				linkedList.add("未知");
			}
			linkedList.add(tuple.get(qParking.parkName));
			linkedList.add(tuple.get(qFinance.chargeTime));
			linkedList.add(tuple.get(qCompany.name));
			linkedList.add(tuple.get(qParking.provinceName));
			linkedList.add(tuple.get(qParking.cityName));
			linkedList.add(tuple.get(qParking.areaName));
			linkedList.add(tuple.get(qParking.location));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankCard));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankHolder));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankName));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankNum));
			linkedList.add(tuple.get(qFinance.totalAmount));
			linkedList.add(tuple.get(qParking.calculateDay));
			dataList.add(linkedList);
		}
		String sheetName = "财务列表"; 
		String fileName = "财务报表";
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("financeType") != null) {
					if (Integer.parseInt(map.get("financeType").toString()) == 1) {
						sheetName = "停车消费财务列表";
						fileName = "停车消费财务报表";
					} else if (Integer.parseInt(map.get("financeType").toString()) == 2) {
						sheetName = "月卡缴费财务列表";
						fileName = "月卡缴费财务报表";
					}
				}
			}
		}
		ExcelImportUtils.excelExport(headers, dataList, sheetName, fileName).export(request, response);
	}

	public RetKit changeFinanceStatus(Integer financeId, String calculateName, String calculatePerson, String calculateAccount) {
		Finance finance = financeRepository.findById(financeId).get();
		finance.setStatus(BaseConstant.FinanceStatus.trueFlag.getTypeValue());
		finance.setCalculateAccount(calculateAccount);
		finance.setCalculateName(calculateName);
		finance.setCalculatePerson(calculatePerson);
		return financeRepository.save(finance) != null ? RetKit.ok() : RetKit.fail();
	}

	@Override
	public Object getClearingLogList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {

		List<Map<String, Object>> listData = new ArrayList<>();

		QFinance qFinance = QFinance.finance; // 财务
		QParking qParking = QParking.parking; // 停车场
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;

		QueryResults<Tuple> queryResults = getClearingLogListTuple(pageSize, pageNum, sortName, direction, para)
				.fetchResults();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("chargeTime", sdf.format(tuple.get(qFinance.chargeTime)));
			map.put("createTime", tuple.get(qFinance.createTime));
			map.put("calculateAccount", tuple.get(qFinance.calculateAccount));
			map.put("calculateName", tuple.get(qFinance.calculateName));
			map.put("calculatePerson", tuple.get(qFinance.calculatePerson));
			map.put("payMoney", tuple.get(qFinance.payMoney));
			map.put("payTime", tuple.get(qFinance.payTime));
			map.put("totalAmount", tuple.get(qFinance.totalAmount));
			map.put("payPlatform", tuple.get(qFinance.payPlatform));
			map.put("parkName", tuple.get(qParking.parkName));
			map.put("balanceWay", tuple.get(qCompanyBalanceWay.balanceWay));
			map.put("bankCard", tuple.get(qCompanyBalanceWay.bankCard));
			map.put("bankHolder", tuple.get(qCompanyBalanceWay.bankHolder));
			map.put("bankName", tuple.get(qCompanyBalanceWay.bankName));
			map.put("bankNum", tuple.get(qCompanyBalanceWay.bankNum));
			map.put("financeType", tuple.get(qFinance.type));
			listData.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", listData);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);

		return result;
	}

	private JPAQuery<Tuple> getClearingLogListTuple(Integer pageSize, Integer pageNum, String sortName,
			String direction, List<Map<String, Object>> para) {

		QFinance qFinance = QFinance.finance; // 财务
		QParking qParking = QParking.parking; // 停车场
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;

		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				// 搜索参数
				if (map.get("searchParking") != null) {
					// 停车场名称
					predicates.add(qParking.parkName.like("%" + map.get("searchParking").toString() + "%"));
				} else if (map.get("clearingLogChargeTimeAfter") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
					try {
						Date utilDate = sdf.parse(map.get("clearingLogChargeTimeAfter").toString());
						predicates.add(qFinance.chargeTime.after(utilDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (map.get("clearingLogChargeTimeBefore") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
					try {
						Date utilDate = sdf.parse(map.get("clearingLogChargeTimeBefore").toString());
						predicates.add(qFinance.chargeTime.before(utilDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (map.get("financeType") != null) {
					if (Integer.parseInt(map.get("financeType").toString()) == 1) {
						predicates.add(qFinance.type.eq(BaseConstant.FinanceType.orderType.getTypeValue()));
					} else if (Integer.parseInt(map.get("financeType").toString()) == 2) {
						predicates.add(qFinance.type.eq(BaseConstant.FinanceType.monthlyCardType.getTypeValue()));
					}
				}
			}
		}
		predicates.add(qFinance.status.eq(BaseConstant.FinanceStatus.trueFlag.getTypeValue()));

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.selectDistinct(qFinance.chargeTime, qFinance.createTime, qFinance.calculateAccount, qFinance.type,
						qFinance.calculateName, qFinance.calculatePerson, qFinance.payMoney, qFinance.payTime,
						qFinance.totalAmount, qFinance.payPlatform, qParking.parkName, qCompanyBalanceWay.balanceWay,
						qCompanyBalanceWay.bankCard, qCompanyBalanceWay.bankHolder, qCompanyBalanceWay.bankName,
						qCompanyBalanceWay.bankNum)
				.from(qFinance).leftJoin(qParking).on(qParking.id.eq(qFinance.parkingId)).leftJoin(qCompanyBalanceWay)
				.on(qCompanyBalanceWay.id.eq(qFinance.companyBalcanceWayId))
				.where(predicates.toArray(new Predicate[predicates.size()]));
		if (pageSize == null && pageNum == null) {
			return jPAQuery;
		} else {
			return jPAQuery.offset(pageNum * pageSize).limit(pageSize);
		}

	}

	public void clearingLogExport(List<Map<String, Object>> para, HttpServletRequest request,
			HttpServletResponse response) {
		List<LinkedList<Object>> dataList = new ArrayList<>();

		QFinance qFinance = QFinance.finance; // 财务
		QParking qParking = QParking.parking; // 停车场
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;

		QueryResults<Tuple> queryResults = getClearingLogListTuple(null, null, null, null, para).fetchResults();

		String[] headers = { "类型", "停车场名称", "年月", "结算账户", "结算方", "结算人", "总金额", "已付金额", "付款时间", "入账银行卡号", "入账银行持卡人", "入账银行名称",
				"入账银行编号" };
		LinkedList<Object> linkedList;

		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			linkedList = new LinkedList<>();
			if (tuple.get(qFinance.type) == 1) {
				linkedList.add("停车消费");
			} else if (tuple.get(qFinance.type) == 2) {
				linkedList.add("月卡缴费");
			} else {
				linkedList.add("未知");
			}
			linkedList.add(tuple.get(qParking.parkName));
			linkedList.add(tuple.get(qFinance.chargeTime));
			linkedList.add(tuple.get(qFinance.calculateAccount));
			linkedList.add(tuple.get(qFinance.calculateName));
			linkedList.add(tuple.get(qFinance.calculatePerson));
			linkedList.add(tuple.get(qFinance.totalAmount));
			linkedList.add(tuple.get(qFinance.payMoney));
			linkedList.add(tuple.get(qFinance.payTime));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankCard));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankHolder));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankName));
			linkedList.add(tuple.get(qCompanyBalanceWay.bankNum));
			dataList.add(linkedList);
		}
		String sheetName = "结算日记"; 
		String fileName = "结算日记报表";
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				if (map.get("financeType") != null) {
					if (Integer.parseInt(map.get("financeType").toString()) == 1) {
						sheetName = "停车消费结算日记";
						fileName = "停车消费结算日记报表";
					} else if (Integer.parseInt(map.get("financeType").toString()) == 2) {
						sheetName = "月卡缴费结算日记";
						fileName = "月卡缴费结算日记报表";
					}
				}
			}
		}
		ExcelImportUtils.excelExport(headers, dataList, sheetName, fileName).export(request, response);
	}
}
