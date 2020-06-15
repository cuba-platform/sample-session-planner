package com.company.sessionplanner.web.screens.speaker;

import com.haulmont.cuba.gui.screen.*;
import com.company.sessionplanner.entity.Speaker;

@UiController("sessionplanner_Speaker.browse")
@UiDescriptor("speaker-browse.xml")
@LookupComponent("speakersTable")
@LoadDataBeforeShow
public class SpeakerBrowse extends StandardLookup<Speaker> {

}
