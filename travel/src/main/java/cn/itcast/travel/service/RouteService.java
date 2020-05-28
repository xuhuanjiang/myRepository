package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    PageBean<Route> pageQuery(String cid, int pageNumber, int pageSize, String rname);

    Route findOne(int cid);

    boolean findFavorite(int rid, int uid);

    int findCount(int rid);

    boolean addFavotite(int rid, int uid);
}
