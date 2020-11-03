package ch.cscf.jeci.domain.entities.base;

import ch.cscf.jeci.domain.entities.security.User;
import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: henryp
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Transient
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Type(type="ch.cscf.jeci.persistence.hibernate.EntityStatusUserType")
    private EntityStatus status;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="idSeq")
    protected Long id;

    @CreatedDate
    private Date creationDate;

    private Date dbCreationDate;

    @LastModifiedDate
    private Date updateDate;

    @Column(nullable = false)
    private Date dbUpdateDate;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User creationUser;

    private String dbCreationUser;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updateUser;

    private String dbUpdateUser;

    @PrePersist
    public void prePersist(){
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        setCreationUser(currentUser);
        setCreationDate(new Date());
    }

    @PreUpdate
    public void preUpdate(){
        User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
        setUpdateUser(currentUser);
        setUpdateDate(new Date());
    }

    /**
     * Equals method, based on the class and the ID.
     * TODO: improve as it's not really correct ! Woked when all ids were genwerated from the same table/sequence but not here !
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BaseEntity)) {
            return false;
        }

        if(this.id==null){
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        if(id==null){
            return super.hashCode();
        }
        return id.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @JsonIgnore
    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    @JsonIgnore
    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    @JsonIgnore
    public Date getDbCreationDate() {
        return dbCreationDate;
    }

    @JsonIgnore
    public Date getDbUpdateDate() {
        return dbUpdateDate;
    }

    @JsonIgnore
    public String getDbCreationUser() {
        return dbCreationUser;
    }

    @JsonIgnore
    public String getDbUpdateUser() {
        return dbUpdateUser;
    }

    @JsonIgnore
    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

}
