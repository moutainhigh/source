package com.dchip.cloudparking.web.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthentic implements Serializable,UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4867613903306792830L;

	private Integer accountId ;
	
	private String userName ;
	
	private String password ;
	
	private Integer roleId ;
	
	private String role ;
	
	private Integer roleType ;
	
	private Integer companyId ;
	
	
	private List<String> roleList = new ArrayList<String>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
		 if(this.role != null) {
			 String[] roles = this.role.split(",");  
		        for(String r : roles){  
		            if(r != null && !"".equals(r)){  
		                GrantedAuthority authority = new SimpleGrantedAuthority(r);  
		                authorities.add(authority);
		                roleList.add(r);
		            }  
		        } 
		 }
		 return authorities;
	}



	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}



	public Integer getAccountId() {
		return accountId;
	}



	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Integer getRoleId() {
		return roleId;
	}



	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Integer getRoleType() {
		return roleType;
	}



	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}



	public Integer getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return this.userName;
	}
	
	@Override
	public int hashCode() {
		return userName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}

}
