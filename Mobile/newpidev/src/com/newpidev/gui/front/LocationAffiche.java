package com.newpidev.gui.front;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Locationc;
import com.newpidev.services.LocationcService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LocationAffiche extends Form {

    public static Locationc currentLocationc = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label chauffeurLabel, modelLabel, datelocLabel, dureeLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public LocationAffiche(Form previous) {
        super("Locationcs", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Locationc> listLocationcs = LocationcService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher locationc par Model");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Locationc locationc : listLocationcs) {
                if (locationc.getModel().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeLocationcModel(locationc);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listLocationcs.size() > 0) {
            for (Locationc locationc : listLocationcs) {
                Component model = makeLocationcModel(locationc);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentLocationc = null;
            new LocationManipuler(this).show();
        });

    }

    private Container makeModelWithoutButtons(Locationc locationc) {
        Container locationcModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        locationcModel.setUIID("containerRounded");


        chauffeurLabel = new Label("Chauffeur : " + locationc.getChauffeur());
        chauffeurLabel.setUIID("labelDefault");

        modelLabel = new Label("Model : " + locationc.getModel());
        modelLabel.setUIID("labelDefault");

        datelocLabel = new Label("Dateloc : " + new SimpleDateFormat("dd-MM-yyyy").format(locationc.getDateloc()));
        datelocLabel.setUIID("labelDefault");

        dureeLabel = new Label("Duree : " + locationc.getDuree());
        dureeLabel.setUIID("labelDefault");

        chauffeurLabel = new Label("Chauffeur : " + locationc.getChauffeur().getNom());
        chauffeurLabel.setUIID("labelDefault");


        locationcModel.addAll(

                chauffeurLabel, modelLabel, datelocLabel, dureeLabel
        );

        return locationcModel;
    }

    private Component makeLocationcModel(Locationc locationc) {

        Container locationcModel = makeModelWithoutButtons(locationc);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("LoginButton");
        editBtn.addActionListener(action -> {
            currentLocationc = locationc;
            new LocationManipuler(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("LoginButton");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce locationc ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = LocationcService.getInstance().delete(locationc.getId());

                if (responseCode == 200) {
                    currentLocationc = null;
                    dlg.dispose();
                    locationcModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du locationc. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        locationcModel.add(btnsContainer);

        return locationcModel;
    }

}