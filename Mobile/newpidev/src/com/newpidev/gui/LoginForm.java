package com.newpidev.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.newpidev.MainApp;
import com.newpidev.entities.User;
import com.newpidev.gui.back.AccueilAdmin;
import com.newpidev.gui.front.AccueilClient;
import com.newpidev.services.UserService;

public class LoginForm extends Form {

    Resources res;

    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");

        res = theme;

        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome, ", "WelcomeWhite"),
                new Label("Choose where to start", "WelcomeBlue")
        );

        TextField emailTF = new TextField("");
        emailTF.setHint("Tapez votre email");
        emailTF.setUIID("otherTF");

        TextField passwordTF = new TextField("", "Tapez votre mot de passe", 20, TextField.PASSWORD);
        passwordTF.setUIID("otherTF");

        Button connectBtn = new Button("Connexion");
        connectBtn.setUIID("LoginButton");
        connectBtn.addActionListener(l -> connexion(emailTF.getText(), passwordTF.getText()));

        Label registerLabel = new Label("Besoin d'un compte ?");
        registerLabel.setUIID("otherLabel");

        Button registerBtn = new Button("Inscription");
        registerBtn.setUIID("LoginButton");
        registerBtn.addActionListener(l -> new RegisterForm(this).show());

        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        Container buttons = BoxLayout.encloseY(
                emailTF, passwordTF, connectBtn, registerLabel, registerBtn
        );

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                buttons
        );
        add(BorderLayout.CENTER, by);

        by.setScrollableY(true);
        by.setScrollVisible(false);

        /*Button backendBtn = new Button("Back");
        backendBtn.addActionListener(l -> new AccueilAdmin(res).show());

        this.add(backendBtn);*/
    }

    private void connexion(String email, String password) {
        User user = UserService.getInstance().checkCredentials(email, password);

        if (user != null) {
            MainApp.setSession(user);
            if (user.getRoles().equals("ROLE_ADMIN")) {
                new AccueilAdmin(res).show();
            } else {
                new AccueilClient(res).show();
            }
        } else {
            Dialog.show("Erreur", "Identifiants invalides", new Command("Ok"));
        }
    }
}
