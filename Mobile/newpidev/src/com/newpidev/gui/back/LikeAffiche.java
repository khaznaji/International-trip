package com.newpidev.gui.back;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.newpidev.entities.Like;
import com.newpidev.services.LikeService;

import java.util.ArrayList;

public class LikeAffiche extends Form {

    public static Like currentLike = null;
    Form previous;
    Label divLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public LikeAffiche(Form previous) {
        super("Likes", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {


        ArrayList<Like> listLikes = LikeService.getInstance().getAll();


        if (listLikes.size() > 0) {
            for (Like like : listLikes) {
                Component model = makeLikeModel(like);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }


    private Container makeModelWithoutButtons(Like like) {
        Container likeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        likeModel.setUIID("containerRounded");


        divLabel = new Label("Div : " + like.getDiv());
        divLabel.setUIID("labelDefault");

        divLabel = new Label("Div : " + like.getDiv().getNom());
        divLabel.setUIID("labelDefault");


        likeModel.addAll(

                divLabel
        );

        return likeModel;
    }

    private Component makeLikeModel(Like like) {

        Container likeModel = makeModelWithoutButtons(like);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        likeModel.add(btnsContainer);

        return likeModel;
    }

}