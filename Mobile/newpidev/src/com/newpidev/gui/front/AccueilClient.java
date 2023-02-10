package com.newpidev.gui.front;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.newpidev.gui.LoginForm;

public class AccueilClient extends SideMenuBaseFormFront {

    public AccueilClient(Resources theme) {
        super("Profil", BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setUIID("Toolbar");
        tb.setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent =
                BorderLayout.north(
                                BorderLayout.west(menuButton)
                        ).
                        add(BorderLayout.CENTER, space).
                        add(BorderLayout.SOUTH,
                                FlowLayout.encloseIn(
                                        new Label("My profile", "WelcomeWhite")
                                ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);

        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        setupSideMenu(theme);
    }


    @Override
    protected void showOtherForm(Resources theme) {
        new LoginForm(theme).show();
    }
}
