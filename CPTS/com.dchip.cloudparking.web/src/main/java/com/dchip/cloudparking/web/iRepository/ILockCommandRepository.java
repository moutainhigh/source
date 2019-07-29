package com.dchip.cloudparking.web.iRepository;

import com.dchip.cloudparking.web.model.po.LockCommond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILockCommandRepository extends JpaRepository<LockCommond, Integer>{

}
