package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.domain.entities.thesaurus.Language;

import javax.persistence.*;
import java.util.List;

/**
 * @author: kansoa
 */
@Entity
@Table(
        name="SAMPLEHEADERMASSFILE", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SMF_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SMF_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SMF_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SMF_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SMF_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SMF_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SMF_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SMF_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SMF_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SMF_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEHEADERMASSFILE")
public class SampleMassOriginalFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="SMF_LAN_ID")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="SMF_PTV_ID")
    private MIDATProtocolVersion protocolVersion;

    @Column(name="SMF_FILE")
    private byte[] bytes;

    @Column(name="SMF_FILENAME")
    private String fileName;

    @Column(name="SMF_SHEETNAME")
    private String sheetName;

    @OneToMany(mappedBy = "sampleMassOriginalFile")
    private List<Sample> samples;

    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public Language getLanguage() {
        return language;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
