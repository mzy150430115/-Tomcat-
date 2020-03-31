package controller;

import server.HttpServlet;
import server.HttpServletRequest;
import server.HttpServletResponse;

import java.net.http.HttpResponse;

public class IndexController extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response){
        //1.获取请求发送过来携带的参数
        //2.找到业务层的方法 做事
        //3.将最终的业务层执行完毕的结果 交还给服务器 让服务器写回给浏览器
        //response.write("告诉response对象我的信息在一个文件里 去读");
        response.sendRedirect("index.view");


    }
}
