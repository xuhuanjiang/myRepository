package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findFavoriteByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid=? and uid=?";
        Favorite favorite =null;
        try {
            favorite = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){

        }

        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public List<Route> findRank() {
        String sql = "select r.rname,r.price,r.rid from tab_route r join  " +
                "(select count(*) ,rid from tab_favorite group by rid) f on r.rid=f.rid;";
        return null;
    }
}
