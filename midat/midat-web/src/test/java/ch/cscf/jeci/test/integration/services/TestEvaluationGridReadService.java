package ch.cscf.jeci.test.integration.services;

import ch.cscf.jeci.domain.dto.midat.EvaluationGridDTO;
import ch.cscf.jeci.test.integration.IntegrationTest;
import ch.cscf.midat.services.interfaces.EvaluationGridReadService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author: henryp
 */
@Ignore("Too dependent on DB data")
public class TestEvaluationGridReadService extends IntegrationTest{

    private static final Long ID_OF_A_SAMPLE_WITH_GRID_ITEMS=507l;
    private static final Long ID_OF_A_SAMPLE_WITHOUT_GRID_ITEMS=518l;

    @Autowired
    private EvaluationGridReadService service;

    @Test
    public void testSetup(){
        assertNotNull(service);
    }

    @Test
    public void testGetEvaluationGridWithData(){
        EvaluationGridDTO gridDTO = service.getEvalutationGrid(ID_OF_A_SAMPLE_WITH_GRID_ITEMS);
        assertNotNull(gridDTO);
        assertNotNull(gridDTO.getGrid());
        assertNotNull(gridDTO.getExtraFields());
    }

    @Test
    public void testGetEvaluationGridWithoutData(){
        EvaluationGridDTO gridDTO = service.getEvalutationGrid(ID_OF_A_SAMPLE_WITHOUT_GRID_ITEMS);
        assertNull(gridDTO);
    }

}
