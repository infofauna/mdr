package ch.cscf.jeci.domain.dto.midat;

/**
 * @author: henryp
 */
public class BioticIndexesDTO {

    public final Double ibchIndexValue;
    public final String ibchExtraMsgCode;
    public final String ibchCalendarDateInterval;
    public final Double makroIndexValue;
    public final Double spearIndexValue;

    public final BioticWaterQualityRatingDTO ibchQuality;
    public final BioticWaterQualityRatingDTO spearQuality;
    public final BioticWaterQualityRatingDTO makroindexQuality;

    public BioticIndexesDTO(Double ibchIndexValue, Double makroIndexValue, Double spearIndexValue, BioticWaterQualityRatingDTO ibchQuality,  BioticWaterQualityRatingDTO makroindexQuality, BioticWaterQualityRatingDTO spearQuality,String ibchExtraMsgCode ,String ibchCalendarDateInterval) {
        this.ibchIndexValue = ibchIndexValue;
        this.makroIndexValue = makroIndexValue;
        this.spearIndexValue = spearIndexValue;
        this.ibchQuality = ibchQuality;
        this.spearQuality = spearQuality;
        this.makroindexQuality = makroindexQuality;
        this.ibchExtraMsgCode =ibchExtraMsgCode;
        this.ibchCalendarDateInterval = ibchCalendarDateInterval;
    }




}
