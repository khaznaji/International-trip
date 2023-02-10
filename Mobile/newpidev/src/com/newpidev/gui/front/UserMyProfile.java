package com.newpidev.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.MainApp;
import com.newpidev.entities.User;
import com.newpidev.utils.Statics;

public class UserMyProfile extends Form {


    public static User currentUser = null;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button editProfileBtn;
    Label emailLabel, rolesLabel, passwordLabel, nomLabel, prenomLabel, telLabel, cinLabel, imageLabel;
    ImageViewer imageIV;

    public UserMyProfile(Form previous) {
        super("Mon profil", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {

        currentUser = MainApp.getSession();

        editProfileBtn = new Button("Modifier mon profil");
        editProfileBtn.setUIID("LoginButton");

        this.add(editProfileBtn);

        this.add(makeUserModel(currentUser));
    }

    private void addActions() {
        editProfileBtn.addActionListener(action -> {
            currentUser = null;
            new UserEditProfile(this).show();
        });
    }

    private Component makeUserModel(User user) {
        Container userModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        userModel.setUIID("containerRounded");


        emailLabel = new Label("Email : " + user.getEmail());
        emailLabel.setUIID("labelDefault");

        rolesLabel = new Label("Roles : " + user.getRoles());
        rolesLabel.setUIID("labelDefault");

        passwordLabel = new Label("Password : " + user.getPassword());
        passwordLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + user.getNom());
        nomLabel.setUIID("labelDefault");

        prenomLabel = new Label("Prenom : " + user.getPrenom());
        prenomLabel.setUIID("labelDefault");

        telLabel = new Label("Tel : " + user.getTel());
        telLabel.setUIID("labelDefault");

        cinLabel = new Label("Cin : " + user.getCin());
        cinLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + user.getImage());
        imageLabel.setUIID("labelDefault");

        if (user.getImage() != null) {
            String url = Statics.USER_IMAGE_URL + user.getImage();
            Image image = URLImage.createToStorage(
                    EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                    url,
                    url,
                    URLImage.RESIZE_SCALE
            );
            imageIV = new ImageViewer(image);
        } else {
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
        }
        imageIV.setFocusable(false);

        userModel.addAll(
                emailLabel, rolesLabel, passwordLabel, nomLabel, prenomLabel, telLabel, cinLabel, imageLabel

                , imageIV

        );

        return userModel;
    }
}