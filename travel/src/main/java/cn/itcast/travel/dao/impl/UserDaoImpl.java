package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //创建JDBCTemplate 对象
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByName(String username) {
        String sql = "select username from tab_user where username=?";
        User user = null;
        try {
             user= jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }

        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code =?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
    }

    @Override
    public void updataStatus(int uid) {
        String sql = "update tab_user set status=? where uid=?";
        jdbcTemplate.update(sql,"Y",uid);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username=? and password=?";
        User login_user = null;
        try {
            login_user=jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (Exception e){

        }
        return login_user;
    }
}
