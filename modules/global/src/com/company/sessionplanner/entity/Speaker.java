package com.company.sessionplanner.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|firstName,lastName")
@Table(name = "SESSIONPLANNER_SPEAKER", indexes = {
        @Index(name = "IDX_SESSIONPLANNER_SPEAKER_LAST_NAME", columnList = "LAST_NAME")
})
@Entity(name = "sessionplanner_Speaker")
public class Speaker extends StandardEntity {
    private static final long serialVersionUID = -785326450121099162L;

    @NotNull
    @Column(name = "FIRST_NAME", nullable = false)
    protected String firstName;

    @Column(name = "LAST_NAME")
    protected String lastName;

    @Email
    @NotNull
    @Column(name = "EMAIL", nullable = false, unique = true)
    protected String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
