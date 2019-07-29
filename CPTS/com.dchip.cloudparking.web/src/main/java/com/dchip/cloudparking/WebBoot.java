package com.dchip.cloudparking;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.net.UnknownHostException;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({@ComponentScan("com.**.controller"),@ComponentScan("com.**.serviceImp")})
@EnableJpaRepositories({"com.**.iRepository"})
@EnableRedisRepositories
@EntityScan("com.**.po")
@EnableTransactionManagement
@EnableScheduling
public class WebBoot{

	public static void main(String[] args) {
		SpringApplication.run(WebBoot.class, args);
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectFactory) throws UnknownHostException{
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectFactory);
		return template;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectFactory) throws UnknownHostException{
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectFactory);
		return template;
	}
	
	
	@Bean
    @Autowired
    public JPAQueryFactory jpaQuery(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
	
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
