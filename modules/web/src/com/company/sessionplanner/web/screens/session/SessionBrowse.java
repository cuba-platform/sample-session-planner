package com.company.sessionplanner.web.screens.session;

import com.company.sessionplanner.entity.Session;
import com.company.sessionplanner.service.SessionService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.calendar.EntityCalendarEvent;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.time.LocalDateTime;

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

    @Inject
    private CollectionLoader<Session> sessionsDl;

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        Screen screen = screenBuilders.editor(Session.class, this)
                .editEntity((Session) event.getEntity())
                .withLaunchMode(OpenMode.DIALOG).build();
        screen.addAfterCloseListener(afterCloseEvent -> {
            sessionsDl.load();
        });
        screen.show();
    }

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {

        Session session = ((EntityCalendarEvent<Session, LocalDateTime>)event.getCalendarEvent()).getEntity();

        if (!sessionService.rescheduleSession(session, event.getNewStart())) {
            notifications.create(Notifications.NotificationType.WARNING)
            .withCaption("Session "+session.getTopic()+" cannot be rescheduled to "+event.getNewStart()+" due to a conflict")
            .show();
        }
        sessionsDl.load();
    }

}
