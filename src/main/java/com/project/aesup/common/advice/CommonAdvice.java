package com.project.aesup.common.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

public class CommonAdvice {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable{
		
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		
		if(logger.isInfoEnabled()) {
			logger.info(className + "." + methodName + "() 시작");
		}
		Object[] args = joinPoint.getArgs();
		if((args != null) && (args.length > 0)) {
			for(int i = 0; i < args.length; i++) {
				logger.info("매개변수 [" + i + "] : " + args[i]);
			}
		}
		
		Object retVal = joinPoint.proceed();
		
		if(logger.isInfoEnabled()) {
			logger.info(className + "." + methodName + "() 종료");
		}
		return retVal;
	}
	
	public void afterThrowing(DataAccessException ex) throws Throwable{
		
		if(logger.isInfoEnabled()) {
			logger.info("DataAccessException afterThrowing 시작 : " + ex.getClass().getName() + " 예외 잡힘");
		}
		if(logger.isErrorEnabled()) {
			logger.error(ex.getMessage());
		}
		if(logger.isInfoEnabled()) {
			logger.info("DataAccessException afterThrowing 종료");
		}
		throw ex;
	}
}
