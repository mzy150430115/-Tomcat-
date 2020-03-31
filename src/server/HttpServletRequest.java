package server;

import java.util.HashMap;

public class HttpServletRequest {
    //是自己创建一个类型
    //目的存储浏览器发送请求时携带的所有信息
    private String content;
    private HashMap<String,String> paramsMap;

    public HttpServletRequest() {
    }

    public HttpServletRequest(String content, HashMap<String, String> paramsMap) {
        this.content = content;
        this.paramsMap = paramsMap;
    }

    public String getParameter(String key){
        return this.paramsMap.get(key);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParamsMap(HashMap<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }
}
