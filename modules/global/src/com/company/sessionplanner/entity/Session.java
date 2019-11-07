package com.company.sessionplanner.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@NamePattern("%s|description")
@Table(name = "SESSIONPLANNER_SESSION")
@Entity(name = "sessionplanner_Session")
public class Session extends StandardEntity {
    private static final long serialVersionUID = 7497709835929871175L;

    @NotNull
    @Column(name = "TOPIC", nullable = false)
    protected String topic;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "START_DATE", nullable = false)
    protected Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    protected Date endDate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SPEAKER_ID")
    protected Speaker speaker;

    @Column(name = "DESCRIPTION", length = 2000)
    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @PrePersist
    @PreUpdate
    public void updateEndDate() {
        endDate = calculateEndDate(startDate);
    }

    public static Date calculateEndDate(Date startDate) {
        return Date.from(startDate.toInstant().plus(1, ChronoUnit.HOURS));
    }
}