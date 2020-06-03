package com.company.sessionplanner.service;

import com.company.sessionplanner.entity.Session;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service(SessionService.NAME)
public class SessionServiceBean implements SessionService {

    @Inject
    private DataManager dataManager;

    @Override
    public Session rescheduleSession(Session session, LocalDateTime newStartDate) {

        LocalDateTime dayStart = newStartDate.truncatedTo(ChronoUnit.DAYS).withHour(8);
        LocalDateTime dayEnd = newStartDate.truncatedTo(ChronoUnit.DAYS).withHour(19);

        Long sessionsSameTime = dataManager.loadValue("select count(s) from sessionplanner_Session s " +
                "where (s.startDate between :dayStart and :dayEnd) " +
                "and s.speaker = :speaker " +
                "and s.id <> :sessionId", Long.class)
                .parameter("dayStart", dayStart)
                .parameter("dayEnd", dayEnd)
                .parameter("speaker", session.getSpeaker())
                .parameter("sessionId", session.getId())
                .one();
        if (sessionsSameTime >= 2) {
            return session;
        }
        session.setStartDate(newStartDate);
        return dataManager.commit(session);
    }
}
