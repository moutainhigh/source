package com.dchip.cloudparking.api.iRepository;

import com.dchip.cloudparking.api.model.po.ParkingConcession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IParkingConcessionRepository  extends JpaRepository<ParkingConcession, Integer> {

    /**
     * 会员认领
     * @param parkingConcessionId
     * @param userId
     * @return
     */
    @Query(value = "update `parking_concession` set status = 1,tenant_id=:userId,rent_time=now() where status=0 and id =:parkingConcessionId", nativeQuery = true)
    @Modifying
    Integer accept(@Param("parkingConcessionId") Integer parkingConcessionId, @Param("userId") Integer userId);
    /**
     * 临时车认领
     * @param parkingConcessionId
     * @param licensePlate
     * @return
     */
    @Query(value = "update `parking_concession` set status = 1,tenant_id=:userId,tenant_plate=:licensePlate,rent_time=now() where status=0 and id =:parkingConcessionId", nativeQuery = true)
    @Modifying
    Integer accept(@Param("parkingConcessionId") Integer parkingConcessionId, @Param("userId") Integer userId, @Param("licensePlate") String licensePlate);

    /**
     * 查找我的认领
     */
    @Query(value = "select * from `parking_concession` where tenant_id = :tenantId", nativeQuery = true)
    List<ParkingConcession> queryAcceptList(@Param("tenantId") Integer tenantId);

    /**
     * 查找我的发布
     */
    @Query(value = "select * from `parking_concession` where user_id = :userId", nativeQuery = true)
    List<ParkingConcession> queryPublishList(@Param("userId") Integer userId);

    /**
     * (废弃) 查找附近共享车位的停车场
     * @param minLongi  最小经度
     * @param maxLongi 最大纬度
     * @param minLati 最小纬度
     * @param maxLati 最大纬度
     * @param longitude 当前经度
     * @param latitude 当前纬度
     * @return
     */
    @Query(value = "SELECT\r\n" +
            "	pc.id,\r\n" +
            "	p.park_name AS parkName,\r\n" +
            "	p.id AS parkingId,\r\n" +
            "	pc.effect_during_time AS effectDuringTime,\r\n" +
            "	pc.effect_during_date AS effectDuringDate,\r\n" +
            "	pc.cost,\r\n" +
            "	pc.status as parkingConcessionStatus,\r\n" +
            "	p.STATUS AS parkingStatus,\r\n" +
            "	CAST(p.longitude AS DECIMAL(10, 6)) AS longitude,\r\n" +
            "	CAST(p.latitude AS DECIMAL(10, 6)) AS latitude,\r\n" +
            "	ROUND(\r\n" +
            "		6378.137 * 2 * ASIN(\r\n" +
            "			SQRT(\r\n" +
            "				POW(\r\n" +
            "					SIN((:latitude - latitude) * PI() / 360),\r\n" +
            "					2\r\n" +
            "				) + COS(:latitude * PI() / 180) * COS((latitude + 0.0) * PI() / 180) * POW(\r\n" +
            "					SIN((:longitude - longitude) * PI() / 360),\r\n" +
            "					2\r\n" +
            "				)\r\n" +
            "			)\r\n" +
            "		) * 1000\r\n" +
            "	) AS distance,\r\n "+
            "	p.province_name AS province,\r\n" +
            "	p.city_name AS city,\r\n" +
            "	p.area_name AS area,\r\n" +
            "	p.location,\r\n" +
            "	p.comcat_name AS contactName,\r\n" +
            "	p.comcat_phone AS contactPhone,\r\n" +
            "	fs.free_time_length AS freeTimeLength,\r\n" +
            "	fs.hour_cost AS hourCost,\r\n" +
            "	fs.most_cost AS mostCost,\r\n" +
            "	s.total_count AS totalCarport,\r\n" +
            "	s.enter_count AS enterCount\r\n" +
            " FROM\r\n" +
            "	parking_concession pc\r\n" +
            "LEFT JOIN parking p ON (pc.parking_id = p.id)\r\n" +
            "LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)\r\n" +
            "LEFT JOIN stock s ON (s.parking_id = p.id) " +
            "WHERE\r\n" +
            "	(\r\n" +
            "		p.longitude BETWEEN :minLongi\r\n" +
            "		AND :maxLongi\r\n" +
            "	)\r\n" +
            "AND p.id in (select parking_id from card)\r\n" +//月卡停车场
            "AND (p.latitude BETWEEN :minLati AND :maxLati) ORDER BY distance ASC",nativeQuery = true)
    List<Map<String, Object>> searchNearBy(
            @Param("minLongi")Object minLongi,
            @Param("maxLongi")Object maxLongi,
            @Param("minLati")Object minLati,
            @Param("maxLati")Object maxLati,
            @Param("longitude")Object longitude,
            @Param("latitude")Object latitude);

    /**
     *  包含距离的会员停车场信息列表
     * @param longitude 当前经度
     * @param latitude 当前纬度
     * @return
     */
    @Query(value = "SELECT\r\n" +
            "	pc.id AS parkingConcessionId,\r\n" +
            "	pc.effect_during_time AS effectDuringTime,\r\n" +
            "	pc.effect_during_date AS effectDuringDate,\r\n" +
            "	pc.cost,\r\n" +
            "	p.id AS parkingId,\r\n" +
            "	pc.user_id AS userId,\r\n" +
            "	pc.space_no AS spaceNo,\r\n" +
            "	pc.license_plate AS licensePlate,\r\n" +

            "	p.park_name AS parkName,\r\n" +
            "	p.province_name AS province,\r\n" +
            "	p.city_name AS city,\r\n" +
            "	p.area_name AS area,\r\n" +
            "	p.location,\r\n" +

            "	CAST(p.longitude AS DECIMAL(10, 6)) AS longitude,\r\n" +
            "	CAST(p.latitude AS DECIMAL(10, 6)) AS latitude,\r\n" +
            "	ROUND(\r\n" +
            "		6378.137 * 2 * ASIN(\r\n" +
            "			SQRT(\r\n" +
            "				POW(\r\n" +
            "					SIN((:latitude - latitude) * PI() / 360),\r\n" +
            "					2\r\n" +
            "				) + COS(:latitude * PI() / 180) * COS((latitude + 0.0) * PI() / 180) * POW(\r\n" +
            "					SIN((:longitude - longitude) * PI() / 360),\r\n" +
            "					2\r\n" +
            "				)\r\n" +
            "			)\r\n" +
            "		) * 1000\r\n" +
            "	) AS distance \r\n "+

//            "	fs.free_time_length AS freeTimeLength,\r\n" +
//            "	fs.hour_cost AS hourCost,\r\n" +
//            "	fs.most_cost AS mostCost \r\n" +
            " FROM\r\n" +
            "	parking_concession pc\r\n" +
            "LEFT JOIN parking p ON (pc.parking_id = p.id)\r\n" +
            "LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)\r\n" +
            "LEFT JOIN stock s ON (s.parking_id = p.id) " +
            "WHERE\r\n" +
            "AND s.total_count > s.enter_count\r\n"+
            "AND pc.status = 0\r\n"+
            "AND p.id in (select parking_id from card)\r\n" +//月卡停车场
            "limit (:pageNum) * (:pageSize),(:pageSize)"+
            "ORDER BY distance ASC",nativeQuery = true)
    List<Map<String, Object>> getDistanceList(@Param("pageSize")Integer pageSize,@Param("pageNum")Integer pageNum,@Param("longitude")Object longitude, @Param("latitude")Object latitude);

    @Query(value="select * from parking_concession where parking_id=:parkingId and license_plate=:licensePlate limit 1",nativeQuery = true)
    ParkingConcession findSpace(@Param("parkingId")Integer parkingId,@Param("licensePlate") String licensePlate);

    /**
     *
     * @param keyword
     * @return
     */
    @Query(value = "SELECT\r\n" +
            "	pc.id,\r\n" +
            "	p.park_name AS parkName,\r\n" +
            "	p.id AS parkingId,\r\n" +
            "	pc.effect_during_time AS effectDuringTime,\r\n" +
            "	pc.effect_during_date AS effectDuringDate,\r\n" +
            "	pc.cost,\r\n" +
            "	pc.status as parkingConcessionStatus,\r\n" +
            "	p.STATUS AS parkingStatus,\r\n" +
            "	p.province_name AS province,\r\n" +
            "	p.city_name AS city,\r\n" +
            "	p.area_name AS area,\r\n" +
            "	p.location,\r\n" +
            "	p.comcat_name AS contactName,\r\n" +
            "	p.comcat_phone AS contactPhone,\r\n" +
            "	fs.free_time_length AS freeTimeLength,\r\n" +
            "	fs.hour_cost AS hourCost,\r\n" +
            "	fs.most_cost AS mostCost,\r\n" +
            "	s.total_count AS totalCarport,\r\n" +
            "	s.enter_count AS enterCount\r\n" +
            " FROM\r\n" +
            "	parking_concession pc\r\n" +
            "LEFT JOIN parking p ON (pc.parking_id = p.id)\r\n" +
            "LEFT JOIN free_standard fs ON (fs.id = p.free_standard_id)\r\n" +
            "LEFT JOIN stock s ON (s.parking_id = p.id) " +
            "WHERE p.id in (select parking_id from card)\r\n"+
            "AND p.park_name like CONCAT('%',:keyword,'%')\r\n"+
            "ORDER BY pc.id ASC",nativeQuery = true)
    List<Map<String, Object>> searchParkingSpace(@Param("keyword") String keyword);
    /**
     * 取消租赁
     * @param parkingConcessionId
     * @return
     */
    @Query(value="update `parking_concession` set status = 0,tenant_id=null,rent_time=null where status = 1 and id =:parkingConcessionId",nativeQuery=true)
	Integer changeStatus(@Param("parkingConcessionId")String parkingConcessionId);

    /**
     * 根据车牌获取是否已发布信息
     * @param licensePlate
     * @return
     */
    @Query(value = "select count(id) from parking_concession where license_plate=:licensePlate and parking_id=:parkingId and status !=-2 ",nativeQuery=true)
    Integer getPublishCountByPlate(@Param("licensePlate") String licensePlate,@Param("parkingId")Integer parkingId);

    /**
     * 根据车位获取是否已发布信息
     * @param parkingId
     * @param spaceNo
     * @return
     */
    @Query(value = "select count(id) from parking_concession where parking_id=:parkingId and space_no=:spaceNo and status !=-2 ",nativeQuery=true)
    Integer findParkingSpaceNo(@Param("parkingId")Integer parkingId,@Param("spaceNo") String spaceNo);

    /**
     * 根据车牌查找车位租赁信息
     * @param carNum
     * @return
     */
    @Query(value = "select * from parking_concession where license_plate=:licensePlate and status=0 limit 1",nativeQuery=true)
    ParkingConcession findAcceptMsgByCarNum(@Param("licensePlate")String carNum);

    /**
     * 根据停车场id和用户id查找租赁信息
     * @param parkingId
     * @param tenantId
     * @return
     */
    @Query(value = "select * from parking_concession where parking_id=:parkingId and tenant_id=:tenantId and status=0 limit 1",nativeQuery=true)
    ParkingConcession findAcceptMsg(@Param("parkingId")Integer parkingId,@Param("tenantId") Integer tenantId);

    /**
     * 根据停车场id和用户id查找租赁信息
     * @param parkingId
     * @param tenantPlate
     * @return
     */
    @Query(value = "select * from parking_concession where parking_id=:parkingId and tenant_plate=:tenantPlate and status=1 limit 1",nativeQuery=true)
    ParkingConcession findAcceptMsg(@Param("parkingId")Integer parkingId,@Param("tenantPlate") String tenantPlate);
}
