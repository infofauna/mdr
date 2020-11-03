package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * @author: henryp
 */

@Entity
@Table(
        name = "SAMPLESTATION", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "SST_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "SST_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SST_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SST_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SST_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SST_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SST_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SST_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SST_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SST_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "SEQ_SAMPLESTATION")
public class SampleStation extends BaseEntity {

    @Column(name = "SST_OID")
    private String stationNumber;


    @OneToMany(mappedBy = "station")
    private List<SampleStationItem> items;


    @Type(type = "org.hibernate.spatial.GeometryType")
    @Column(name = "SST_COORDINATES")
    private Point coordinates;


    @Column(name = "SST_Z")
    private Double coordinateZ;

    @OneToMany(mappedBy = "station"  ,fetch = FetchType.LAZY)
    private List<Sample> samples;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SST_CVL_ID_CANTON", updatable = false, insertable = false)
    private ThesaurusValue stationCanton;


    @Column(name = "SST_CVL_ID_CANTON")
    private Long stationCantonId;

    @Transient
    @LocalizedProperty("stationCanton")
    private String stationCantonI18n;

    @Column(name = "SST_GEWISSNR")
    private String stationGewissNumber;


    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name="SST_SST_ID")
    private SampleStation parent;


    @OneToMany(mappedBy="parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<SampleStation> stationStations;



    @Column(name = "SST_INS_ID")
    private Long stationPrincipalInstitutionId;


    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String oid) {
        this.stationNumber = oid;
    }

    public List<SampleStationItem> getItems() {
        return items;
    }

    public void setItems(List<SampleStationItem> items) {
        this.items = items;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public ThesaurusValue getStationCanton() {
        return stationCanton;
    }

    public void setStationCanton(ThesaurusValue stationCanton) {
        this.stationCanton = stationCanton;
    }

    public Long getStationCantonId() {
        return stationCantonId;
    }

    public void setStationCantonId(Long stationCantonId) {
        this.stationCantonId = stationCantonId;
    }

    public String getStationCantonI18n() {
        return stationCantonI18n;
    }

    public void setStationCantonI18n(String stationCantonI18n) {
        this.stationCantonI18n = stationCantonI18n;
    }


    public List<SampleStation> getStationStations() {
        return stationStations;
    }

    public void setStationStations(List<SampleStation> stationStations) {
        this.stationStations = stationStations;
    }

    public String getStationGewissNumber() {
        return stationGewissNumber;
    }

    public void setStationGewissNumber(String stationGewissNumber) {
        this.stationGewissNumber = stationGewissNumber;
    }
    public SampleStation getParent() {
        return parent;
    }

    public void setParent(SampleStation parent) {
        this.parent = parent;
    }

    public Long getStationPrincipalInstitutionId() {
        return stationPrincipalInstitutionId;
    }

    public void setStationPrincipalInstitutionId(Long stationPrincipalInstitutionId) {
        this.stationPrincipalInstitutionId = stationPrincipalInstitutionId;
    }
    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public Double getCoordinateZ() {
        return coordinateZ;
    }

    public void setCoordinateZ(Double coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}
