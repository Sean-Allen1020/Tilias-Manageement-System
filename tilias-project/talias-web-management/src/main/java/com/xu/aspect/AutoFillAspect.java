package com.xu.aspect;

import com.xu.exception.UnsupportedOperationTypeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自动填充公共字段
 */
@Aspect
@Component
public class AutoFillAspect {

    // 创建切入点，限制切入位置为mapper下的方法，并限制注解
    @Pointcut("execution(* com..mapper.*.*(..)) && @annotation(com.xu.aspect.AutoFill)")
    public void pointcutAutoFill(){}

    // 切入方法，切入点启动前需要执行
    @Before("pointcutAutoFill()")
    public void autoFill(JoinPoint jp) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // 获取切点方法的元信息
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        // 获取方法上注释值
        String type = method.getAnnotation(AutoFill.class).type();

        // 获取方法运行时的实体类
        Object entity = jp.getArgs()[0];
        // 获取实体类的setter，用以赋值
        Method setCreateTime = entity.getClass().getMethod("setCreateTime", LocalDateTime.class);
        Method setUpdateTime = entity.getClass().getMethod("setUpdateTime", LocalDateTime.class);

        if(type.equals("insert")){
            setCreateTime.invoke(entity, LocalDateTime.now());
            setUpdateTime.invoke(entity, LocalDateTime.now());
        }else if(type.equals("update")){
            setUpdateTime.invoke(entity, LocalDateTime.now());
        }else{
            throw new UnsupportedOperationTypeException(type);
        }
    }
}


