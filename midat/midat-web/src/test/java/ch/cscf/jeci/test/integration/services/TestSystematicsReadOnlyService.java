package ch.cscf.jeci.test.integration.services;

import ch.cscf.jeci.domain.entities.systematics.TaxonomicRank;
import ch.cscf.jeci.services.systematics.interfaces.SystematicsReadOnlyService;
import ch.cscf.jeci.test.integration.IntegrationTest;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: henryp
 */
public class TestSystematicsReadOnlyService extends IntegrationTest{

    private static final long NUM_OF_RANKS = 23;
    private static final String ROOT_RANK_NAME = "Kingdom";
    private static final String LOWEST_RANK_NAME = "Hybrides";
    public static final long ID_OF_LOWEST_RANK = 64l;


    @Autowired
    private SystematicsReadOnlyService service;

    @Test
    public void testSetup(){
        assertNotNull(service);
    };

    @Test
    public void testGetAllRanks(){
        List<TaxonomicRank> ranks = service.getAllRanks();
        assertNotNull(ranks);

        //should be 22 ranks from kingdom to form
        assertEquals(NUM_OF_RANKS, ranks.size());
        assertEquals(ROOT_RANK_NAME, ranks.get(0).getDesignation());
        assertEquals(LOWEST_RANK_NAME, ranks.get((int) NUM_OF_RANKS - 1).getDesignation());
    }

    @Test
    public void testGetRankById(){
        assertEquals(ROOT_RANK_NAME, service.getRankById(1l).getDesignation());
        assertEquals(LOWEST_RANK_NAME, service.getRankById(ID_OF_LOWEST_RANK).getDesignation());
    }

    @Test
    public void testGetRankByIdInvalid(){
        assertNull(service.getRankById(99999l));
    }


        @Test
    public void testGetRankDesignation(){
        assertEquals(ROOT_RANK_NAME, service.getRankDesignation(1l));
        assertEquals(LOWEST_RANK_NAME, service.getRankDesignation(ID_OF_LOWEST_RANK));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRankDesignationInvalid(){
        service.getRankDesignation(9999l);
    }

    @Test
    public void testGetAncestor(){
        long idOfCanisLupusSpecies = 1003308l;

        String phylum = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "PHYLUM", true);
        String classe = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "CLASS", true);
        String order = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "ORDER", true);
        String family = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "FAMILY", true);
        String genus = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "GENUS", true);
        String species = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "SPECIES", true);
        String nonexistent = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "NIMPORTEQUOI", true);

        assertEquals("Chordata", phylum );
        assertEquals("Mammalia", classe );
        assertEquals("Carnivora", order );
        assertEquals("Canidae", family );
        assertEquals("Canis", genus );
        assertEquals("lupus", species);
        assertNull(nonexistent);

        //not including the taxon provided, so no speices should be found found as ancestor of another species :
        species = service.getAncestorDesignationForRank(idOfCanisLupusSpecies, "SPECIES", false);
        assertNull(species);

    }

    @Test
    public void testTaxonToStringWithRanksList(){

        List<String> ranksToInclude = Lists.newArrayList("KINGDOM", "PHYLUM", "CLASS", "ORDER", "FAMILY", "GENUS", "SPECIES");

        long idOfCanisSpecies = 1003308l;
        String representation = service.taxonToString(idOfCanisSpecies, ranksToInclude, " / ");

        assertEquals("ANIMALIA / Chordata / Mammalia / Carnivora / Canidae / Canis / lupus", representation);

        ranksToInclude = Lists.newArrayList("GENUS", "SPECIES");
        representation = service.taxonToString(idOfCanisSpecies, ranksToInclude, " ");
        assertEquals("Canis lupus", representation);

    }

}
