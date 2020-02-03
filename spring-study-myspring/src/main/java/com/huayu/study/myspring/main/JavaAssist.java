package com.huayu.study.myspring.main;

import javassist.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *  当CtClass对象通过writeFile()、toClass()、toBytecode()转化为Class后，Javassist冻结了CtClass对象，因此，JVM不允许再次加载Class文件，所以不允许对其修改。
 因此，若想对CtClass对象进行修改，必须对其进行解冻，通过defrost()方法进行
 * 动态创建类
 * Created by zhaohuayu on 17/2/16.
 */
public class JavaAssist {


    public static void main(String[] args) throws Exception {
            test1();

    }

    public static void test1 () throws Exception {
        //创建类
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.huayu.study.MyTest");
        System.out.println("--------------------");

        //添加name属性及getter,setter
        CtField param = new CtField(classPool.get("java.lang.String"), "name", ctClass) ;
        param.setModifiers(Modifier.PRIVATE);
        ctClass.addMethod(CtNewMethod.setter("setName", param));
        ctClass.addMethod(CtNewMethod.getter("getName", param));
        ctClass.addField(param, CtField.Initializer.constant(""));

        //添加无参构造器
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass) ;
        ctConstructor.setBody("{name=\"testName\";}");
        ctClass.addConstructor(ctConstructor);

        ctConstructor = new CtConstructor(new CtClass[]{classPool.get("java.lang.String")},ctClass) ;
        ctConstructor.setBody("{$0.name=$1 ;}");
        ctClass.addConstructor(ctConstructor);

        System.out.println(ctClass.toClass());//后续不允许修改类,除非解冻 ctClass.defrost();

        Object o = Class.forName("com.huayu.study.MyTest").newInstance();

        Method getter = o.getClass().getMethod("getName") ;

        System.out.println(getter.invoke(o));

        Method setter = o.getClass().getMethod("setName", new Class[] {String.class}) ;

        setter.invoke(o, "test2") ;

        o = Class.forName("com.huayu.study.MyTest").getConstructor(String.class).newInstance("test3") ;
        getter = o.getClass().getMethod("getName") ;
        System.out.println(getter.invoke(o));
    }


}
