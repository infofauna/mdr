package ch.cscf.jeci.test.persistence;

import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationItemDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author: henryp
 */
public class TestStationItemDAO extends PersistenceTest{

    @Autowired
    private SampleStationItemDAO dao;

    @Test
    public void testWatercourseSearch(){

        List<String> names1 = dao.findWatercourseNamesLike("su", 4l);
        List<String> names2 = dao.findWatercourseNamesLike("sü", 4l);
        List<String> names3 = dao.findWatercourseNamesLike("SU", 4l);

        assertTrue(names1.equals(names2));
        assertTrue(names1.equals(names3));
        assertTrue(names2.equals(names3));

    }

    @Test
    public void testLocalitySearch(){

        List<String> names1 = dao.findLocalityNamesLike("su", 4l);
        List<String> names2 = dao.findLocalityNamesLike("sü", 4l);
        List<String> names3 = dao.findLocalityNamesLike("SU", 4l);

        assertTrue(names1.equals(names2));
        assertTrue(names1.equals(names3));
        assertTrue(names2.equals(names3));
    }
}
