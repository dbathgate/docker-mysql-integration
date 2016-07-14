import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MySqlDaoIT {

    private MySqlDao mysqlDao;

    @Before
    public void setUp() {
        String host = System.getProperty("mysql.host");
        System.out.println("Properties: " + System.getProperty("mysql.host") + " " + System.getProperty("mysql.port"));
        Integer port = Integer.parseInt(System.getProperty("mysql.port"));
        
        mysqlDao = new MySqlDao(host, port);

    }
    @Test
    public void pingTest() {

        Assert.assertTrue(mysqlDao.ping());
    }

    @Test
    public void setValueTest() {
        mysqlDao.setValue("key1", "value1");
        mysqlDao.setValue("key2", "value2");
        mysqlDao.setValue("key3", "value3");

        Optional<String> value1 = mysqlDao.getValue("key1");
        Optional<String> value2 = mysqlDao.getValue("key2");
        Optional<String> value3 = mysqlDao.getValue("key3");

        Assert.assertTrue(value1.isPresent());
        Assert.assertTrue(value2.isPresent());
        Assert.assertTrue(value3.isPresent());

        Assert.assertEquals("value1", value1.get());
        Assert.assertEquals("value2", value2.get());
        Assert.assertEquals("value3", value3.get());
    }
}
