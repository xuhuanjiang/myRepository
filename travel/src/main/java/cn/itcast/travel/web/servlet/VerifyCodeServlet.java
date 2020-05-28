package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/verifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先先获取页面的验证码
        String code = request.getParameter("code");
        //在从session中获取存入的code
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        //session.removeAttribute("CHECKCODE_SERVER");
        //创建返回bean对象
        ResultInfo info = new ResultInfo();
        //判定两个code是否相等
        if (code != null && checkcode_server.equalsIgnoreCase(code)){
            //验证码相等
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
        }

        //使用ObjectMapper序列化bean对象，然后返回
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
