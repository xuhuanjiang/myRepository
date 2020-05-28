package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将验证码删除
        HttpSession session = request.getSession();
        if(session.getAttribute("CHECKCODE_SERVER") != null){
            session.removeAttribute("CHECKCODE_SERVER");
        }

        session.removeAttribute("CHECKCODE_SERVER");
        //获取参数列表
        Map<String, String[]> map = request.getParameterMap();
        //用工具进行封装
        User user  = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service查询用户表单，返回一个查询到的用户
        UserService service = new UserServiceImpl();
        User login_user = service.login(user);
        ResultInfo info = new ResultInfo();
        //判定user的状态
        if(login_user != null){
            if ("Y".equalsIgnoreCase(login_user.getStatus())){
                //将user存入session中
                info.setFlag(true);
                session.setAttribute("user",login_user);
            }else {
                info.setFlag(false);
                info.setErrorMsg("您尚未激活，请激活后在登录");
            }
        }else{
            info.setFlag(false);
            info.setErrorMsg("登录失败，用户名或者密码错误");
        }

        //序列化为json然后返回
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
