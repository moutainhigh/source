package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.CloneCar;

@Repository
public interface ICloneCarRepository extends JpaRepository<CloneCar, Integer>{

}
