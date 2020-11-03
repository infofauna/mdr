package ch.cscf.jeci.test.integration.services;

import ch.cscf.jeci.domain.dto.midat.GroundProtocolDTO;
import ch.cscf.jeci.test.integration.IntegrationTest;
import ch.cscf.midat.services.interfaces.GroundProtocolReadService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author: henryp
 */
@Ignore("Too dependent on DB data")
public class TestGroundProtocolReadService extends IntegrationTest{

    private static final Long ID_OF_A_SAMPLE_WITH_GROUND_PROTOCOL_ITEMS=507l;
    private static final Long ID_OF_A_SAMPLE_WITHOUT_GROUND_PROTOCOL_ITEMS=518l;

    @Autowired
    private GroundProtocolReadService service;

    @Test
    public void testSetup(){
        assertNotNull(service);
    }

    @Test
    public void testGetGroundProtocolWithData(){
        GroundProtocolDTO dto = service.getGroundProtocol(ID_OF_A_SAMPLE_WITH_GROUND_PROTOCOL_ITEMS);
        assertNotNull(dto);
    }

    @Test
    public void testGetGroundProtocolWithoutData(){
        GroundProtocolDTO dto = service.getGroundProtocol(ID_OF_A_SAMPLE_WITHOUT_GROUND_PROTOCOL_ITEMS);
        assertNull(dto);
    }

}
