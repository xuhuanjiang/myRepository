package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询类别总路线功能
     * @param cid
     * @return
     */
    @Override
    public int findCount(String cid,String rname) {
        StringBuffer sb = new StringBuffer("select count(*) from tab_route where 1=1");
        List params = new ArrayList();
        //判断cid
        if (cid !=null && !"".equals(cid)){
            sb.append(" and cid = ?");
            params.add(Integer.parseInt(cid));
        }
        //判断rname
        if ( rname != null &&  !("".equals(rname)) ){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        String sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     * 分页查询旅游路线
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findPage(String cid, int start, int pageSize,String rname) {
        StringBuffer sb = new StringBuffer("select * from tab_route where 1=1");
        //判断rname是否为null,且rname取出空格后是否为空串
        List params = new ArrayList();
        //判断cid
        if (cid !=null && !"".equals(cid)){
            sb.append(" and cid=? ");
            params.add(Integer.parseInt(cid));
        }
        //判断rname
        if ( rname != null &&  !("".equals(rname)) ){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        String sql = sb.toString();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    /**
     * 根据id查询route
     * @param rid
     * @return
     */
    @Override
    public Route findRouteById(int rid) {
        String sql = "select * from tab_route where rid=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavoriteByRidAndUid(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        jdbcTemplate.update(sql,rid,new Date(),uid);
    }

    /**
     * 收藏完成后count+1 便于后面排行榜
     * @param rid
     */
    @Override
    public void addCount(int rid) {
        String sql = "update tab_route set count=count+1 where rid = ?";
        jdbcTemplate.update(sql,rid);
    }
}
