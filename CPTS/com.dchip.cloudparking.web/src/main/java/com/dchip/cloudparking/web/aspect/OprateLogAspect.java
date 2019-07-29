package com.dchip.cloudparking.web.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iRepository.IDailyRecordRepository;
import com.dchip.cloudparking.web.iRepository.IMenuRepository;
import com.dchip.cloudparking.web.model.po.DailyRecord;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.RetKit;

/**
 * 操作日志切面类
 * @author d-chip
 *
 */
@Aspect
@Component
public class OprateLogAspect {
	private static final Logger log = LoggerFactory.getLogger(OprateLogAspect.class);

	@Autowired
	private IDailyRecordRepository dailyRecordRepository;
	@Autowired 
	private IMenuRepository menuRepository;
	
	@Pointcut( "@annotation(org.springframework.web.bind.annotation.ResponseBody)  "
			 + " && execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.*.*(..)) "
			 + " && !execution(public * com.dchip.cloudparking.web.controller.IndexController.*(..)) "
			 + " && !execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.UserController.getChartData()) "
			 + " && !execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.EquipmentController.getRoadways(..)) "
			 + " && !execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.AccountController.getParking(..)) "
			 + " && !execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.*.check*(..)) "
			 + " && !execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.*.query*(..)) "
			 + " && !execution(public com.dchip.cloudparking.web.utils.RetKit com.dchip.cloudparking.web.controller.CloneCarController.findCloneCarDetailInfo(..)) "
			)
    public void controllerPointCut(){}  
	
	@AfterReturning(returning="returnValue",pointcut="controllerPointCut()") 
	public void afterReturning(JoinPoint jp ,Object returnValue){  
	    RetKit retKit = (RetKit)returnValue;
    	if(retKit.success()) {
        	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  	
        	HttpServletRequest request = requestAttributes.getRequest();
        	
            MethodSignature signature = (MethodSignature) jp.getSignature();  
            Method method = signature.getMethod(); //获取被拦截的方法  
            String methodName = method.getName(); //获取被拦截的方法名        
        	
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserAuthentic userAuthentic =(UserAuthentic)authentication.getPrincipal();
            String[] urlSplit = request.getRequestURL().toString().split("/");
            String menuName = menuRepository.findByUrl(urlSplit[urlSplit.length-2]).getMenuName();
            Object[] args =jp.getArgs();
            Integer actType =null;
            
            if(methodName.contains("add")){//1-添加
            	actType=BaseConstant.DailyRecordType.Add.getTypeValue();            	
            }else if(methodName.contains("delete")) {//2-删除
            	actType=BaseConstant.DailyRecordType.Delete.getTypeValue();            	
            }else if(methodName.contains("change")) {//4-禁用
            	actType=BaseConstant.DailyRecordType.UnEnabled.getTypeValue();
            }else if(methodName.contains("enabled")) {//5-启用
            	actType=BaseConstant.DailyRecordType.Enabled.getTypeValue();
            }else if(methodName.contains("saveSettingRule")){//6-保存规则设置操作
            	actType=BaseConstant.DailyRecordType.SettingRule.getTypeValue();            	
            }else if (methodName.contains("PassWord")) {//7-修改密码
				actType = BaseConstant.DailyRecordType.updatePassWord.getTypeValue();
			}else if(methodName.contains("Import")) {//8-导入月卡信息
            	actType=BaseConstant.DailyRecordType.Import.getTypeValue();
            }else if(methodName.contains("pass")){//9-审核通过
            	actType=BaseConstant.DailyRecordType.pass.getTypeValue();            	
            }else if(methodName.contains("notPass")){//10-审核不通过
            	actType=BaseConstant.DailyRecordType.notPass.getTypeValue();            	
            }else if(methodName.contains("notify")){//11-提示更新
            	actType = BaseConstant.DailyRecordType.Notify.getTypeValue();
			}else if (methodName.equals("applyWithdrawal")) {//12-申请提现
				actType = BaseConstant.DailyRecordType.Apply.getTypeValue();
			}else if (methodName.contains("transferAccounts")) {//13-确认转账
				actType = BaseConstant.DailyRecordType.TransferAccounts.getTypeValue();
			}else if (methodName.contains("agree")) {//14-同意申请
				actType = BaseConstant.DailyRecordType.AgreeToApply.getTypeValue();
			}else {//3-编辑
            	actType = BaseConstant.DailyRecordType.Edit.getTypeValue();
            }
	            
	            log.info("===============请求内容===============");
	        	log.info("业务名：{}",menuName);
	            log.info("用户名：{},用户id：{}",userAuthentic.getUserName(),userAuthentic.getAccountId()); 
	            log.info("请求的方法名：{},",methodName); 
	            log.info("请求地址:"+request.getRequestURL().toString());
	            log.info("请求方法:"+request.getMethod());
	            log.info("请求的方法:"+jp.getSignature());
	            for (Object object : args) {
	            	log.info("请求的参数:{}",object.toString());
	    		}
	            log.info("===============请求内容===============");
	            
	            DailyRecord dailyRecord = new DailyRecord();
	            try {
	            	dailyRecord.setAccountId(userAuthentic.getAccountId());
	    			dailyRecord.setActTime(new Date());
	    			dailyRecord.setActName( menuName );
	    			dailyRecord.setActType(actType);
	    			dailyRecordRepository.save(dailyRecord);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	}
	    } 
}
