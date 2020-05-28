package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //记得将方法定义为public，因为在BaseServlet中，我们为了安全，并没有打破封装
    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(1111);
    }

    //这里有时间在回来整合用户模块的而所有servlet
}
