package com.newpidev.gui.front;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Chauffeur;
import com.newpidev.services.ChauffeurService;

import java.util.ArrayList;

public class ChauffeurAffiche extends Form {

    public static Chauffeur currentChauffeur = null;
    Form previous;
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label nomLabel, prenomLabel, sexeLabel, numLabel, disponibiliteLabel;
    Container btnsContainer;

    public ChauffeurAffiche(Form previous) {
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


        ArrayList<Chauffeur> listChauffeurs = ChauffeurService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher chauffeur par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Chauffeur chauffeur : listChauffeurs) {
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
            for (Chauffeur chauffeur : listChauffeurs) {
                Component model = makeChauffeurModel(chauffeur);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donn??e"));
        }
    }

    private void addActions() {

    }

    private Container makeModelWithoutButtons(Chauffeur chauffeur) {
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

    private Component makeChauffeurModel(Chauffeur chauffeur) {

        Container chauffeurModel = makeModelWithoutButtons(chauffeur);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        chauffeurModel.add(btnsContainer);

        return chauffeurModel;
    }

}