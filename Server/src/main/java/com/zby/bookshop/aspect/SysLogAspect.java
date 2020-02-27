//package com.zby.bookshop.aspect;
//
//import com.zby.bookshop.annotation.Log;
//import com.zby.bookshop.pojo.SysLog;
//import com.zby.bookshop.service.SysLogService;
//import com.zby.bookshop.util.IpAdrressUtil;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//
//@Aspect
//@Component
//public class SysLogAspect {
//    @Autowired
//    private SysLogService sysLogService;
//
//    @Pointcut("@annotation(com.zby.bookshop.annotation.Log)")
//    public void logPoinCut() {
//    }
//
//    @AfterReturning("logPointCut()")
//    public void saveSysLog(JoinPoint joinPoint)
//    {
//        SysLog sysLog = new SysLog();
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        Log operation = method.getAnnotation(Log.class);
//        if (operation != null) {
//            String value = operation.value();
//            sysLog.setOperate(value);//保存获取的操作
//        }
//        String className = joinPoint.getTarget().getClass().getName();
//
//        //获取请求的方法名
//        //获取请求的方法名
//        String methodName = method.getName();
//        sysLog.setMethod(className + "." + methodName);
//
//        //获取用户ip地址
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest();
//        sysLog.setIp(IpAdrressUtil.getIpAdrress(request));
//
//        //获取uid
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (!(authentication instanceof AnonymousAuthenticationToken)) {
////            sysLog.setName(authentication.getName());
////        }
//        sysLogService.insert(sysLog);
//
//    }
//
//}
