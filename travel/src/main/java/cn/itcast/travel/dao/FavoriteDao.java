package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    Favorite findFavoriteByRidAndUid(int rid, int uid);

    int findCountByRid(int rid);

    public List<Route> findRank();
}
