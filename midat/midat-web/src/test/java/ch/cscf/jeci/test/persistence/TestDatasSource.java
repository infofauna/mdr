package ch.cscf.jeci.test.persistence;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.assertNotNull;


/**
 * @author: henryp
 */
public class TestDatasSource extends PersistenceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws Exception{

        assertNotNull("The context's data source was null.", dataSource);

        Connection con = dataSource.getConnection();

        assertNotNull("Received a null connection.", con);

        con.prepareStatement("select 0 from dual").execute();

    }
}
