package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名
        String username = request.getParameter("username");
        //调用service的方法查询
        UserService service = new UserServiceImpl();
        User user = service.checkName(username);
        //创建给浏览器响应对象
        ResultInfo info = new ResultInfo();
        //判定查询结果是否有值
        if (user == null){
            info.setFlag(true);
            info.setErrorMsg("用户名可以使用");
        }else {
            info.setFlag(false);
            info.setErrorMsg("用户名重复");
        }

        //将响应Bean对象序列化为json，然后响应
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
