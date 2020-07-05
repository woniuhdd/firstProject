package com.common.utils;

import com.sys.model.SysUser;
import com.sys.service.SysUserManager;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/18.
 */

@Component
public class SessionUtils {


private static SysUserManager sysUserManager;


@Autowired
public void setSysUserManager(SysUserManager sysUserManager){
    SessionUtils.sysUserManager = sysUserManager;
}


    private static ServletRequestAttributes getRequestAttr() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttr().getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static SysUser getSysUser() {
        try{Object o = SecurityUtils.getSubject().getPrincipal();
            String account = (String)o;
            return sysUserManager.getUserByUsername(account) ;
        }catch(ClassCastException e){
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            String account = sysUser.getUsername();
            return sysUserManager.getUserByUsername(account) ;
        }
    }
    public static <T> T getBeanOfType(Class<T> clazz) {
        return getApplication().getBean(clazz);
    }

    private static WebApplicationContext getApplication() {
        ServletContext servletContext = getSession().getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) getApplication().getBean(beanName);
    }

}
