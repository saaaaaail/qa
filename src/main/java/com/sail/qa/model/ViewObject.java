package com.sail.qa.model;

import org.springframework.stereotype.Component;

import javax.net.ssl.SSLEngineResult;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */
@Component
public class ViewObject {
    private Map<String,Object> map = new HashMap<>();

    public Object get(String key){
        return map.get(key);
    }

    public void set(String key,Object value){
        map.put(key,value);
    }
}
