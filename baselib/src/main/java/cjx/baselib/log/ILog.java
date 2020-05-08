package cjx.baselib.log;

public interface ILog {


    void log(String tag,String msg);
    void log(String tag,Object msg);
    String customTag(String tag);
}
