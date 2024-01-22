package com.cus.healthcare.system.entity;

import lombok.EqualsAndHashCode;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UserRoleHasUserKey implements Serializable {
    @Column(name = "user_id")
    private String user;
    @Column(name = "role_id")
    private long userRole;
}