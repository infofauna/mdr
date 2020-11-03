package ch.cscf.jeci.test.integration.services;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingDTO;
import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingLocalizedDTO;
import ch.cscf.jeci.test.integration.IntegrationTest;
import ch.cscf.midat.services.interfaces.BioticWaterQualityRatingReadService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: henryp
 */
public class TestBioticWaterQualityGradeReadService extends IntegrationTest{

    @Autowired
    private BioticWaterQualityRatingReadService service;

    @Test
    public void testSetup(){
        assertNotNull(service);
    };

    @Test
    public void testValueIBCH(){
        BioticWaterQualityRatingDTO state = service.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_IBCH, 10,null);
        assertNotNull(state);
    }

    @Test
    public void testValueMAKROINDEX(){
        BioticWaterQualityRatingDTO state = service.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_MAKROINDEX, 4,null);
        assertNotNull(state);
    }

    @Test
    public void testValueSPEARWithdecimal(){
        BioticWaterQualityRatingDTO state = service.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, 19.99,null);
        assertNotNull(state);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalValue(){
        service.getBiologicalRatingForIndexTypeAndValue(ThesaurusCodes.MIDATINDICE_SPEARINDEX, 66666,null);
    }

    @Test
    public void testListIBCH(){
        List<BioticWaterQualityRatingLocalizedDTO> states = service.getBiologicalRatingForIndexTypeLocalized(ThesaurusCodes.MIDATINDICE_IBCH);
        assertNotNull(states);
        assertFalse(states.isEmpty());
    }

    @Test
    public void testListOrdering(){
        List<BioticWaterQualityRatingLocalizedDTO> states = service.getBiologicalRatingForIndexTypeLocalized(ThesaurusCodes.MIDATINDICE_IBCH);

        BioticWaterQualityRatingDTO current, next;

        for(int i=0; i<states.size()-1; i++){
            current = states.get(i);
            next=states.get(i+1);
            assertTrue(current.sortOrder<next.sortOrder);
        }

    }

}
