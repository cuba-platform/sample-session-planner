package com.company.sessionplanner.web.screens.session;

import com.company.sessionplanner.entity.Session;
import com.company.sessionplanner.service.SessionService;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.EditorScreenFacet;
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
    private SessionService sessionService;

    @Inject
    private CollectionContainer<Session> sessionsDc;
    @Inject
    private EditorScreenFacet<Session, SessionEdit> sessionEditDialog;

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        sessionEditDialog.setEntityProvider(() -> (Session)event.getEntity());
        sessionEditDialog.show();
    }

    @Subscribe("sessionsCalendar")
    private void onSessionsCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {
        Session session = sessionService.rescheduleSession((Session) event.getEntity(), event.getNewStart());
        sessionsDc.replaceItem(session);
    }

}
