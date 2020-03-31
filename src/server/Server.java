package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void startServer(){

        try {
            System.out.println("=====");
            //自己创建一个服务
            ServerSocket server = new ServerSocket(9999);
            while (true){
                //等待某一个客户端过来链接
                Socket socket = server.accept();
                //启动一个线程 负责处理当前浏览器发送的信息
                new ServerHandler(socket).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
