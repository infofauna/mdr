package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;


@Entity
@Table(
        name = "AVALIABILITYCALENDAR", schema = "MIDAT"

)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "IAC_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "IAC_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IAC_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IAC_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IAC_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IAC_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IAC_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IAC_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IAC_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IAC_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_AVALIABILITYCALENDAR")

/**
 * Created by abdallahkanso on 13.07.17.
 */
public class IndexAvaliabilityCalendar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IAC_CVL_ID_MIDATINDICE", updatable = false, insertable = false)
    private ThesaurusValue midatIndice;

    @Column(name = "IAC_CVL_ID_MIDATINDICE")
    private Long midatIndiceId;

    @Transient
    @LocalizedProperty("midatIndice")
    private String midatIndiceI18n;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IAC_CVL_ID_MIDATWINDOW", updatable = false, insertable = false)
    private ThesaurusValue midatWindow;

    @Column(name = "IAC_CVL_ID_MIDATWINDOW")
    private Long midatWindowId;

    @Transient
    @LocalizedProperty("midatWindow")
    private String midatWindowI18n;

    @Column(name = "IAC_ELEVATIONMIN")
    private Double elevationMin;


    @Column(name = "IAC_ELEVATIONMAX")
    private Double elevationMax;

    @Column(name = "IAC_STARTDAY")
    private Integer startDay;

    @Column(name = "IAC_ENDDAY")
    private Integer endDay;

    @Column(name = "IAC_STARTMONTH")
    private Integer startMonth;

    @Column(name = "IAC_ENDMONTH")
    private Integer endMonth;

    public ThesaurusValue getMidatIndice() {
        return midatIndice;
    }

    public void setMidatIndice(ThesaurusValue midatIndice) {
        this.midatIndice = midatIndice;
    }

    public Long getMidatIndiceId() {
        return midatIndiceId;
    }

    public void setMidatIndiceId(Long midatIndiceId) {
        this.midatIndiceId = midatIndiceId;
    }

    public String getMidatIndiceI18n() {
        return midatIndiceI18n;
    }

    public void setMidatIndiceI18n(String midatIndiceI18n) {
        this.midatIndiceI18n = midatIndiceI18n;
    }

    public ThesaurusValue getMidatWindow() {
        return midatWindow;
    }

    public void setMidatWindow(ThesaurusValue midatWindow) {
        this.midatWindow = midatWindow;
    }

    public Long getMidatWindowId() {
        return midatWindowId;
    }

    public void setMidatWindowId(Long midatWindowId) {
        this.midatWindowId = midatWindowId;
    }

    public String getMidatWindowI18n() {
        return midatWindowI18n;
    }

    public void setMidatWindowI18n(String midatWindowI18n) {
        this.midatWindowI18n = midatWindowI18n;
    }

    public Double getElevationMin() {
        return elevationMin;
    }

    public void setElevationMin(Double elevationMin) {
        this.elevationMin = elevationMin;
    }

    public Double getElevationMax() {
        return elevationMax;
    }

    public void setElevationMax(Double elevationMax) {
        this.elevationMax = elevationMax;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

}
