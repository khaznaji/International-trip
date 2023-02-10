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
public class Div {
     private int iddiv;
    private String nomdiv;
    private String typediv;
    private String adressediv;
    private int numdiv;

    public Div(int iddiv, String nomdiv,  String typediv, String adressediv, int numdiv) {
        this.iddiv = iddiv;
        this.nomdiv = nomdiv;
        this.typediv = typediv;
        this.adressediv = adressediv;
        this.numdiv = numdiv;
    }

   

    public Div() {
    }

    public int getIddiv() {
        return iddiv;
    }

    public void setIddiv(int iddiv) {
        this.iddiv = iddiv;
    }

    public String getNomdiv() {
        return nomdiv;
    }

    public void setNomdiv(String nomdiv) {
        this.nomdiv = nomdiv;
    }

    public String getTypediv() {
        return typediv;
    }

    public void setTypediv(String typediv) {
        this.typediv = typediv;
    }

    public String getAdressediv() {
        return adressediv;
    }

    public void setAdressediv(String adressediv) {
        this.adressediv = adressediv;
    }

    public int getNumdiv() {
        return numdiv;
    }

    public void setNumdiv(int numdiv) {
        this.numdiv = numdiv;
    }

    @Override
    public String toString() {
        return "Div{" + "iddiv=" + iddiv + ", nomdiv=" + nomdiv + ", typediv=" + typediv + ", adressediv=" + adressediv + ", numdiv=" + numdiv + '}';
    }

   
    
   

    
}
