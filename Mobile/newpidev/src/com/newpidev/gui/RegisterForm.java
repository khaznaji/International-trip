package com.newpidev.gui;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.newpidev.entities.User;
import com.newpidev.services.UserService;

import java.io.IOException;

public class RegisterForm extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;

    Label emailLabel;
    Label passwordLabel;
    Label nomLabel;
    Label prenomLabel;
    Label telLabel;
    Label cinLabel;
    Label imageLabel;
    TextField emailTF;
    TextField passwordTF;
    TextField nomTF;
    TextField prenomTF;
    TextField telTF;
    TextField cinTF;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public RegisterForm(Form previous) {
        super("Inscription", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {


        Label loginLabel = new Label("Vous avez deja un compte ?");

        Button loginBtn = new Button("Login");
        loginBtn.setUIID("LoginButton");
        loginBtn.addActionListener(l -> new LoginForm(theme).show());


        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");

        passwordLabel = new Label("Password : ");
        passwordLabel.setUIID("labelDefault");
        passwordTF = new TextField();
        passwordTF.setHint("Tapez le password");

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


        imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


        manageButton = new Button("S'inscrire");
        manageButton.setUIID("LoginButton");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                emailLabel, emailTF,
                passwordLabel, passwordTF,
                nomLabel, nomTF,
                prenomLabel, prenomTF,
                telLabel, telTF,
                cinLabel, cinTF,


                manageButton,
                loginLabel, loginBtn
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
                int responseCode = UserService.getInstance().add(
                        new User(
                                emailTF.getText(),
                                "ROLE_CLIENT",
                                passwordTF.getText(),
                                nomTF.getText(),
                                prenomTF.getText(),
                                telTF.getText(),
                                cinTF.getText(),
                                selectedImage
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Inscription effectué avec succes", new Command("Ok"));
                    previous.showBack();
                } else if (responseCode == 203) {
                    Dialog.show("Erreur", "Email deja utilisé", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de user. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private boolean controleDeSaisie() {


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir email", new Command("Ok"));
            return false;
        }

        if (passwordTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir password", new Command("Ok"));
            return false;
        }


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir nom", new Command("Ok"));
            return false;
        }


        if (prenomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir prenom", new Command("Ok"));
            return false;
        }


        if (telTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir tel", new Command("Ok"));
            return false;
        }


        if (cinTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir cin", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}