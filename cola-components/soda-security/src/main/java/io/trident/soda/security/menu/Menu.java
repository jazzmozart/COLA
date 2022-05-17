package io.trident.soda.security.menu;

import io.trident.soda.core.LoginType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wang.qiuyi
 * 用于实现 @see com.sfinsight.security.menu.AbstractMenu 类之上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Component
public @interface Menu {

    /**
     * 当前操作要求的用户类型，默认是前台用户
     * @return
     */
    LoginType loginType() default LoginType.Member;

    String bizID() default "";
}
