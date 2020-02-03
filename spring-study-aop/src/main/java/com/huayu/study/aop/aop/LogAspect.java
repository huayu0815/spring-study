package com.huayu.study.aop.aop;

import com.huayu.study.aop.document.Action;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by zhaohuayu on 16/12/27.
 */
@Aspect //切面
@Component //spring管理的bean
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.huayu.study.aop.document.Action)") //声明一个切点
    public void annotationPointCut(){}

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class) ;
        logger.info("注解式拦截:" + method.getName() + ",action Value:" + action.name());

        Object returnObj = null;
        long beginTime = System.currentTimeMillis();
        try{
            returnObj = proceedingJoinPoint.proceed(args);
        } catch (Throwable e) {
            logger.error("timeAround error:", e);
        }
        long endTime = System.currentTimeMillis();

        logger.info("TimeAround,class:{},DeclaringTypeName:{},Time:{}", proceedingJoinPoint.getTarget().getClass(), signature.getDeclaringTypeName(),endTime - beginTime);
        return returnObj;

    }

    @Before("execution(* com.huayu.study.aop.core.DemoDao.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        logger.info("方法规则式拦截:" + method.getName());
    }



    /**
     * xml 配置
     *
     * 定义一个建言
     * 第一个*标识返回值匹配任意类型(public * xxx)匹配public类型的任意返回类型的方法,第二个标识任意方法名,()匹配一个不接受参数的方法,(*)匹配一个任意类型的参数,
     * (*,String)匹配第一个任意类型,第二个为String类型的方法,(..)匹配任意个任意类型的参数
     *
     * XML方式的配置
     *  <bean id="audience" class="com.XinXiangShop.AOP.Audience"/>
     <aop:config>
     <aop:aspect ref="audience">
     <aop:before method="takeSeat" pointcut="execution(* *.perform(..))"/>
     <aop:before method="turnOffPhone" pointcut="execution(* *.perform(..))"/>
     <aop:after-returning method="applaud" pointcut="execution(* *.perform(..))"/>
     <aop:after-throwing method="unHappy" pointcut="execution(* *.perform(..))"/>
     </aop:aspect>
     </aop:config>
     *
     * 利用AOP的事务控制
     * <aop:config>
     <aop:pointcut id="allServiceMethods"
     expression="execution(* com.apress.prospring2.ch16.services.*.*(..))"/>
     <aop:advisor advice-ref="defaultTransactionAdvice"
     pointcut-ref="allServiceMethods"/>
     </aop:config>

     <tx:advice id="defaultTransactionAdvice" transaction-manager="transactionManager">
     <tx:attributes>
     <tx:method
     name="*"
     isolation="DEFAULT"
     propagation="REQUIRED"
     no-rollback-for="java.lang.RuntimeException"
     timeout="100"/>
     <tx:method
     name="get*"
     read-only="true"/>
     </tx:attributes>
     </tx:advice>
     *
     * 声明式事务控制
     * <tx:annotation-driven transaction-manager="transactionManager"/>
     <aop:aspectj-autoproxy />
     *
     */

}
