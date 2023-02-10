/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

/**
 *
 * @author HP
 */
public class Fav {
     private String nomfav;
    private int numfav;

    public Fav() {
    }
    

    public Fav(String nomfav, int numfav) {
        this.nomfav = nomfav;
        this.numfav = numfav;
    }
  

    
    public String getNomfav() {
        return nomfav;
    }

    public void setNomfav(String nomfav) {
        this.nomfav = nomfav;
    }

    public int getNumfav() {
        return numfav;
    }

    public void setNumfav(int numfav) {
        this.numfav = numfav;
    }

    @Override
    public String toString() {
        return "Fav{" + "nomfav=" + nomfav + ", numfav=" + numfav + '}';
    }
    
    
}
