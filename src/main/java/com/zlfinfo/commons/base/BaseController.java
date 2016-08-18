package com.zlfinfo.commons.base;

import com.zlfinfo.commons.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/8/18.
 */
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * ajax失败
     * @param msg 失败的信息
     * @return {Object}
     */
    public Object renderError(String msg){
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax 成功
     * @return {object}
     */
    public Object renderSuccess(){
        Result result =new Result();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax 成功
     * @param msg 成功的消息
     * @return {object}
     */
    public Object renderSuccess(String msg){
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax 成功
     * @param obj 成功的对象
     * @return {object}
     */
    public Object renderSuccess(Object obj){
        Result result = new Result();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }

}
