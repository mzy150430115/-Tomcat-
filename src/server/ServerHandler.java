package server;

import controller.IndexController;

import java.beans.MethodDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

public class ServerHandler extends Thread{

    private Socket socket;
    public ServerHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        this.receiveRequest();
        //读取消息
        //解析
        //找人做事
        //响应回去
    }
    //读取消息
    private void receiveRequest(){
        try{
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            //读取信息 content?key=value&key=value
            String contentAndParams = reader.readLine();
            //调用一个方法 解析读取的信息
            this.parseContentAndParams(contentAndParams);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //解析
    private void parseContentAndParams(String contentAndParams){
        //创建两个变量 存储请求的资源名 携带的参数
        String  content = null;
        HashMap<String,String> paramsMap = null;
        //content?key=value&key=value
        //寻找问号的位置
        int questionMarkIndex = contentAndParams.indexOf("?");
        //判断是否携带的参数
        if(questionMarkIndex!=-1){
            //携带了参数 截取问号前面的信息-->请求资源名 问号后面的信息拆分存入map集合里
            content =contentAndParams.substring(0,questionMarkIndex);
            paramsMap = new HashMap<String,String >();
            //处理问号后面的参数 拆分存入map集合
            String params = contentAndParams.substring(questionMarkIndex+1);
            String[] keyAndValues = params.split("&");
            for(String keyAndValue:keyAndValues){
               String[] KV =  keyAndValue.split("=");
               paramsMap.put(KV[0],KV[1]);
            }

        }else {
            //没有携带参数 那么请求发送的信息就是完整的资源名
            content = contentAndParams;

        }
        //=================解析完毕==================
        //content-paramsMap

        //我自己创建两个对象 一个包含请求携带的信息
        //另一个为了接受响应回来的结果 创建时是空对象 在cotroller执行完毕后 将其填满
        HttpServletRequest request = new HttpServletRequest(content,paramsMap);
        HttpServletResponse response = new HttpServletResponse();//
        ServletController.findController(request,response);
        //上面的方法执行完毕后，真实的Controller里面的那个service方法执行完了
        //responese对象中的响应信息就会被填满了
        this.responseToBrowser(response);

    }
    //将最终的响应信息写回浏览器
    private void responseToBrowser(HttpServletResponse response){
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(response.getResponseContent());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    }



