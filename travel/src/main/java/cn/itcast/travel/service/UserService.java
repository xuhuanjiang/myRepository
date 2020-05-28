package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    User checkName(String username);

    boolean register(User user);

    boolean active(String code);

    User login(User user);
}
