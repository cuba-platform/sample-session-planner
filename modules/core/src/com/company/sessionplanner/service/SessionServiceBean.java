package com.company.sessionplanner.service;

import com.company.sessionplanner.entity.Session;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;

@Service(SessionService.NAME)
public class SessionServiceBean implements SessionService {

    @Inject
    private DataManager dataManager;

    @Override
    public boolean rescheduleSession(Session session, LocalDateTime newStartDate) {

        LocalDateTime newEndDate = Session.calculateEndDate(newStartDate);

        LoadContext<Session> loadContext = LoadContext.create(Session.class);
        loadContext.setQueryString("select s from sessionplanner_Session s where " +
                "s.startDate < :newEndDate and s.endDate > :newStartDate " +
                "and s.speaker = :speaker " +
                "and s.id <> :sessionId")
                .setParameter("newStartDate", newStartDate)
                .setParameter("newEndDate", newEndDate)
                .setParameter("speaker", session.getSpeaker())
                .setParameter("sessionId", session.getId());

        long sessionsSameTime = dataManager.getCount(loadContext);

        if (sessionsSameTime == 0) {
            session.setStartDate(newStartDate);
            dataManager.commit(session);
            return true;
        }

        return false;
    }
}
