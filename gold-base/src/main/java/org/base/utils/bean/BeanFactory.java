package org.base.utils.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangwr on 2016/6/17.
 */
public class BeanFactory {

    private static Map<String,List<FieldR>> cacheData = new HashMap<String, List<FieldR>>();

    /**
     * 获取指定类的class
     * @param clazz
     * @return
     */
    public static List<FieldR> getFields(Class<?> clazz){
        String key = clazz.getName();
        if(cacheData.containsKey(key)){
            //优先从缓存中取
            return cacheData.get(key);
        }

        return null;
    }

    /**
     * 获取嵌套的属性
     * @return
     */
    public static List<FieldR> getNestedFields(Class<?> clazz){
        List<FieldR> fieldRs = new ArrayList<FieldR>();
        List<Class<?>> nesteds = getNestedClass(clazz);
        for(Class<?> cl : nesteds){
            fieldRs.addAll(getFields(cl));
        }
        return fieldRs;
    }

    /**
     * 获取指定类的所有嵌套类
     * @param clazz 指定类
     * @return 嵌套的所有类
     */
    private static List<Class<?>> getNestedClass(Class<?> clazz){
        List<Class<?>> nesteds = new ArrayList<Class<?>>();
        boolean go = true;
        Class<?> cur = clazz;
        do{
            if(Object.class.equals(cur)){
                go = false;
                continue;
            }
            nesteds.add(cur);
            cur=cur.getSuperclass();
        }while (go);
        return nesteds;
    }
}