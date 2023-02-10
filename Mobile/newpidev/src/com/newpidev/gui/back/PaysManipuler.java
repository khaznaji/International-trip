package com.newpidev.gui.back;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Pays;
import com.newpidev.services.PaysService;

public class PaysManipuler extends Form {


    Pays currentPays;

    TextField paysTF;
    Label paysLabel;


    Button manageButton;

    Form previous;

    public PaysManipuler(Form previous) {
        super(PaysAffiche.currentPays == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentPays = PaysAffiche.currentPays;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


        paysLabel = new Label("Pays : ");
        paysLabel.setUIID("labelDefault");
        paysTF = new TextField();
        paysTF.setHint("Tapez le pays");


        if (currentPays == null) {


            manageButton = new Button("Ajouter");
        } else {
            paysTF.setText(currentPays.getPays());


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("LoginButton");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                paysLabel, paysTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentPays == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = PaysService.getInstance().add(
                            new Pays(


                                    paysTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Pays ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de pays. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = PaysService.getInstance().edit(
                            new Pays(
                                    currentPays.getId(),


                                    paysTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Pays modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de pays. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((PaysAffiche) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (paysTF.getText().equals("")) {
            Dialog.show("Avertissement", "Pays vide", new Command("Ok"));
            return false;
        }


        return true;
    }
}