package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.UserThrid;

@Repository
public interface IUserThridRepository extends JpaRepository<UserThrid, Integer> {

}
