package com.newpidev.gui.back;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.newpidev.MainApp;
import com.newpidev.entities.User;
import com.newpidev.gui.LoginForm;
import com.newpidev.utils.Statics;

public class AccueilAdmin extends SideMenuBaseFormBack {

    public AccueilAdmin(Resources theme) {
        super("List of users", BoxLayout.y());
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
