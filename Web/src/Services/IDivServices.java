/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Div;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public interface IDivServices {
     public int ajouterDiv(Div d);
     public int supprimerDiv(Div d);
     public int modifierDiv(Div d);
     public ArrayList<Div> afficherDiv();
     public ObservableList<Div> getDataTeam();
     public ArrayList<Div> rechercherDiv(String R,String C);
    
}
