package com.company.sessionplanner.service;

import com.company.sessionplanner.entity.Session;

import java.time.LocalDateTime;

public interface SessionService {
    String NAME = "sessionplanner_SessionService";

    boolean rescheduleSession(Session session, LocalDateTime newStartDate);
}
