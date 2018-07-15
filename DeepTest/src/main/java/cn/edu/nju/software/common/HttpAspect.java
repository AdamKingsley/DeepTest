package cn.edu.nju.software.common;

import cn.edu.nju.software.common.exception.ExceptionHandle;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class HttpAspect {
    private Long time;
    @Autowired
    private ExceptionHandle exceptionHandle;

    @Pointcut("execution(public * cn.edu.nju.software.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        time = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        log.info("url={}", request.getRequestURL());
        //method
        log.info("method={}", request.getMethod());
        //ip
        log.info("ip={}", request.getRemoteAddr());
        //class_method
        //log.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        log.info("args={}", joinPoint.getArgs());
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            return exceptionHandle.exceptionGet(e);
        }
    }

    @AfterReturning(pointcut = "log()", returning = "object")//打印输出结果
    public void doAfterReturing(Object object) {
        log.info("this request costs " + (System.currentTimeMillis() - time) + "millis");
        log.info("response={}", object.toString());
    }
}
