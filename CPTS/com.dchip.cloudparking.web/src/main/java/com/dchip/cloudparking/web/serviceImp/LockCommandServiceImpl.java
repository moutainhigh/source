package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.iRepository.ILockCommandRepository;
import com.dchip.cloudparking.web.iService.ILockCommandService;
import com.dchip.cloudparking.web.model.po.LockCommond;
import com.dchip.cloudparking.web.po.qpo.QLockCommond;
import com.dchip.cloudparking.web.utils.ObjAnalysis;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LockCommandServiceImpl extends BaseService implements ILockCommandService {

    @Autowired
    private ILockCommandRepository lockCommandRepository;

    @Override
    public Object getList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
        List<Map<String, Object>> listData = new ArrayList<>();

        QLockCommond qLockCommond = QLockCommond.lockCommond;

        List<Predicate> predicates = new ArrayList<>();
        if (!para.isEmpty()) {
            for (Map<String, Object> map : para) {
                if (map.get("key") != null) {

                }
            }
        }

        Sort sort = null;
        if(StrKit.notBlank(sortName) && StrKit.notBlank(direction) ){
            sort = new Sort(sortName, direction, qLockCommond);
        }else{
            sort = new Sort("id", "DESC", qLockCommond);
        }

        JPAQuery<LockCommond> jPAQuery = jpaQueryFactory
                .selectDistinct(
                        qLockCommond
                )
                .from(qLockCommond)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier())
                .offset(pageNum * pageSize)
                .limit(pageSize);

        QueryResults<LockCommond> queryResults = jPAQuery.fetchResults();

        for (LockCommond lockCommond : queryResults.getResults()) {
            Map m = ObjAnalysis.ConvertObjToMap(lockCommond);
            listData.add(m);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", listData);
        result.put("totalElements", queryResults.getTotal());
        result.put("code", 0);
        return result;
    }

    @Override
    public void save(LockCommond lockCommond) {
        lockCommandRepository.save(lockCommond);
    }

}
