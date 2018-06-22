package com.test.quartzdemo.job;


import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author zhouguanya
 * @Date 2018/6/12
 * @Description
 */
public class Person {
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) throws Exception {
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Person.class).getPropertyDescriptors();
            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                System.out.printf("属性名：%s%n", propertyDescriptor.getName());
                System.out.printf("属性类型：%s%n", propertyDescriptor.getPropertyType());
                System.out.printf("属性读方法：%s%n", propertyDescriptor.getReadMethod());
                System.out.printf("属性写方法：%s%n", propertyDescriptor.getWriteMethod());
            }
            //测试setProperty()、getProperty()两个方法
            testProperty();
            //测试setPropertyByIntrospector()、getPropertyByIntrospector()两个方法
            testByIntrospector();
            //测试BeanUtils工具包
            testBeanUtils();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置bean的某个属性值
     * @param obj
     * @param propertyName
     * @param value
     * @throws Exception
     */
    private static void setProperty(Object obj,String propertyName,Object value)throws Exception {
        // 获取bean的某个属性的描述符  两种方式
        PropertyDescriptor pd = new PropertyDescriptor(propertyName,obj.getClass());
        // 获得用于写入属性值的方法
        Method setMethod = pd.getWriteMethod();
        System.out.printf("写入方法的方法名：%s%n", setMethod.getName());
        // 写入属性值
        setMethod.invoke(obj, value);
    }

    /**
     * 获取bean的某个属性值
     * @param obj
     * @param propertyName
     * @return
     * @throws Exception
     */
    private static Object getProperty(Object obj,String propertyName) throws Exception {
        // 获取Bean的某个属性的描述符  两种方式
        PropertyDescriptor pd = new PropertyDescriptor(propertyName,obj.getClass());
        // 获得用于读取属性值的方法
        Method getMethod = pd.getReadMethod();
        System.out.printf("读取方法的方法名：%s%n", getMethod.getName());
        // 读取属性值
        return getMethod.invoke(obj);
    }

    /**
     * 通过内省设置bean的某个属性值
     * @param obj
     * @param propertyName
     * @param value
     * @throws Exception
     */
    public static void setPropertyByIntrospector(Object obj, String propertyName, Object value) throws Exception {
        // 获取bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        // 获取bean的所有属性列表
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        // 遍历属性列表，查找指定的属性
        if (propertyDescriptors != null && propertyDescriptors.length > 0) {
            for (PropertyDescriptor propDesc : propertyDescriptors) {
                // 找到则写入属性值
                if (propDesc.getName().equals(propertyName)) {
                    Method methodSetUserName = propDesc.getWriteMethod();
                    System.out.printf("写入方法的方法名：%s%n", methodSetUserName.getName());
                    // 写入属性值
                    methodSetUserName.invoke(obj, value);
                    break;
                }
            }
        }
    }

    /**
     * 通过内省获取bean的某个属性值
     * @param obj
     * @param propertyName
     * @throws Exception
     */
    public static Object getPropertyByIntrospector(Object obj, String propertyName) throws Exception {
        // 获取bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        // 获取bean的所有属性列表
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors != null && propertyDescriptors.length > 0) {
            for (PropertyDescriptor propDesc : propertyDescriptors) {
                // 找到则读取属性值
                if (propDesc.getName().equals(propertyName)) {
                    Method methodGetUserName = propDesc.getReadMethod();
                    System.out.printf("读取方法的方法名：%s%n", methodGetUserName.getName());
                    // 读取属性值
                    Object objUserName = methodGetUserName.invoke(obj);
                    return objUserName;
                }
            }
        }
        return null;
    }

    /**
     * 测试setProperty()、getProperty()两个方法
     * @throws Exception
     */
    public static void testProperty() throws Exception {
        System.out.println("------------------------------华丽的分割线------------------------------");
        Person person = new Person();
        String propertyName = "name";
        Object nameValue = getProperty(person,propertyName);
        System.out.printf("属性name的值=%s%n", nameValue);
        Object name = "张三";
        setProperty(person, propertyName, name);
        System.out.printf("get()获取属性name的值=%s%n", person.getName());
        System.out.printf("getProperty()获取属性name的值=%s%n", getProperty(person, propertyName));
    }

    /**
     * 测试setPropertyByIntrospector()、getPropertyByIntrospector()两个方法
     * @throws Exception
     */
    public static void testByIntrospector() throws Exception {
        System.out.println("------------------------------华丽的分割线------------------------------");
        Person personOther = new Person();
        String propertyAge = "age";
        Object ageValue = getPropertyByIntrospector(personOther,propertyAge);
        System.out.printf("属性age的值=%s%n", ageValue);
        Object age = 20;
        setPropertyByIntrospector(personOther, propertyAge, age);
        System.out.printf("get()获取属性age的值=%s%n", personOther.getAge());
        System.out.printf("getPropertyByIntrospector()获取属性age的值=%s%n", getPropertyByIntrospector(personOther, propertyAge));
    }

    /**
     * 内省操作非常的繁琐，所以所以Apache开发了一套简单、易用的API来操作Bean的属性——BeanUtils工具包。
     * 下载地址：http://commons.apache.org/beanutils/
     */
    public static void testBeanUtils() {
        System.out.println("------------------------------华丽的分割线------------------------------");
        Person person = new Person();
        try {
            BeanUtils.setProperty(person, "name", "王五");
            System.out.printf("name：%s%n", BeanUtils.getProperty(person, "name"));
            BeanUtils.setProperty(person, "age", 30);
            System.out.printf("age：%s%n", BeanUtils.getProperty(person, "age"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
