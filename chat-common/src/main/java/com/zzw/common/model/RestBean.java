package com.zzw.common.model;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

public record RestBean<T>(int code , T data, String message) {



    public static <T> RestBean<T> success(T data){
        return  new RestBean<>(200,data,"请求成功");
    }
    public static <T> RestBean<T> success(){
        return success(null);
    }
    public static <T> RestBean<T> error(int code,String message){
        return new RestBean<>(code,null,message);
    }

    public static <T> RestBean<T> forbidden(int code){
        return new RestBean<>(code,null,"没有权限");
    }

    public String ToJSON(){
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }

}
