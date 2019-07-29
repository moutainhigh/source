package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.ICompanyBalanceWayRepository;
import com.dchip.cloudparking.web.iRepository.ICompanyRepository;
import com.dchip.cloudparking.web.iService.ICompanyManageService;
import com.dchip.cloudparking.web.model.po.Company;
import com.dchip.cloudparking.web.model.po.CompanyBalanceWay;
import com.dchip.cloudparking.web.model.vo.CompanyVo;
import com.dchip.cloudparking.web.po.qpo.QCompany;
import com.dchip.cloudparking.web.po.qpo.QCompanyBalanceWay;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class CompanyManageServiceImp extends BaseService implements ICompanyManageService {
	@Autowired
	public ICompanyRepository companyRepository;
	@Autowired
	public ICompanyBalanceWayRepository companyBalanceWayRepository;

	@Override
	public Object getCompanyManageList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();
		QCompany qCompany = QCompany.company; // 公司
		QCompanyBalanceWay qCompanyBalanceWay = QCompanyBalanceWay.companyBalanceWay;//公司收入账号
		/**
		 * 查询条件的谓语集合(where条件)
		 */
		List<Predicate> predicates = new ArrayList<>();
		if (!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				// 搜索参数
				if (map.get("searchCompanyName") != null) {
					// 公司名称
					predicates.add(qCompany.name.like("%" + map.get("searchCompanyName").toString() + "%"));
				}
			}
		}
		// 动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "id":
			sort = new Sort(sortName, direction.toString(), qCompany);
			break;
		default:
			sort = new Sort(sortName, direction, qCompany);
			break;
		}

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qCompany.id, qCompany.name, qCompany.legalPerson, qCompany.tel,qCompanyBalanceWay.id,qCompanyBalanceWay.balanceWay
						,qCompanyBalanceWay.bankCard,qCompanyBalanceWay.bankHolder,qCompanyBalanceWay.bankName,qCompanyBalanceWay.bankNum,
						qCompanyBalanceWay.companyId,qCompanyBalanceWay.createTime,qCompanyBalanceWay.isFirst)
				.from(qCompany)
				.leftJoin(qCompanyBalanceWay).on(qCompanyBalanceWay.companyId.eq(qCompany.id))
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier())
				.offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qCompany.id));
			map.put("companyName", tuple.get(qCompany.name));
			map.put("legalPerson", tuple.get(qCompany.legalPerson));
			map.put("tel", tuple.get(qCompany.tel));
			map.put("companyBalanceWayId", tuple.get(qCompanyBalanceWay.id));
			map.put("balanceWay", tuple.get(qCompanyBalanceWay.balanceWay));
			map.put("bankCard", tuple.get(qCompanyBalanceWay.bankCard));
			map.put("bankHolder", tuple.get(qCompanyBalanceWay.bankHolder));
			map.put("bankName", tuple.get(qCompanyBalanceWay.bankName));
			map.put("bankNum", tuple.get(qCompanyBalanceWay.bankNum));
			map.put("companyId", tuple.get(qCompanyBalanceWay.companyId));
			map.put("createTime", tuple.get(qCompanyBalanceWay.createTime));
			map.put("isFirst", tuple.get(qCompanyBalanceWay.isFirst));
			data.add(map);
		}

		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	@Override
	public RetKit delete(Integer companyId, Integer companyBalanceWayId) {
		if (companyId == null) {
			return RetKit.fail("公司ID为空，删除失败");
		}else if (companyBalanceWayId == null) {
			return RetKit.fail("银行账号ID为空，删除失败");
		}else {
			companyBalanceWayRepository.deleteById(companyBalanceWayId);
			companyRepository.deleteById(companyId);
			return RetKit.ok("删除成功");
		}
	}

	@Override
	public RetKit save(CompanyVo companyVo) {
		Company company = new Company();
		CompanyBalanceWay companyBalanceWay = new CompanyBalanceWay();
		if (companyVo.getCompanyId() != 0 && companyVo.getCompanyBalanceWayId() != 0) {
			company = companyRepository.findById(companyVo.getCompanyId()).get();
			companyBalanceWay = companyBalanceWayRepository.findById(companyVo.getCompanyBalanceWayId()).get();
		}else {
			companyBalanceWay.setCreateTime(new Date());
		}
		String bankCard = companyVo.getBankCard();
        char[] bankArray = bankCard.toCharArray();
        String bankString = "";
        for(int i=0;i<bankArray.length;i++){
            if(i%4==0 && i>0){
                bankString +=" ";
            }
            bankString += bankArray[i];
        }
		String regex = "(.{4})";
		if (StrKit.notBlank(bankCard)) {
			bankCard = bankCard.replaceAll(regex,"$1\t\t");
		}
		company.setName(companyVo.getCompanyName());
		company.setLegalPerson(companyVo.getLegalPerson());
		company.setTel(companyVo.getTel());
		company = companyRepository.save(company);
		companyBalanceWay.setBalanceWay(companyVo.getBalanceWay());
		companyBalanceWay.setBankCard(bankString);
		companyBalanceWay.setBankHolder(companyVo.getBankHolder());
		companyBalanceWay.setBankName(companyVo.getBankName());
		companyBalanceWay.setBankNum(companyVo.getBankNum());
		companyBalanceWay.setIsFirst(companyVo.getIsFirst());
		companyBalanceWay.setCompanyId(company.getId());
		companyBalanceWayRepository.save(companyBalanceWay);
		return RetKit.ok();
	}
	
	public List<Company> findAllCompany(){
		return companyRepository.findAll();
	}

	@Override
	public Company findByName(String name){
		return companyRepository.findByName(name);
	}

}
