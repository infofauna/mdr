package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;


@Entity
@Table(
        name = "SAMPLELOADCOMMENT", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "SLC_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "SLC_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SLC_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SLC_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SLC_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SLC_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SLC_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SLC_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SLC_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SLC_MODUSER"))
})

@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_SAMPLELOADCOMMENT")
public class SampleComment extends BaseEntity {


    @Column(name = "SLC_SPH_ID", insertable = false, updatable = false)
    private Long sampleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLC_SPH_ID")
    private Sample sample;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLC_CVL_ID_MIDATLOADCMT", updatable = false, insertable = false)
    private ThesaurusValue sampleComment;

    @Column(name = "SLC_CVL_ID_MIDATLOADCMT")
    private Long sampleCommentId;

    @Transient
    @LocalizedProperty("sampleComment")
    private String sampleCommentI18n;


    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public ThesaurusValue getSampleComment() {
        return sampleComment;
    }

    public void setSampleComment(ThesaurusValue sampleComment) {
        this.sampleComment = sampleComment;
    }

    public Long getSampleCommentId() {
        return sampleCommentId;
    }

    public void setSampleCommentId(Long sampleCommentId) {
        this.sampleCommentId = sampleCommentId;
    }

    public String getSampleCommentI18n() {
        return sampleCommentI18n;
    }

    public void setSampleCommentI18n(String sampleCommentI18n) {
        this.sampleCommentI18n = sampleCommentI18n;
    }

}
