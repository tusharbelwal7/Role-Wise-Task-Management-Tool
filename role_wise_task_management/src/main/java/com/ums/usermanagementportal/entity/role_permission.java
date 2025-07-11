package com.ums.usermanagementportal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="role_permission")
public class role_permission {
    @Id
    private Integer role_id;
    private Integer perm_id;

    public role_permission(int role_id, int perm_id) {
        this.role_id = role_id;
        this.perm_id = perm_id;
    }

    public role_permission(){

    }

    public Integer getPerm_id() {
        return perm_id;
    }

    public void setPerm_id(Integer perm_id) {
        this.perm_id = perm_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}
