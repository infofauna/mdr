package ch.cscf.jeci.domain.entities.security;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity()
@Table(name = "ADMIN_USER_ROLE_GROUP", schema = "appmanager")
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "ARG_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "ARG_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ARG_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "ARG_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "ARG_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "ARG_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "ARG_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "ARG_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "ARG_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "ARG_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "APPMANAGER.SEQ_ADMIN_USER_ROLE_GROUP")
public class RoleGroup extends BaseEntity {

    /*
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARG_USR_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;
    */

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARG_APR_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "ARG_AGR_ID")
    private Group group;

    @Column(name = "ARG_WRITETABLE")
    @Type(type = "yes_no")
    private Boolean writable;

    @Column(name = "ARG_USR_ID")
    private Long userId;

    @Transient
    private Long roleId;

    @Transient
    private Long groupId;


    @Transient
    private Boolean deleted;

    public RoleGroup() {
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    public Boolean getDeleted() {
        return deleted;
    }

    public boolean isDeleted() {
        if (this.deleted == null){
            setDeleted(false);
        }else{
            setDeleted(true);
        }
        return this.deleted;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


}
