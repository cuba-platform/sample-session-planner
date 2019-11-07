package com.company.sessionplanner.service;

import com.company.sessionplanner.entity.Session;

import java.util.Date;

public interface SessionService {
    String NAME = "sessionplanner_SessionService";

    boolean rescheduleSession(Session session, Date newStartDate);
}