package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        CategoryDao dao = new CategoryDaoImpl();
        //首先，先重redis中查询
        //1.获取jedis对象
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> tuple = jedis.zrangeWithScores("category", 0, -1);


        //创建一个list集合，用于存储查询信息
        List<Category> list = null;
        //判断这个键是否有值
        if (tuple.size() == 0 || tuple == null){
            //System.out.println("从数据库查询");
            //redis里面没有值，从数据库查询，然后存入redis中
            list = dao.findAll();
            for (int i = 0; i <list.size() ; i++) {
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            //System.out.println("从redis中查询");
            //redis中有查询结果，这时需要将他所存入的东西存入到list集合中
            list = new ArrayList<Category>();
            for (Tuple tuple1 :tuple){
                //创建一个category对象
                Category category = new Category();
                category.setCid((int) tuple1.getScore());
                category.setCname(tuple1.getElement());
                //将对象加入到list集合中
                list.add(category);
            }
        }
        //最后关闭连接
        //JedisUtil.close(jedis);
        return list;
    }
}
