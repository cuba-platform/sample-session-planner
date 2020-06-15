package com.company.sessionplanner.web.screens.session;

import com.company.sessionplanner.entity.Session;
import com.company.sessionplanner.service.SessionService;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.CloseAction;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.OpenMode;
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
    private CollectionContainer<Session> sessionsDc;

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        screenBuilders.editor(Session.class, this)
                .withScreenClass(SessionEdit.class)
                .editEntity((Session) event.getEntity())
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(afterCloseEvent -> {
                    CloseAction closeAction = afterCloseEvent.getCloseAction();
                    if (closeAction.equals(WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                        Session entity = afterCloseEvent.getScreen().getEditedEntity();
                        sessionsDc.replaceItem(entity);
                    }
                }).show();
    }

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {
        Session session = sessionService.rescheduleSession((Session) event.getEntity(), event.getNewStart());
        sessionsDc.replaceItem(session);
    }

}
