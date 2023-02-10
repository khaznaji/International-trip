/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Fav;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public interface IFavServices {
     public int ajouterFav(Fav f);
     public int supprimerFav(Fav f);
     public ArrayList<Fav> afficherFav();
     public ObservableList<Fav> getDataTeam();
    
}
