package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public User checkName(String username) {
        return dao.findByName(username);
    }

    @Override
    public boolean register(User user) {
        //这里应该在将用户名再此进行校验的，但是这里就不在进行了
        //这里最好需要再次验证用户名和密码，因为可能存在没刷新页面用户就再次提交的情况

        //先设置激活状态和，激活码
        user.setStatus("N");
        //获取激活码
        user.setCode(UuidUtil.getUuid());
        //调用dao的save方法进行注册
        dao.save(user);
        //注册完成后需要发邮件给客户点击验证
        String content = "<a href='http://localhost/travel/activeServlet?code="+user.getCode()+"'>点击验证，黑马旅游网</a>";
        MailUtils.sendMail(user.getEmail(),content,"验证邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //首先先通过用户的code查询数据库的
        User user = dao.findByCode(code);
        if (user != null ){
            dao.updataStatus(user.getUid());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User login(User user) {
        //调用dao的方法查询
        User login_user = dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
        return login_user;
    }
}
