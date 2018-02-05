package com.ztth.api.path.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Configuration //声明组件
@Aspect //  声明切面
@ComponentScan  //组件自动扫描
@EnableAspectJAutoProxy //spring自动切换JDK动态代理和CGLIB
public class ParameterCheckAspect {
    @Autowired
    private ParameterCheckOption parameterCheckOption;

    // 定义切点
    @Pointcut("within(com.ztth.api.path.rest..*)")
    public void check() {
    }

    /**
     * 切面方法，使用统一异常处理
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "check()", argNames = "Valid")
    public Object checkIsValid(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = null;
        // 参数校验，未抛出异常表示验证OK
        parameterCheckOption.checkValid(joinPoint);
        object = ((ProceedingJoinPoint) joinPoint).proceed();
        return object;
    }

}
