package ch.cscf.jeci.test.integration.services;

import ch.cscf.jeci.test.integration.IntegrationTest;
import ch.cscf.midat.services.interfaces.StationReadService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * @author: henryp
 */
public class TestSampleStationReadService extends IntegrationTest{

    @Autowired
    private StationReadService service;


    @Test
    public void testSetup(){
        assertNotNull(service);
    };

    /*
    @Test
    public void testGetStationsAsFeaturesReturnsFeatures(){
        FeatureCollection stationsAsFeatures = service.getAllAuthorizedStationsAsFeatures(false);
        assertNotNull(stationsAsFeatures);
        //assertFalse(stationsAsFeatures.getFeatures().isEmpty());
    }

    @Test
    public void testGetStationsAsFeaturesContainsAllProperties(){

        FeatureCollection stationsAsFeatures = service.getAllAuthorizedStationsAsFeatures(false);
        for(Feature feature : stationsAsFeatures.getFeatures()){
            assertTrue(feature.getProperties().size() == 3);
        }
    }
    */


}
