package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Company;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Integer>{

    @Query(value = "select * from company where name = :name limit 1", nativeQuery = true)
    Company findByName(@Param("name") String name);
    
    @Query(value="select c.* from company c where c.id = :companyId", nativeQuery=true)
	Company findCompanyById(@Param("companyId") Integer companyId);
}
