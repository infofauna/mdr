package ch.cscf.jeci.domain.dto.midat;

/**
 * Created by abdallahkanso on 11.07.17.
 */
public class StationDTO {

    private Long id;
    private String stationNumber;
    private Double stationCoordinatesX, stationCoordinatesY, stationAltitude;
    private String stationCanton;
    private String stationGewissNumber;
    private Double stationDistance;
    private String stationDisplay;

    private Long stationPrincipalInstitutionId;
    private String stationPrincipalInstitution;

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Double getStationCoordinatesX() {
        return stationCoordinatesX;
    }

    public void setStationCoordinatesX(Double stationCoordinatesX) {
        this.stationCoordinatesX = stationCoordinatesX;
    }

    public Double getStationCoordinatesY() {
        return stationCoordinatesY;
    }

    public void setStationCoordinatesY(Double stationCoordinatesY) {
        this.stationCoordinatesY = stationCoordinatesY;
    }

    public Double getStationAltitude() {
        return stationAltitude;
    }

    public void setStationAltitude(Double stationAltitude) {
        this.stationAltitude = stationAltitude;
    }

    public String getStationCanton() {
        return stationCanton;
    }

    public void setStationCanton(String stationCanton) {
        this.stationCanton = stationCanton;
    }

    public String getStationGewissNumber() {
        return stationGewissNumber;
    }

    public void setStationGewissNumber(String stationGewissNumber) {
        this.stationGewissNumber = stationGewissNumber;
    }

    public Double getStationDistance() {
        return stationDistance;
    }

    public void setStationDistance(Double stationDistance) {
        this.stationDistance = stationDistance;
    }


    public String getStationDisplay() {
        return stationDisplay;
    }

    public void setStationDisplay(String stationDisplay) {
        this.stationDisplay = stationDisplay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationPrincipalInstitution() {
        return stationPrincipalInstitution;
    }

    public void setStationPrincipalInstitution(String stationPrincipalInstitution) {
        this.stationPrincipalInstitution = stationPrincipalInstitution;
    }

    public Long getStationPrincipalInstitutionId() {
        return stationPrincipalInstitutionId;
    }

    public void setStationPrincipalInstitutionId(Long stationPrincipalInstitutionId) {
        this.stationPrincipalInstitutionId = stationPrincipalInstitutionId;
    }

    public StationDTO() {
    }
}
