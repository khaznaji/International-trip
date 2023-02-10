package com.newpidev.gui.front;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.MainApp;
import com.newpidev.entities.User;
import com.newpidev.services.UserService;
import com.newpidev.utils.Statics;

import java.io.IOException;

public class UserEditProfile extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    User currentUser;

    Label emailLabel, nomLabel, prenomLabel, telLabel, cinLabel, imageLabel;
    TextField
            emailTF,
            nomTF,
            prenomTF,
            telTF,
            cinTF;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public UserEditProfile(Form previous) {
        super("Modifier mon profil", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {
        currentUser = MainApp.getSession();


        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");

        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");

        prenomLabel = new Label("Prenom : ");
        prenomLabel.setUIID("labelDefault");
        prenomTF = new TextField();
        prenomTF.setHint("Tapez le prenom");

        telLabel = new Label("Tel : ");
        telLabel.setUIID("labelDefault");
        telTF = new TextField();
        telTF.setHint("Tapez le tel");

        cinLabel = new Label("Cin : ");
        cinLabel.setUIID("labelDefault");
        cinTF = new TextField();
        cinTF.setHint("Tapez le cin");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");


        emailTF.setText(currentUser.getEmail());
        nomTF.setText(currentUser.getNom());
        prenomTF.setText(currentUser.getPrenom());
        telTF.setText(currentUser.getTel());
        cinTF.setText(currentUser.getCin());


        if (currentUser.getImage() != null) {
            selectedImage = currentUser.getImage();
            String url = Statics.USER_IMAGE_URL + currentUser.getImage();
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


        manageButton = new Button("Modifier");
        manageButton.setUIID("LoginButton");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                emailLabel, emailTF,
                nomLabel, nomTF,
                prenomLabel, prenomTF,
                telLabel, telTF,
                cinLabel, cinTF,


                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = UserService.getInstance().edit(
                        new User(
                                currentUser.getId(),


                                emailTF.getText(),
                                "ROLE_USER",
                                "",
                                nomTF.getText(),
                                prenomTF.getText(),
                                telTF.getText(),
                                cinTF.getText(),
                                selectedImage

                        ), imageEdited
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Profil modifié avec succes", new Command("Ok"));
                    MainApp.setSession(new User(
                            currentUser.getId(),


                            emailTF.getText(),
                            "ROLE_USER",
                            "",
                            nomTF.getText(),
                            prenomTF.getText(),
                            telTF.getText(),
                            cinTF.getText(),
                            selectedImage
                    ));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de user. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((UserMyProfile) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Email vide", new Command("Ok"));
            return false;
        }

        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }


        if (prenomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prenom vide", new Command("Ok"));
            return false;
        }


        if (telTF.getText().equals("")) {
            Dialog.show("Avertissement", "Tel vide", new Command("Ok"));
            return false;
        }


        if (cinTF.getText().equals("")) {
            Dialog.show("Avertissement", "Cin vide", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}