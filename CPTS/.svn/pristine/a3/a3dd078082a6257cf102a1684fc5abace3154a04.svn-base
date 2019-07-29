package com.dchip.cloudparking;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import com.dchip.cloudparking.web.serviceImp.BCryptPasswordEncoderImpl;
import com.dchip.cloudparking.web.serviceImp.UserDetailServiceImpl;



@Configuration
@EnableWebSecurity
@Order(0)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
    private SessionRegistry sessionRegistry;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoderImpl();
	}
	
	//实现了security用户详情接口的类
	@Bean
	public UserDetailsService getUserDetails() {
		return  new UserDetailServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(getUserDetails());
	    authenticationProvider.setPasswordEncoder(passwordEncoder());
	    return authenticationProvider;
	}
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		//设置security的用户详情
		auth.userDetailsService(getUserDetails());
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		//匿名用户访问无权限资源时的跳转到登录页
		httpSecurity.exceptionHandling().authenticationEntryPoint(new CustomLoginUrlAuthenticationEntryPoint("/login"));
		//session无效的跳转链接
		//httpSecurity.sessionManagement().invalidSessionUrl("/login");
		httpSecurity.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry).expiredUrl("/login");
		//http请求的权限设置
		httpSecurity.authorizeRequests()
			//登录页与资源文件不拦截
			.antMatchers("/","/login","/loginIn","/css/*","/fonts/*","/images/*",
					"/images/loginimgs/*","/js/**","/thirdplugin/**","/sendSocketToUser").permitAll()
			//任何没有匹配的url请求，只需要用户被验证
			.anyRequest().authenticated()
			//任何url都需要csrf跨域保护
			.antMatchers("/**").authenticated().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			//登录页面的路径
			.and().formLogin().loginPage("/login")
			 //退出的跳转路径
			.and().logout().logoutUrl("/login").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
			//允许所有请求通过Http Basic 验证
			.and().httpBasic();

   }
	
	
}
