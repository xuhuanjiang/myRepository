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

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //程序执行这个servlet证明验证码失效需要删除
        //将验证码删除
        HttpSession session = request.getSession();
        if(session.getAttribute("CHECKCODE_SERVER") != null){
            session.removeAttribute("CHECKCODE_SERVER");
        }

        //这里还是需要在判定一下验证码

        //获取参数集合
        Map<String, String[]> map = request.getParameterMap();
        //System.out.println("map = " + map);
        //使用BeanUtils封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service的方法进行注册，返回一个boolean值
        UserService  service = new UserServiceImpl();
        boolean flag_register = service.register(user);

        //创建返回bean对象并根据条件设置参数
        ResultInfo info = new ResultInfo();
        info.setFlag(flag_register);
        if (!flag_register){
            info.setErrorMsg("注册失败");
        }

        //使用ObjectMapper序列化info成json
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
