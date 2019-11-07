package com.company.sessionplanner.web.screens.session;

import com.company.sessionplanner.service.SessionService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.calendar.EntityCalendarEvent;
import com.haulmont.cuba.gui.screen.*;
import com.company.sessionplanner.entity.Session;

import javax.inject.Inject;

@UiController("sessionplanner_Session.browse")
@UiDescriptor("session-browse.xml")
@LookupComponent("sessionsTable")
@LoadDataBeforeShow
public class SessionBrowse extends StandardLookup<Session> {

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private SessionService sessionService;

    @Inject
    private Notifications notifications;

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventClick(Calendar.CalendarEventClickEvent event) {
        Screen screen = screenBuilders.editor(Session.class, this)
                .editEntity((Session) event.getEntity())
                .withLaunchMode(OpenMode.DIALOG).build();
        screen.addAfterCloseListener(afterCloseEvent -> {
            getScreenData().loadAll();
        });
        screen.show();
    }

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent event) {

        Session session = ((EntityCalendarEvent<Session>)event.getCalendarEvent()).getEntity();

        if (!sessionService.rescheduleSession(session, event.getNewStart())) {
            notifications.create(Notifications.NotificationType.WARNING)
            .withCaption("Session "+session.getTopic()+" cannot be rescheduled to "+event.getNewStart()+" due to a conflict")
            .show();
        }

        getScreenData().loadAll();

    }



}