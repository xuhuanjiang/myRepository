package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.Route_imgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.Route_imgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao dao = new RouteDaoImpl();
    Route_imgDao imgDao = new Route_imgDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();
    FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 分页查询功能
     * @param cid
     * @param pageNumber
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(String cid, int pageNumber, int pageSize, String rname) {
        //将现有的数据存入pageBean
        PageBean pageBean = new PageBean();
        pageBean.setPageNumber(pageNumber);
        pageBean.setPageSize(pageSize);

        //调用dao的方法查询总记录条数
        int totalCount = dao.findCount(cid,rname);
        //设置总记录数
        pageBean.setTotalCount(totalCount);

        //分页查询，
        int start = (pageNumber-1)*pageSize;
        List<Route> list = dao.findPage(cid,start,pageSize,rname);
        pageBean.setList(list);

        //计算出总页数
        int totalPage = totalCount%pageSize == 0?totalCount/pageSize :(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        //这里涉及到有3张表，
        //1.先根据id查询route表饭后一个route
        Route route = dao.findRouteById(rid);
        //根据图片表格查询商品图片 rid 查询
        List<RouteImg> list = imgDao.findImgById(route.getRid());
        //将查询的结果集加入到route中
        route.setRouteImgList(list);
        //查询seller表，然后将卖家信息存入bean中
        Seller seller = sellerDao.findSellerById(route.getSid());
        route.setSeller(seller);
        return route;
    }

    @Override
    public boolean findFavorite(int rid, int uid) {
        //调用favoriteDao查询
        Favorite favorite = favoriteDao.findFavoriteByRidAndUid(rid,uid);
        return favorite != null;
    }

    @Override
    public int findCount(int rid) {
        //调用到查询总数
        return favoriteDao.findCountByRid(rid);
    }

    @Override
    public boolean addFavotite(int rid, int uid) {
        //首选先根据 rid和uid查询
        Favorite favorite = favoriteDao.findFavoriteByRidAndUid(rid,uid);
        //判定是否已经收藏
        if (favorite != null){
            return false;
        }else {
            //调用dao添加
            dao.addFavoriteByRidAndUid(rid,uid);//这里dao应该返回一个值。这里不做接收了
            //在收藏成功后应该让route表里面的count加1。这两个操作应该涉及到业务
            //调用dao的方法完成
            dao.addCount(rid);
            return true;
        }
    }
}
