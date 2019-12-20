package cjx.liyueyun.baselib.base.mvp.okhttp;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author caicai
 * @create 2019/11/25
 * @Describe
 */
public class MyErrorException extends Exception {

    private String erroMsg;
    private int code;

    public MyErrorException(Exception e) {

        if (e instanceof UnknownHostException){//主机无法识别主机的名字或IP地址

        }else if (e instanceof ConnectException){//服务器进程拒绝连接

        }else if (e instanceof SocketTimeoutException){//等待连接超时

        }else if (e instanceof BindException){//无法把Socket对象与指定的本地IP地址或端口绑定

        }else if (e instanceof IOException){

        }

    }

}
