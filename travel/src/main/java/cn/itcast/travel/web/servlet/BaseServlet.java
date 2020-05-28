package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户的模块所传递方法名称
        String uri = req.getRequestURI();
        //uri资源路径标识符。形式应该是  /向虚拟路径/方法名
        String method_name = uri.substring(uri.lastIndexOf("/")+1);
        //System.out.println("method_name = " + method_name);
        //获取字节码文件，这里可以使用this来获取，因为子类调用service的话。this就代表的是该子类

        try {
            Method  method = this.getClass().getMethod(method_name, HttpServletRequest.class, HttpServletResponse.class);
            //执行该方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将bean对象装换为json然后直接响应给服务器
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValue(Object obj ,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),obj);
    }

    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
