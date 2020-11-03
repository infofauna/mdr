package ch.cscf.jeci.test.persistence;

import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.dto.midat.SearchResultDTO;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author: henryp
 */
public class TestSampleDAO extends PersistenceTest {

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private SampleStationDAO stationDAO;

    @Test
    public void testSetup(){
        assertNotNull(sampleDAO);
        assertNotNull(stationDAO);
    }

    @Test
    public void testSearch1() {

        Page page = new Page();
        SampleSearchParameters param = buildSampleSearchParameters();
        List<SearchResultDTO> samples = sampleDAO.search(param, "sampleDate", SortOrder.desc, page);
        assert (page.getTotalRows() != null);

        assertNotNull(samples);
    }

    @Test
    public void testSearch3() {

        Page page = new Page();
        SampleSearchParameters param = buildSampleSearchParameters();
        param.setLocality("sonce");
        List<SearchResultDTO> samples = sampleDAO.search(param, "sampleDate", SortOrder.desc, page);
        assert (page.getTotalRows() != null);

        assertNotNull(samples);
    }
    @Test
    public void testEmptySearch() {

        SampleSearchParameters param = buildSampleSearchParameters();
        param.setLocality("lalalalalalalalalalalalalalalalalal");

        List<SearchResultDTO> samples = sampleDAO.search(param, "sampleDate", SortOrder.desc, null);

        assertNotNull(samples);
        assertTrue(samples.isEmpty());
    }

    @Test
    public void testExportSearch(){
        List<Map<String, Object>> result = sampleDAO.searchForExport(buildSampleSearchParameters());
        assertNotNull(result);
    }


    private SampleSearchParameters buildSampleSearchParameters() {
        SampleSearchParameters param = new SampleSearchParameters();
        param.setCreationUserId(2l);
        return param;
    }

    @Test
    public void testSearchByStations(){
        Collection<SampleStation> stations = stationDAO.list();
        List<Long> stationIds = new ArrayList<>(stations.size()+1);
        Page page = new Page();

        for(SampleStation station : stations){
            stationIds.add(station.getId());
        }

        //make sure at least one id
        stationIds.add(1l);

        List<SearchResultDTO> samples = sampleDAO.searchByStations(stationIds, 2l, "sampleDate", SortOrder.desc, page, true, true,true);

        assertNotNull(samples);
        //assertFalse(samples.isEmpty());
    }

}
