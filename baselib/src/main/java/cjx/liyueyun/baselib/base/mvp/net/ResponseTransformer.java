package cjx.liyueyun.baselib.base.mvp.net;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import cjx.liyueyun.baselib.base.mvp.net.mInterface.ITransformer;

public class ResponseTransformer<T> implements ITransformer<T> {

    @Override
    public  T transform(String  response) {

        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) response;
        }
        // TODO: 2020/5/6 Gson可以用一个全局唯一的，不用每次new
        T bean = new Gson().fromJson(response, entityClass);

        return bean;
    }
}
