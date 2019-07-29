package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.FirstWhiteList;

@Repository
public interface IFirstWhiteListRepository extends JpaRepository<FirstWhiteList, Integer>{

    @Query(value = "select * from first_white_list where license_plate_type_id =:licensePlateTypeId limit 1",nativeQuery = true)
    FirstWhiteList findByLicensePlateId(@Param("licensePlateTypeId") Integer licensePlateTypeId);
    
    @Query(value = "select count(*) from first_white_list where license_plate_type_id =:licensePlateTypeId",nativeQuery = true)
    Integer findFirstWhiteList(@Param("licensePlateTypeId") Integer licensePlateTypeId);
}
