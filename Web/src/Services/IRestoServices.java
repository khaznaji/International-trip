/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Resto;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public interface IRestoServices {
     public int ajouterResto(Resto r);
     public int supprimerResto(Resto r);
     public int modifierResto(Resto r);
     public ArrayList<Resto> afficherResto();
     public ObservableList<Resto> getDataTeam();
     public ArrayList<Resto> rechercherResto(String R,String C);
    
}
