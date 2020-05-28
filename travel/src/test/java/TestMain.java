import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;

public class TestMain {
    @Test
    public void testJdbc() {
        //UserDao dao = new UserDaoImpl();
        JDBCUtils.getDataSource();
    }
}
