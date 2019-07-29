package com.dchip.cloudparking.web.serviceImp;

import com.dchip.cloudparking.web.iRepository.IParkingLotRepository;
import com.dchip.cloudparking.web.iService.IParkingLotService;
import com.dchip.cloudparking.web.model.po.ParkingLot;
import com.dchip.cloudparking.web.po.qpo.QParkingLot;
import com.dchip.cloudparking.web.utils.Sort;
import com.dchip.cloudparking.web.utils.StrKit;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingLotServiceImpl extends BaseService implements IParkingLotService {

    @Autowired
    private IParkingLotRepository parkingLotRepository;

    @Override
    public Object getList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para) {
        List<Map<String, Object>> listData = new ArrayList<>();

        QParkingLot qParkingLot = QParkingLot.parkingLot;

        List<Predicate> predicates = new ArrayList<>();
        if (!para.isEmpty()) {
            for (Map<String, Object> map : para) {
                if (map.get("key") != null) {

                }
            }
        }

        Sort sort = null;
        if(StrKit.notBlank(sortName) && StrKit.notBlank(direction) ){
            sort = new Sort(sortName, direction, qParkingLot);
        }else{
            sort = new Sort("id", "DESC", qParkingLot);
        }

        JPAQuery<Tuple> jPAQuery = jpaQueryFactory
                .selectDistinct(
                        qParkingLot.id,
                        qParkingLot.mainId,
                        qParkingLot.uid,
                        qParkingLot.version,
                        qParkingLot.online,
                        qParkingLot.initState,
                        qParkingLot.smartCustom,
                        qParkingLot.upHeight,
                        qParkingLot.downHeight,
                        qParkingLot.areaId,
                        qParkingLot.number,
                        qParkingLot.status,
                        qParkingLot.state
                )
                .from(qParkingLot)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(sort.getOrderSpecifier())
                .offset(pageNum * pageSize)
                .limit(pageSize);

        QueryResults<Tuple> queryResults = jPAQuery.fetchResults();

        for (Tuple tuple : queryResults.getResults()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tuple.get(qParkingLot.id));
            map.put("mainId", tuple.get(qParkingLot.mainId));
            map.put("uid", tuple.get(qParkingLot.uid));
            map.put("version", tuple.get(qParkingLot.version));
            map.put("online", tuple.get(qParkingLot.online));
            map.put("initState", tuple.get(qParkingLot.initState));
            map.put("smartCustom", tuple.get(qParkingLot.smartCustom));
            map.put("upHeight", tuple.get(qParkingLot.upHeight));
            map.put("downHeight", tuple.get(qParkingLot.downHeight));
            map.put("areaId", tuple.get(qParkingLot.areaId));
            map.put("number", tuple.get(qParkingLot.number));
            map.put("status", tuple.get(qParkingLot.status));
            map.put("state", tuple.get(qParkingLot.state));
            listData.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", listData);
        result.put("totalElements", queryResults.getTotal());
        result.put("code", 0);
        return result;
    }

    @Override
    public void save(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

}
