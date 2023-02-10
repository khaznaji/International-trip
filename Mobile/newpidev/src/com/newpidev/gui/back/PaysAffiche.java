package com.newpidev.gui.back;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Pays;
import com.newpidev.services.PaysService;
import com.newpidev.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class PaysAffiche extends Form {

    public static Pays currentPays = null;
    Form previous;
    Button addBtn;


    PickerComponent sortPicker;
    ArrayList<Component> componentModels;
    Label paysLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public PaysAffiche(Form previous) {
        super("Payss", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Pays> listPayss = PaysService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Pays").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listPayss);
            for (Pays pays : listPayss) {
                Component model = makePaysModel(pays);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listPayss.size() > 0) {
            for (Pays pays : listPayss) {
                Component model = makePaysModel(pays);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentPays = null;
            new PaysManipuler(this).show();
        });

    }

    private Container makeModelWithoutButtons(Pays pays) {
        Container paysModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        paysModel.setUIID("containerRounded");


        paysLabel = new Label("Pays : " + pays.getPays());
        paysLabel.setUIID("labelDefault");


        paysModel.addAll(

                paysLabel
        );

        return paysModel;
    }

    private Component makePaysModel(Pays pays) {

        Container paysModel = makeModelWithoutButtons(pays);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("LoginButton");
        editBtn.addActionListener(action -> {
            currentPays = pays;
            new PaysManipuler(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("LoginButton");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce pays ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = PaysService.getInstance().delete(pays.getId());

                if (responseCode == 200) {
                    currentPays = null;
                    dlg.dispose();
                    paysModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du pays. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        paysModel.add(btnsContainer);

        return paysModel;
    }

}