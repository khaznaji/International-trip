/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newpidev.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.MainApp;
import com.newpidev.entities.User;
import com.newpidev.utils.Statics;

/**
 * @author MSI GF63
 */
public abstract class SideMenuBaseFormFront extends Form {

    public static User currentUser = null;
    Resources theme = UIManager.initFirstTheme("/theme");
    Label emailLabel, rolesLabel, passwordLabel, nomLabel, prenomLabel, telLabel, cinLabel, imageLabel;
    ImageViewer imageIV;

    public SideMenuBaseFormFront(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public void setupSideMenu(Resources theme) {
        Label username = new Label("Client");
        username.setUIID("WelcomeWhite");

        Container sidemenuTop = BoxLayout.encloseY(
                username
        );
        sidemenuTop.setUIID("SidemenuTop");

        addGUIs();

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Mon profil", FontImage.MATERIAL_VERIFIED_USER, e -> new UserMyProfile(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Hebergements", FontImage.MATERIAL_HOUSE, e -> new HebergementAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Payss", FontImage.MATERIAL_MAP, e -> new PaysAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Chauffeurs", FontImage.MATERIAL_PERSON, e -> new ChauffeurAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Locations", FontImage.MATERIAL_CAR_RENTAL, e -> new LocationAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Evenements", FontImage.MATERIAL_EVENT, e -> new EvenementAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Reservations", FontImage.MATERIAL_BOOK, e -> new ReservationAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Divertissement", FontImage.MATERIAL_GAMES, e -> new DivAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Likes", FontImage.MATERIAL_FAVORITE, e -> new LikeAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Types", FontImage.MATERIAL_CATEGORY, e -> new TypeAffiche(this).show());
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> showOtherForm(theme));
    }

    private void addGUIs() {
        currentUser = MainApp.getSession();
        User user = currentUser;

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

        this.add(userModel);
    }

    protected abstract void showOtherForm(Resources res);
}
