package server;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
//这个方法目的是为了管理 findController方法
//1.方法与之前服务器的Handler做的事情不一样
//2.每一次寻找Controller类的时候都要参考一下web.properties
//性能较低 加一个缓存
//每一个Controller类都是由findController方法寻找
//找到Controller类的目的是为了执行里面的方法
//让类中的方法有一个统一的规则，便于查找和使用
//4.发现Controller类与之前的service和dao相似，只有方法执行，没有属性
// 让controller类的对象变成单例模式


public class ServletController {
    //添加缓存 存储web.properties
    private static HashMap<String,String> controllerNameMap = new HashMap<>();
    //添加一个集合 存储管理的所有Controller类对象
    private static HashMap<String,HttpServlet> controllerObjectMap = new HashMap<>();
    //创建静态块，在加载类的时候讲配置文件中的信息读取出来放到缓存中
    static {
        try {
            Properties pro = new Properties();
            pro.load(new FileReader("src//web.properties"));
            Enumeration en = pro.propertyNames();
            while (en.hasMoreElements()) {
                String content = (String) en.nextElement();
                String realControllderName = pro.getProperty(content);
                controllerNameMap.put(content, realControllderName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //找人干活---控制层 (cotroller action servlet)
    public static void findController (HttpServletRequest request,HttpServletResponse response){
        //获取request对象 的请求名
        String content = request.getContent();
        try {
            //先去objectMap中寻找对象
            HttpServlet controllerObject = controllerObjectMap.get(content);
            //若对象不存在，证明之前没有使用过
            if(controllerObject==null){
                //参考配置文件真实类名
                String realControllerName = controllerNameMap.get(content);
                //看请求对应的类型是否存在
                if(realControllerName!=null){
                    //反射获取类
                    Class clazz = Class.forName(realControllerName);
                    controllerObject = (HttpServlet) clazz.newInstance();
                    controllerObjectMap.put(content,controllerObject);
                }
            }
            //以上可以保证controllerObject对象肯定存在
            //反射找寻类的方法
            Class controllerClass = controllerObject.getClass();
            Method serviceMethod = controllerClass.getMethod("service", HttpServletRequest.class, HttpServletResponse.class);
            serviceMethod.invoke(controllerObject,request,response);
        } catch (ClassNotFoundException e) {
            response.write("请求的"+content+"不存在");
        } catch (NoSuchMethodException e){
            response.write("405 没有可以执`行的方法");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
