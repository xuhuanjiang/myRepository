package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface Route_imgDao {
    List<RouteImg> findImgById(int rid);
}
