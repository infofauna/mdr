package ch.cscf.jeci.domain.dto.midat;

/**
 * @author: henryp
 */
public class BioticWaterQualityRatingLocalizedDTO extends BioticWaterQualityRatingDTO {

    public final String rating;

    public BioticWaterQualityRatingLocalizedDTO(String indexTypeCode, String stateCode, String bgColorCode, String textColorCode, String rangeLegendText, double fromValue, double toValue, int sortOrder, String rating, Integer legendVersionId,String currentVersion) {
        super(indexTypeCode, stateCode, bgColorCode, textColorCode, rangeLegendText, fromValue, toValue, sortOrder,legendVersionId,currentVersion);
        this.rating=rating;
    }

    public BioticWaterQualityRatingLocalizedDTO(BioticWaterQualityRatingDTO unlocalizedRating, String rating) {
        super(unlocalizedRating.indexTypeCode, unlocalizedRating.ratingCode, unlocalizedRating.bgColorCode, unlocalizedRating.textColorCode, unlocalizedRating.rangeLegendText, unlocalizedRating.fromValue,unlocalizedRating.toValue,unlocalizedRating.sortOrder,unlocalizedRating.legendVersionId,unlocalizedRating.currentVersion);
        this.rating=rating;
    }

}
