package com.zhupeng.destiny.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/***
 * 
 * ClassName: UserExpiredCheckAnnotation 
 * @Description: 用户登入是否过期的验证
 * @author zhupeng
 * @date 2019年1月15日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface UserExpiredCheckAnnotation {

}
