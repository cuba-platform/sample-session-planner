package com.company.sessionplanner.web.screens.speaker;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.screen.*;
import com.company.sessionplanner.entity.Speaker;

import javax.inject.Inject;

@UiController("sessionplanner_Speaker.browse")
@UiDescriptor("speaker-browse.xml")
@LookupComponent("speakersTable")
@LoadDataBeforeShow
public class SpeakerBrowse extends StandardLookup<Speaker> {

    @Inject
    private GroupTable<Speaker> speakersTable;

    @Inject
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(InitEvent event) {
        speakersTable.addGeneratedColumn("photo", this::renderPhotoComponent);
    }

    private Component renderPhotoComponent(Speaker speaker) {
        FileDescriptor photo = speaker.getPhoto();
        if (photo == null) {
            return null;
        }
        Image image = createSmallPhoto();
        image.setSource(FileDescriptorResource.class).setFileDescriptor(photo);
        return image;
    }

    private Image createSmallPhoto() {
        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.CONTAIN);
        image.setHeight("40");
        image.setWidth("40");
        return image;
    }

}
