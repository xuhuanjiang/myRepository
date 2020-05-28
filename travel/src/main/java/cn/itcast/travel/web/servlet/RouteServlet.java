package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    /**
     * 带条件分页查询功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    /**
     * 路线分类查询功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取页面的参数，cid，pageNumber，pageSize
        String cid = request.getParameter("cid");
        String pageNumber_str = request.getParameter("pageNumber");
        String pageSize_str = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
       // boolean flag = "".equals(rname);
        //System.out.println(!flag);
        //进行判定和3个参数的初始化
        //int cid = 0;
        if ("null".equals(cid)){
            cid="";
        }
        int pageNumber = 0;
        if (pageNumber_str == null || "".equals(pageNumber_str)){
            pageNumber=1;
        }else {
            pageNumber = Integer.parseInt(pageNumber_str);
        }

        int pageSize = 0;
        if (pageSize_str == null || "".equals(pageSize_str)){
            pageSize=5;
        }

        //调用service方法进行分页查询
        PageBean<Route> pageBean = service.pageQuery(cid,pageNumber,pageSize,rname);

        //获取pageBean后将数据响应回去
        writeValue(pageBean,response);
    }

    /**
     * 路线详细页显示功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数cid
        String rid_str = request.getParameter("rid");
        int rid = 0;
        if (rid_str != null || rid_str.length()>0){
            rid = Integer.parseInt(rid_str);
        }

        //调用service的方法查询结果
        Route route = service.findOne(rid);

        writeValue(route,response);
    }

    /**
     * 查询用户是否已经收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递的参数rid
        String rid_str = request.getParameter("rid");
        int rid = 0;
        if (rid_str != null || rid_str.length()>0){
            rid = Integer.parseInt(rid_str);
        }

        //从session中获取用户登录对象user，并判定用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        int uid ;
        if (user == null){
            //用户没有登录
            uid = 0;
        }else {
            uid = user.getUid();
        }

        //调用service的方法查询
        boolean flag = service.findFavorite(rid,uid);
        //这里规定返回true是查到了对象。即用户已经收藏了
        writeValue(flag,response);
    }

    /**
     * 查询收藏总数
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavoriteCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递的参数rid
        String rid_str = request.getParameter("rid");
        int rid = 0;
        if (rid_str != null || rid_str.length()>0){
            rid = Integer.parseInt(rid_str);
        }

        //调用service查询所有的rid
        int count = service.findCount(rid);

        writeValue(count,response);
    }

    /**
     * 添加收藏的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递的参数rid
        String rid_str = request.getParameter("rid");
        int rid = 0;
        if (rid_str != null || rid_str.length()>0){
            rid = Integer.parseInt(rid_str);
        }
        //从session中获取用户登录对象user，并判定用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        int uid ;
        if (user == null){
            //用户没有登录
            return;
        }else {
            uid = user.getUid();
        }

        //调用service的方法添加
        boolean flag = service.addFavotite(rid,uid);

        writeValue(flag,response);
    }
}
