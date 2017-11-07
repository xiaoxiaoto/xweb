package com.aoto.systemutil.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@Aspect
public class ApplicationAop {

	@Pointcut("execution(* com.aoto..*.controller..*(..))")
	public void defineControllerPointcut() {
	}
	@Pointcut("execution(* com.aoto..*.service..*(..))")
	public void defineServicePointcut() {
	}
	@Pointcut("execution(* com.aoto..*.dao..*(..))")
	public void defineDaoPointcut() {
	}

	@SuppressWarnings({"unused", "rawtypes"})
	@Before("defineControllerPointcut()")
	public void doControllerBeforeAdvice(JoinPoint joinPoint) { 
		
		Object[] args = joinPoint.getArgs();//获取所有参数
		Signature signature = joinPoint.getSignature();
		Class calzz = signature.getDeclaringType();
		
		Annotation[] declaredAnnotations = calzz.getDeclaredAnnotations();
		for(Annotation annotation:declaredAnnotations){
			if(annotation instanceof RestController){
				System.out.println(123);
			}
			System.out.println(annotation.toString());
		}
		Method[] declaredMethods = calzz.getDeclaredMethods();
		for(Method method:declaredMethods){
			Annotation[] declaredMethodAnnotations = method.getDeclaredAnnotations();
			for(Annotation annotation:declaredMethodAnnotations){
				System.out.println(annotation.toString());
			}
		}
		
	}
	
	@Around("defineControllerPointcut()")
	public Object doControllerAroundAdvice(ProceedingJoinPoint  proceedingJoinPoint ) {
		//TODO
		try {
			Object proceed = proceedingJoinPoint.proceed();
			return proceed;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;  
	}
	
	
	@AfterReturning(value="defineControllerPointcut()",returning = "objects")
	public void doControllerAfterReturningAdvice(JoinPoint joinPoint,Object objects) {
	    joinPoint.getClass().getAnnotatedSuperclass();
		//TODO
	}
	@After("defineControllerPointcut()")
	public void doControllerAfterAdvice(JoinPoint joinPoint) {
		//TODO
	}
	@AfterThrowing(value ="defineControllerPointcut()",throwing = "exception")
	public void doControllerAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception) {
		//TODO
	}
	
	
	
	
	
	
	
	
	
	
	@Before("defineServicePointcut()")
	public void doServiceBeforeAdvice(JoinPoint joinPoint) { 
	}
	@Before("defineDaoPointcut()")
	public void doDaoBeforeAdvice(JoinPoint joinPoint) { 
	}

	


}
