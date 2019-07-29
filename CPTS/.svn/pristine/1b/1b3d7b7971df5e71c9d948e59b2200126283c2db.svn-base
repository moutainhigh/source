package com.dchip.cloudparking.web.utils;


import com.dchip.cloudparking.web.model.po.LockCommond;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjAnalysis {

    public static Map ConvertObjToMap(Object obj){
        Map<String,Object> reMap = new HashMap<String,Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for(int i=0;i<fields.length;i++){
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reMap;
    }

    public static void main(String[] args) {
        LockCommond lockCommond = new LockCommond();
        lockCommond.setCommond("34to34yt3n23ri");
        lockCommond.setGroundLockId(1);
        Map m = ConvertObjToMap(lockCommond);
        System.out.println(m);
    }
}