package com.huayu.study.java.javaassist;

import javassist.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *  当CtClass对象通过writeFile()、toClass()、toBytecode()转化为Class后，Javassist冻结了CtClass对象，因此，JVM不允许再次加载Class文件，所以不允许对其修改。
 因此，若想对CtClass对象进行修改，必须对其进行解冻，通过defrost()方法进行
 * 动态创建类
 * Created by zhaohuayu on 17/2/16.
 */
public class JavaAssistDemo {


    public static void main(String[] args) throws Exception {
            createDemo1();

    }

    public static void createDemo1() throws Exception {
        //创建类
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.huayu.study.java.javaassist.Demo1");
        //添加name属性及getter,setter
        CtField param = new CtField(classPool.get("java.lang.String"), "name", ctClass) ;
        param.setModifiers(Modifier.PRIVATE);
        ctClass.addMethod(CtNewMethod.setter("setName", param));
        ctClass.addMethod(CtNewMethod.getter("getName", param));
        ctClass.addField(param, CtField.Initializer.constant(""));

        //添加无参构造器
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass) ;
        ctConstructor.setBody("{name=\"defaultName\";}");
        ctClass.addConstructor(ctConstructor);

        //添加一个参数构造器
        ctConstructor = new CtConstructor(new CtClass[]{classPool.get("java.lang.String")},ctClass) ;
        ctConstructor.setBody("{$0.name=$1 ;}");
        ctClass.addConstructor(ctConstructor);

        // 请求当前线程的上下文类加载程序加载由 CtClass 表示的类文件。它返回一个表示已加载类的 java.lang.Class 对象
        // 如果 CtClass 对象由 writeFile ()、toClass () 或 toBytecode () 转换为类文件, Javassist 将冻结该 CtClass 对象。
        // 那 CtClass 对象的进一步修改不被允许后续不允许修改类,除非解冻 ctClass.defrost();
        ctClass.toClass();

        //无参构造方法实例化对象测试
        //getName()测试
        Object o = Class.forName("com.huayu.study.java.javaassist.Demo1").newInstance();
        Method nameGetter = o.getClass().getMethod("getName") ;
        System.out.println(nameGetter.invoke(o));
        //setName()测试
        Method nameSetter = o.getClass().getMethod("setName", new Class[] {String.class}) ;
        nameSetter.invoke(o, "test1") ;
        nameGetter = o.getClass().getMethod("getName") ;
        System.out.println(nameGetter.invoke(o));

        //一个参数构造方法实例化对象测试
        o = Class.forName("com.huayu.study.java.javaassist.Demo1").getConstructor(String.class).newInstance("test2") ;
        nameGetter = o.getClass().getMethod("getName") ;
        System.out.println(nameGetter.invoke(o));
    }


}
