package ch.cscf.jeci.domain.dto.midat;

/**
 * @author: henryp
 */
public class BioticWaterQualityRatingDTO {

    public final String indexTypeCode;

    public final String ratingCode;

    public final String bgColorCode;

    public final String textColorCode;

    public final String rangeLegendText;

    public final int sortOrder;

    public final Integer legendVersionId;

    public final double fromValue, toValue;
    public final String currentVersion;

    public BioticWaterQualityRatingDTO(String indexTypeCode, String stateCode, String bgColorCode, String textColorCode, String rangeLegendText, double fromValue, double toValue, int sortOrder,Integer legendVersionId,String currentVersion) {
        this.indexTypeCode = indexTypeCode;
        this.ratingCode = stateCode;
        this.bgColorCode = bgColorCode;
        this.textColorCode = textColorCode;
        this.rangeLegendText = rangeLegendText;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.sortOrder = sortOrder;
        this.legendVersionId= legendVersionId;
        this.currentVersion = currentVersion;
    }
}
