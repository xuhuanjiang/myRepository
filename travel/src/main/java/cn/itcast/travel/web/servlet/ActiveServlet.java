package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户点击的验证码
        String code = request.getParameter("code");
        //先判断用户所给的验证码是否为空，
        if (code == null && "".equals(code)){
            response.getWriter().write("激活失败请练习管理员");
            return;
        }

        //验证码不为空时，调用service查询
        UserService service = new UserServiceImpl();
        boolean success = service.active(code);
        //创建响应bean对象
        //ResultInfo info = new ResultInfo();
        if (success){
            response.getWriter().write("激活成功，请"+"<a href='http://localhost/travel/login.html'>登录</a>");
        }else {
            response.getWriter().write("激活失败请练习管理员");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
