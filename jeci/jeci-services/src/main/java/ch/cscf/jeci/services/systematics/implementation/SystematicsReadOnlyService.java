package ch.cscf.jeci.services.systematics.implementation;

import ch.cscf.jeci.domain.entities.systematics.Taxon;
import ch.cscf.jeci.domain.entities.systematics.TaxonDesignation;
import ch.cscf.jeci.domain.entities.systematics.TaxonomicRank;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonomicRankDAO;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SystematicsReadOnlyService implements ch.cscf.jeci.services.systematics.interfaces.SystematicsReadOnlyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaxonDAO taxonDAO;

    @Autowired
    private TaxonomicRankDAO rankDAO;

    private final Map<Long, Taxon> byId = new HashMap<>();
    private final Multimap<Long, Taxon> byParentId = HashMultimap.create();
    private final Multimap<Long, Taxon> byRankId = HashMultimap.create();

    private List<TaxonomicRank> ranks;
    private final Map<Long, TaxonomicRank> ranksById = new HashMap<>();


    @PostConstruct
    public void init(){

        long start = System.currentTimeMillis();
        logger.info("Starting to load all the taxons from DB...");

        List<Taxon> allTaxa = taxonDAO.list("id", Lists.newArrayList("designations", "taxonomicRank"));

        logger.info("Starting to build the taxon maps...");

        long startMap = System.currentTimeMillis();
        int numberOfTaxa=0, withoutParent=0, withoutRank=0, withoutDesignation=0;

        for(Taxon taxon : allTaxa){
            numberOfTaxa++;

            byId.put(taxon.getId(), taxon);

            Long parentTaxonId = taxon.getParentTaxonId();
            if(parentTaxonId != null) {
                byParentId.put(parentTaxonId, taxon);
            }else{
                withoutParent++;
            }


            Long taxonomicRankId = taxon.getTaxonomicRankId();
            if(taxonomicRankId != null){
                byRankId.put(taxonomicRankId, taxon);
            }else{
                withoutRank++;
            }

            List<TaxonDesignation> designations = taxon.getDesignations();
            if(designations.isEmpty()){
                withoutDesignation++;
            }
        }

        long finish = System.currentTimeMillis();

        logger.info("Building of the taxa maps finished.\n {} taxa were loaded from the database.\n The total loading process took {} milliseconds. " +
                "\nThe loading of the entites from the database took {} milliseconds.\nThe building of the maps in memory took {}  milliseconds.",
                numberOfTaxa,
                finish - start,
                startMap - start,
                finish - startMap
        );

        if(withoutParent > 1){
            logger.warn("{} taxa without a parent were found. It's normal to have at least one (root of the tree, kingdoms) but not many !", withoutParent);
        }

        if(withoutRank != 0){
            logger.warn("{} taxa without a taxonomic rank were found. This is a problem with the data.", withoutRank);
        }

        if(withoutDesignation != 0){
            logger.warn("{} taxa without designation were found. This is a problem with the data.", withoutDesignation);
        }



        logger.info("Now loading the taxonomic ranks.");
        ranks = rankDAO.list("id");
        for(TaxonomicRank rank : ranks){
            ranksById.put(rank.getId(), rank);
        }
        logger.info("{} taxonomic ranks loaded.", ranks.size());

    }

    @Override
    public Taxon getTaxonById(Long taxonId) {
        return byId.get(taxonId);
    }

    @Override
    public TaxonomicRank getRankById(Long rankId) {
        return ranksById.get(rankId);
    }

    @Override
    public List<Taxon> getTaxonByParentId(Long parentTaxonId) {
        ArrayList<Taxon> arrayList = new ArrayList<>();
        arrayList.addAll(byParentId.get(parentTaxonId));
        return arrayList;
    }

    @Override
    public List<Taxon> getTaxonsByRankId(Long taxonomyRankId) {
        ArrayList<Taxon> arrayList = new ArrayList<>();
        arrayList.addAll(byRankId.get(taxonomyRankId));
        return arrayList;
    }

    @Override
    public Taxon getParentByChildId(Long childId) {
        Taxon child = byId.get(childId);
        return byId.get(child.getParentTaxonId());
    }

    @Override
    public String getRankDesignation(Long rankId) {
        TaxonomicRank rank = ranksById.get(rankId);
        if(rank != null){
            return rank.getDesignation();
        }
        throw new IllegalArgumentException("There is no rank with ID="+rankId);
    }

    @Override
    public List<TaxonomicRank> getAllRanks() {
        return Collections.unmodifiableList(ranks);
    }

    @Override
    public Taxon getAncestorOfRank(Long taxonId, String ancestorRankCode, boolean includeSelf){

        Taxon ancestor;
        if(includeSelf){
            ancestor = getTaxonById(taxonId);
        }else{
            ancestor = getParentByChildId(taxonId);
        }

        while(ancestor != null){

            TaxonomicRank rank = getRankById(ancestor.getTaxonomicRank().getId());

            if(rank.getCode().equals(ancestorRankCode)){
                return ancestor;
            }

            ancestor = getParentByChildId(ancestor.getId());
        }

        return null;
    }

    @Override
    public String getAncestorDesignationForRank(Long taxonId, String ancestorRankCode, boolean includeSelf){

        Taxon ancestor = getAncestorOfRank(taxonId, ancestorRankCode, includeSelf);
        if(ancestor != null){
            return getLatinDesignation(ancestor);
        }
        return null;
    }

    @Override
    public String taxonToString(Long taxonId, int deepness, String separator) {
        Taxon taxon = getTaxonById(taxonId);
        Taxon parent = getParentByChildId(taxon.getId());

        String latinDesignation = getLatinDesignation(taxon);

        if(deepness <= 0 || parent == null){
            return latinDesignation;
        }

        deepness --;
        return taxonToString(parent.getId(), deepness, separator) + separator + latinDesignation;
    }

    @Override
    public String taxonToString(Long taxonId, List<String> rankCodesToInclude, String separator) {

        Taxon taxon = getTaxonById(taxonId);
        List<String> taxaDesignations = new LinkedList<>();

        while(taxon != null){

            TaxonomicRank rank = getRankById(taxon.getTaxonomicRankId());

            if(rankCodesToInclude.indexOf(rank.getCode())>=0){
                taxaDesignations.add(0, getLatinDesignation(taxon));
            }

            if(taxon.getParentTaxonId() != null){
                taxon = getTaxonById(taxon.getParentTaxonId());
            }else{
                taxon=null;
            }
        }

        return String.join(separator, taxaDesignations);
    }

    private String getLatinDesignation(Taxon taxon){
        for(TaxonDesignation des : taxon.getDesignations()){
            if(des.getLanguageId().equals(Language.LATIN_ID)){
                return des.getDesignation();
            }
        }
        return "Latin N/A. Taxon ID="+taxon.getId();
    }
}
