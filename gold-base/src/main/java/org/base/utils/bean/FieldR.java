package org.base.utils.bean;

import java.lang.reflect.Field;

/**
 * Created by wangwr on 2016/6/17.
 */
public class FieldR {

    private Field field;

    public FieldR(Field field){
        this.field=field;
    }

    /**
     * 设置字段值（优先使用java自省机制设置字段值
     * 当实体bean不提供set方法时直接调用字段赋值）
     * @param obj 需要赋值对象
     * @param value 属性值
     */
    public void setValue(Object obj,Object value){

    }

    /**
     * 获取字段的值
     */
    public Object getValue(){
        return null;
    }

}
