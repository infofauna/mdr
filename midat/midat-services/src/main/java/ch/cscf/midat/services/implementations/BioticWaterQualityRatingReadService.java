package ch.cscf.midat.services.implementations;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingDTO;
import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingLocalizedDTO;
import ch.cscf.jeci.domain.entities.midat.BioticWaterQualityRating;
import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;
import ch.cscf.jeci.persistence.daos.interfaces.midat.BioticWaterQualityRatingDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.IndiceVersionDAO;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Service
public class BioticWaterQualityRatingReadService implements  ch.cscf.midat.services.interfaces.BioticWaterQualityRatingReadService {

    @Autowired
    private BioticWaterQualityRatingDAO biologicalRatingDAO;

    @Autowired
    private ThesaurusReadOnlyService thesaurus;

    @Autowired
    private I18nService i18NService;

    @Autowired
    private IndiceVersionDAO indiceVersionDAO;

    private final ListMultimap<String, BioticWaterQualityRatingDTO> ratings = ArrayListMultimap.create();

    @PostConstruct
    public void init(){

        for(BioticWaterQualityRating rating : biologicalRatingDAO.list("sortOrder", Lists.newArrayList("indexType", "designation", "bgColor", "textColor"))){
            String indexTypeCode = rating.getIndexType().getCode();
            String ratingCode = rating.getDesignation().getCode();

            String bgColorCode = rating.getBgColor() == null ? null : rating.getBgColor().getCode();
            String textColorCode = rating.getTextColor() == null ? null : rating.getTextColor().getCode();

            BioticWaterQualityRatingDTO dto = new BioticWaterQualityRatingDTO(indexTypeCode, ratingCode, bgColorCode, textColorCode, rating.getRangeLegendText(), rating.getFromValue(), rating.getToValue(), rating.getSortOrder(),rating.getLegendVersionId());
            ratings.put(indexTypeCode, dto);
        }
    }

    @Override
    public BioticWaterQualityRatingDTO getBiologicalRatingForIndexTypeAndValue(String indexTypeCode, double value,Integer indexVersionId) {
        checkTypeCode(indexTypeCode);

        List<BioticWaterQualityRatingDTO> ratingsList = ratings.get(indexTypeCode);

        /**
         * Midat plus
         * when provided sample index version is not null, use it to filter out the rating values
         */
        if(indexVersionId != null){
            ratingsList= ratingsList.stream().filter(r -> {
                if(r.legendVersionId != null){
                   return  r.legendVersionId.equals(indexVersionId);
                }
              return false;
            } ).collect(Collectors.toList());
        }else{
            throw new IllegalArgumentException("There is no valid biological water state for the index type "+indexTypeCode +" and the indexVersionId "+indexVersionId);
        }

        for(BioticWaterQualityRatingDTO rating :ratingsList ){
            if(matches(rating, value)){
                return rating;
            }
        }

        throw new IllegalArgumentException("There is no valid biological water state for the index type "+indexTypeCode +" and the value "+value);
    }

    @Override
    public List<BioticWaterQualityRatingLocalizedDTO> getBiologicalRatingForIndexTypeLocalized(String indexTypeCode) {
        checkTypeCode(indexTypeCode);

        List<SampleIndiceVersion> activeIndexes = (List<SampleIndiceVersion>) indiceVersionDAO.list();

        List<BioticWaterQualityRatingDTO> indexRatings = ratings.get(indexTypeCode);
        List<BioticWaterQualityRatingLocalizedDTO> localizedRatings = new ArrayList<>(indexRatings.size());
        for(BioticWaterQualityRatingDTO rating : indexRatings){

            // add only active indexes
            List<SampleIndiceVersion> activeIndexesVal = activeIndexes.stream().filter(v -> {
                if (v.getId().intValue() == rating.legendVersionId.intValue() && v.getCurrent() == 'Y') {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());

            if(activeIndexesVal.size() == 0){
                continue;
            }
            String ratingDesignation = thesaurus.getLocalizedString(ThesaurusCodes.REALM_BIOLSTATETXT, rating.ratingCode, i18NService.currentLanguageCode());
            localizedRatings.add(new BioticWaterQualityRatingLocalizedDTO(rating, ratingDesignation));
        }
        return localizedRatings;
    }

    private boolean matches(BioticWaterQualityRatingDTO bs, double value){
        return bs.fromValue <= value && value <= bs.toValue;
    }

    private void checkTypeCode(String indexTypeCode) {
        if(!ratings.keySet().contains(indexTypeCode)){
            throw new IllegalArgumentException("The index type code "+indexTypeCode+" has no biological water state associated.");
        }
    }
}
