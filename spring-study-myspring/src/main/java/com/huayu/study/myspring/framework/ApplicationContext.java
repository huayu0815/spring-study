package com.huayu.study.myspring.framework;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhaohuayu on 16/12/25.
 */
public class ApplicationContext {

    private static Map<String,Object> application ;

    public static void init(String fileName) throws Exception {
        //单例
        if (application == null) {
            //模拟加载配置文件
            URL url = ApplicationContext.class.getClassLoader().getResource(fileName) ;
            Yaml yaml = new Yaml() ;
            Iterable<Object> iocYaml = yaml.loadAll(new FileInputStream(url.getFile())) ;

            //通过反射生成对象并初始化
            application = new HashMap<String, Object>() ;
            for(Object obj: iocYaml) {
                Map<String,Object> resourceMap = (Map)obj;
                Class targetCls = Class.forName((String)resourceMap.get("class")) ;
                Object targetObj = targetCls.newInstance() ;

                Map<String,Object> keys = (Map)resourceMap.get("keys") ;
                for(String key: keys.keySet()) {
                    setProperty(targetObj,key, keys.get(key));
                }

                application.put((String)resourceMap.get("bean"), targetObj) ;
            }

        }

    }

    public static Object getBean(String name) {
        if (application == null) {
            return null ;
        } else {
            return application.get(name) ;
        }
    }

    /**
     * 给对象的属性赋值
     * @param object
     * @param name
     * @param value
     */
    public static void setProperty(Object object, String name, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class objectClass = object.getClass() ;
        //获取指定的字段
        Field field = objectClass.getDeclaredField(name) ;

        //byFieldName
        setPropertyByField(object, value, field);
        //byMethodName
        //setPropertyByMethod(object, name, value, objectClass);



    }

    private static void setPropertyByMethod(Object object, String name, Object value, Class objectClass) throws IllegalAccessException, InvocationTargetException {
        String methodName  = getSetMethodName(name) ;
        //获取所有方法
        Method[] methods = objectClass.getMethods();
        for (Method method: methods) {
            /**
             * 只有一个参数,并且方法名为setName的,调用setName(value)方法赋值
             */
            if (method.getParameterTypes().length == 1) {
                //获取指定的参数类型
                //Class parameterTypeClass = method.getParameterTypes()[0] ;
                if (method.getName().equals(methodName)) {
                    //打破封装(private也可以调用)
                    method.setAccessible(true);
                    //方法调用
                    method.invoke(object,value) ;
                    break ;
                }
            }
        }
    }

    private static void setPropertyByField(Object object, Object value, Field field) throws IllegalAccessException {
        //打破封装(private也可以调用)
        field.setAccessible(true);
        //字段赋值
        field.set(object, value);
    }

    /**
     * 根据属性名得到set方法名
     * @param name
     * @return
     */
    private static String getSetMethodName(String name) {
        char firstChar = name.charAt(0) ;
        if (firstChar <= 'z' && firstChar >= 'a') {
            firstChar -=32 ;
        }
        return "set" + firstChar + name.substring(1) ;
    }

}
