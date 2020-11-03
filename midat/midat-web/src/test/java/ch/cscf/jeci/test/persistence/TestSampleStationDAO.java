package ch.cscf.jeci.test.persistence;

import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationDAO;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author: henryp
 */
public class TestSampleStationDAO extends PersistenceTest {

    @Autowired
    private SampleStationDAO dao;

    @Test
    public void testSetup() {
        assertNotNull(dao);
    }

    @Test
    public void findStationsIdsInsideRectangle() {

        List<Long> stations = dao.findStationsIdsInsideRectangle(400000.0, 900000.0, 50000.0, 300000.0,8L, true , true);
        assertNotNull(stations);
    }

    @Test
    public void findStationsIdsInsideRectangleAndGroup() {

        List<Long> stations = dao.findStationsIdsInsideRectangleAndBelongingToGroups(400000.0, 900000.0, 50000.0, 300000.0, Lists.newArrayList("VD", "PUBLIC"),8L, true);
        assertNotNull(stations);
    }

/*
    @Test
    public void findStationsByGroup() {
        Long sessionUserId = 1l;
        Set<SampleStation> stations = dao.findPublishedStationsBelongingToGroups(sessionUserId, Lists.newArrayList("VD", "PUBLIC"), true);
        assertNotNull(stations);
    }

 */
}
