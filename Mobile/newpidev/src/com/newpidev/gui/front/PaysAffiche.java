package com.newpidev.gui.front;

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
    PickerComponent sortPicker;
    ArrayList<Component> componentModels;
    Label paysLabel;
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


        paysModel.add(btnsContainer);

        return paysModel;
    }

}