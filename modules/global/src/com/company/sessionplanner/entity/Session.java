package com.company.sessionplanner.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@NamePattern("%s|topic")
@Table(name = "SESSIONPLANNER_SESSION")
@Entity(name = "sessionplanner_Session")
public class Session extends StandardEntity {
    private static final long serialVersionUID = 7497709835929871175L;

    @NotNull
    @Column(name = "TOPIC", nullable = false)
    protected String topic;

    @Column(name = "START_DATE", nullable = false)
    protected @NotNull LocalDateTime startDate;

    @NotNull
    @Column(name = "DURATION", nullable = false)
    private @Positive Integer duration;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SPEAKER_ID")
    protected Speaker speaker;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
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

    @Transient
    @MetaProperty(related = {"startDate", "duration"})
    public LocalDateTime getEndDate() {
        return (startDate != null && duration != null) ? startDate.plusHours(duration) : null;
    }

}
