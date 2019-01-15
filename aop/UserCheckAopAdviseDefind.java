package com.zhupeng.destiny.aop;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhupeng.destiny.entity.ResponseResult;
import com.zhupeng.destiny.utils.RedisUtil;
import com.zhupeng.destiny.utils.ReflectUtils;

/**
 * 
 * ClassName: UserCheckAopAdviseDefind 
 * @Description: 判断用户登入是否有效
 * @author zhupeng
 * @date 2019年1月15日
 */
@Component
@Aspect
public class UserCheckAopAdviseDefind {
	private static final Logger logger = LoggerFactory.getLogger(LogAopAdviseDefine.class);
	
	
	@Pointcut("@annotation(com.zhupeng.destiny.annotation.UserExpiredCheckAnnotation)")
	public void pointcut(){}
	
	@Around("pointcut()")
	public Object checkUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		ResponseResult responseResult = new ResponseResult();  
		String classType = proceedingJoinPoint.getTarget().getClass().getName();  
        Class<?> clazz = Class.forName(classType);  
        String clazzName = clazz.getName();  
        String methodName = proceedingJoinPoint.getSignature().getName(); //获取方法名称 
        Object[] args = proceedingJoinPoint.getArgs();//参数
          //获取参数名称和值
        Map<String,Object > nameAndArgs = ReflectUtils.getFieldsName(this.getClass(), clazzName, methodName,args); 
        logger.info("调用{}方法，参数是：{}",methodName,nameAndArgs.toString());
        
		HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		logger.info("请求类型："+request.getContentType()+"   请求方式："+request.getMethod());
		
		String phone = request.getParameter("phone");
		
		phone = RedisUtil.getValueByKey(phone);
		if(phone == null){
			responseResult.setCode(999);
			responseResult.setMessage("亲，请先登入");
			return responseResult;
		}
        Object result =  proceedingJoinPoint.proceed();
        RedisUtil.setValueAndKey(phone, phone,10L,TimeUnit.MINUTES);
        return result;
	}
	
}
