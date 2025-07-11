package com.ums.usermanagementportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="permissions")
public class permissions {
    @Id
    private Integer perm_id;

    @Column(name = "can_create")
    private Boolean canCreate;

    @Column(name = "can_read")
    private Boolean canRead;

    @Column(name = "can_update")
    private Boolean canUpdate;

    @Column(name = "can_delete")
    private Boolean canDelete;

    @Column(name = "can_assign_task")
    private Boolean canAssignTask;

    public permissions(int perm_id, Boolean canCreate, Boolean canRead, Boolean canUpdate, Boolean canDelete, Boolean canAssignTask) {
        this.perm_id = perm_id;
        this.canCreate = canCreate;
        this.canRead = canRead;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
        this.canAssignTask = canAssignTask;
    }

    public Integer getPerm_id() {
        return perm_id;
    }

    public void setPerm_id(Integer perm_id) {
        this.perm_id = perm_id;
    }

    public Boolean getCanCreate() {
        return canCreate;
    }

    public void setCanCreate(Boolean canCreate) {
        this.canCreate = canCreate;
    }

    public Boolean getCanRead() {
        return canRead;
    }

    public void setCanRead(Boolean canRead) {
        this.canRead = canRead;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Boolean getCanAssignTask() {
        return canAssignTask;
    }

    public void setCanAssignTask(Boolean canAssignTask) {
        this.canAssignTask = canAssignTask;
    }

    public permissions(){

    }
}
