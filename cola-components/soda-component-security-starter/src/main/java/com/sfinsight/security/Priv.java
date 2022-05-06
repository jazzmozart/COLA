package com.sfinsight.security;

import com.sfinsghts.core.LoginType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wang.qiuyi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD,
    java.lang.annotation.ElementType.TYPE })
@Component
public @interface Priv {

    /**
     * 当前操作是否要求己登录，默认要求己登录。
     * @return
     */
    boolean login() default true;

    /**
     * 当前操作要求的用户类型，默认是前台用户
     * @return
     */
    LoginType loginType() default LoginType.Member;

    /**
     * 当前操作要求用户属性中具有某些值，便如RealName=Test
     * @return
     */
    String userAttr() default "";

    /**
     * 权限类型，由业务系统通过扩展机制来处理
     * @return
     */
    String value() default "";

}
