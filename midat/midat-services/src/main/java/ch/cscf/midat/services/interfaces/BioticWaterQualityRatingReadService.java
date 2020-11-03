package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingDTO;
import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingLocalizedDTO;

import java.util.List;

/**
 * @author: henryp
 */
public interface BioticWaterQualityRatingReadService {

    BioticWaterQualityRatingDTO getBiologicalRatingForIndexTypeAndValue(String indexTypeCode, double value, Integer versionId);

    List<BioticWaterQualityRatingLocalizedDTO> getBiologicalRatingForIndexTypeLocalized(String indexTypeCode);

}
