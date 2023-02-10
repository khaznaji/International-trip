package com.newpidev.gui.back;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.services.ChauffeurService;

import java.util.ArrayList;

public class ChaffeurAffiche extends Form {

    public static com.newpidev.entities.Chauffeur currentChauffeur = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label nomLabel, prenomLabel, sexeLabel, numLabel, disponibiliteLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ChaffeurAffiche(Form previous) {
        super("Chauffeurs", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

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
        addBtn = new Button("Ajouter");
        addBtn.setUIID("LoginButton");
        this.add(addBtn);


        ArrayList<com.newpidev.entities.Chauffeur> listChauffeurs = ChauffeurService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher chauffeur par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (com.newpidev.entities.Chauffeur chauffeur : listChauffeurs) {
                if (chauffeur.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeChauffeurModel(chauffeur);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listChauffeurs.size() > 0) {
            for (com.newpidev.entities.Chauffeur chauffeur : listChauffeurs) {
                Component model = makeChauffeurModel(chauffeur);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentChauffeur = null;
            new ChauffeurManipuler(this).show();
        });

    }

    private Container makeModelWithoutButtons(com.newpidev.entities.Chauffeur chauffeur) {
        Container chauffeurModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        chauffeurModel.setUIID("containerRounded");


        nomLabel = new Label("Nom : " + chauffeur.getNom());
        nomLabel.setUIID("labelDefault");

        prenomLabel = new Label("Prenom : " + chauffeur.getPrenom());
        prenomLabel.setUIID("labelDefault");

        sexeLabel = new Label("Sexe : " + chauffeur.getSexe());
        sexeLabel.setUIID("labelDefault");

        numLabel = new Label("Num : " + chauffeur.getNum());
        numLabel.setUIID("labelDefault");

        disponibiliteLabel = new Label("Disponibilite : " + chauffeur.getDisponibilite());
        disponibiliteLabel.setUIID("labelDefault");


        chauffeurModel.addAll(

                nomLabel, prenomLabel, sexeLabel, numLabel, disponibiliteLabel
        );

        return chauffeurModel;
    }

    private Component makeChauffeurModel(com.newpidev.entities.Chauffeur chauffeur) {

        Container chauffeurModel = makeModelWithoutButtons(chauffeur);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("LoginButton");
        editBtn.addActionListener(action -> {
            currentChauffeur = chauffeur;
            new ChauffeurManipuler(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("LoginButton");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce chauffeur ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ChauffeurService.getInstance().delete(chauffeur.getId());

                if (responseCode == 200) {
                    currentChauffeur = null;
                    dlg.dispose();
                    chauffeurModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du chauffeur. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        chauffeurModel.add(btnsContainer);

        return chauffeurModel;
    }

}