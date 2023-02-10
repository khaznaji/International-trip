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
public class Resto {
      private int idrest ; 
      private String nomrest  ;
      private String adresserest  ;
      private String menurest  ;
      private int numrest ; 
      private String imgrest  ;

    public Resto() {
    }

    public Resto(int idrest, String nomrest, String adresserest, String menurest,int numrest, String imgrest) {
        this.idrest = idrest;
        this.nomrest = nomrest;
        this.adresserest = adresserest;
        this.menurest = adresserest;
        this.numrest = numrest;
        this.imgrest = imgrest;
    }

    public int getIdrest() {
        return idrest;
    }

    public String getNomrest() {
        return nomrest;
    }

    public String getAdresserest() {
        return adresserest;
    }

    public String getMenurest() {
        return menurest;
    }
    public int getNumrest() {
        return numrest;
    }

    public String getImgrest() {
        return imgrest;
    }

    public void setIdrest(int idrest) {
        this.idrest = idrest;
    }

    public void setNomrest(String nomrest) {
        this.nomrest = nomrest;
    }

    public void setAdresserest(String adresserest) {
        this.adresserest = adresserest;
    }

    public void setMenurest(String menurest) {
        this.menurest = menurest;
    }
    public void setNumrest(int numrest) {
        this.numrest = numrest;
    }

    public void setImgrest(String imgrest) {
        this.imgrest = imgrest;
    }

    @Override
    public String toString() {
        return "Resto{" + "idrest=" + idrest + ", nomrest=" + nomrest + ", adresserest=" + adresserest + ", menurest=" + menurest + ", numrest=" + numrest + ", imgrest=" + imgrest + '}';
    }

   

    
}
