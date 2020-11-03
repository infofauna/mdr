package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.domain.entities.thesaurus.Language;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SAMPLEHEADERFILE", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SHF_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SHF_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SHF_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SHF_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SHF_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SHF_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SHF_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SHF_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SHF_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SHF_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEHEADERFILE")
public class SampleOriginalFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SHF_SPH_ID")
    private Sample parent;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="SHF_LAN_ID")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="SHF_PTV_ID")
    private MIDATProtocolVersion protocolVersion;

    @Column(name="SHF_FILE")
    private byte[] bytes;

    @Column(name="SHF_FILENAME")
    private String fileName;

    @Column(name="SHF_SHEETNAME")
    private String sheetName;

    public Sample getParent() {
        return parent;
    }

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
}
