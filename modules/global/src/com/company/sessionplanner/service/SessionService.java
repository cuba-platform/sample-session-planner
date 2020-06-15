package com.company.sessionplanner.service;

import com.company.sessionplanner.entity.Session;

import java.time.LocalDateTime;

public interface SessionService {
    String NAME = "sessionplanner_SessionService";

    Session rescheduleSession(Session session, LocalDateTime newStartDate);
}
