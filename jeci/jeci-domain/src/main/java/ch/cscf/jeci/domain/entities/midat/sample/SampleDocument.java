package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;

import javax.persistence.*;


@Entity
@Table(
        name="SAMPLEDOCUMENT", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SPT_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SPT_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SPT_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SPT_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SPT_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SPT_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SPT_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SPT_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SPT_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SPT_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEHEADER")

public class SampleDocument extends BaseEntity {

    @Column(name = "SPT_SPH_ID", insertable = false, updatable = false)
    private Long sampleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPT_SPH_ID")
    private Sample sample;

    @Column(name="SPT_FILENAME")
    private String fileName;

    @Column(name="SPT_TITLE")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPT_PTV_ID")
    private MIDATProtocolVersion protocolVersion;


    public Long getSampleId() {
        return sampleId;
    }

    public Sample getSample() {
        return sample;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
