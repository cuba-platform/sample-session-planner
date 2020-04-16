package com.company.sessionplanner.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NamePattern("%s|description")
@Table(name = "SESSIONPLANNER_SESSION")
@Entity(name = "sessionplanner_Session")
public class Session extends StandardEntity {
    private static final long serialVersionUID = 7497709835929871175L;

    @NotNull
    @Column(name = "TOPIC", nullable = false)
    protected String topic;

    @Column(name = "START_DATE", nullable = false)
    protected @NotNull LocalDateTime startDate;

    @Column(name = "END_DATE")
    protected LocalDateTime endDate;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SPEAKER_ID")
    protected Speaker speaker;

    @Column(name = "DESCRIPTION", length = 2000)
    protected String description;

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

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

    public static LocalDateTime calculateEndDate(LocalDateTime startDate) {
        return startDate.plusHours(1);
    }
}
