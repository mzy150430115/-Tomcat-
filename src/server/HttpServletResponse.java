package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class HttpServletResponse {
    //自己创建的一个对象
    //目的响应
    private StringBuffer responseContent = new StringBuffer();

    //直接添加响应信息
    public void write(String str){
        this.responseContent.append(str);
    }

    //让response读取文件 文件的内容是响应信息
    public void sendRedirect(String path){

        try {
            File file = new File("src//file//"+path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String value = reader.readLine();
            while (value!=null){
                this.responseContent.append(value);
                value = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getResponseContent(){
        return this.responseContent.toString();
    }
}
