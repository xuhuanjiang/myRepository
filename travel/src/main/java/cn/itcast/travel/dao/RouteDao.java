package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findCount(String cid,String rname);

    List<Route> findPage(String cid, int start, int pageSize,String rname);

    Route findRouteById(int rid);

    void addFavoriteByRidAndUid(int rid, int uid);

    void addCount(int rid);

}
