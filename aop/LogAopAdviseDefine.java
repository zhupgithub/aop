package com.zhupeng.destiny.aop;

import java.util.Map;

import org.apache.ibatis.javassist.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhupeng.destiny.utils.ReflectUtils;

@Component
@Aspect
public class LogAopAdviseDefine {
	private static final Logger logger = LoggerFactory.getLogger(LogAopAdviseDefine.class);
	
	@Pointcut("!execution(* com.zhupeng.destiny.aop.*.*(..)) && execution(* com.zhupeng.destiny..*.*(..))")///!execution(* com.zhupeng.destiny.aop.*.*(..)) and !execution(* com.zhupeng.destiny.utils.*.*(..)) and 
	private void pointcut(){}
	
	
	@Before("pointcut()")
	public void logRecordOnMethodBefore(JoinPoint joinPonit) throws ClassNotFoundException, NotFoundException{
//		String classType = joinPonit.getTarget().getClass().getName(); 
//		Class<?> clazz = Class.forName(classType);  
//        String clazzName = clazz.getName();  
//        String methodName = joinPonit.getSignature().getName(); //获取方法名称 
//        Object[] args = joinPonit.getArgs();//参数
//          //获取参数名称和值
//        Map<String,Object > nameAndArgs = ReflectUtils.getFieldsName(this.getClass(), clazzName, methodName,args); 
		
		logger.info("调用{}方法，参数是：{}",joinPonit.getSignature().toShortString(),joinPonit.getArgs());
	}

	@AfterReturning(pointcut = "pointcut()" , returning = "retVal")
	public void logRecordOnMethodAfter(JoinPoint joinPonit,Object retVal){
		logger.info("调用{}方法，返回数据是：{}",joinPonit.getSignature().toShortString(),retVal);
	}
	
	@AfterThrowing(pointcut = "pointcut()" ,throwing = "e")
	public void logRecordOnMethodException(JoinPoint joinPonit,Exception e){
		logger.info("调用{}方法，发生异常，异常信息是：{}",joinPonit.getSignature().toShortString(),e.getMessage());
	}
	
	
	
}
