package server;

import java.net.http.HttpResponse;

//定义规则
//方法名字必须统一 服务器寻找的时候就方便了
//方法的参数必须统一 request response
public abstract class HttpServlet {

    public abstract void service(HttpServletRequest request, HttpServletResponse response);


}
