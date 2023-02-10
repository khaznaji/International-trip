/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Div;
import Entites.Resto;
import Utils.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public class RestoServices implements IRestoServices {
    Connection connx ;
    Statement ste;
    private PreparedStatement pst;
     public RestoServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
     @Override   
    public int ajouterResto(Resto r) {
        int x = 0;
        try {
           String sql ="INSERT INTO `resto`( `idrest`, `nomrest`, `adresserest`, `menurest`,`numrest`, `imgrest`) VALUES ("+r.getIdrest()+", '"+r.getNomrest()+"', '"+r.getAdresserest()+"', '"+r.getMenurest()+"', "+r.getNumrest()+", '"+r.getImgrest()+"');";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(RestoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
     @Override
    public int supprimerResto(Resto r) {
        String sql ="Delete from `resto` where nomrest= ? ";
         try {
           
            PreparedStatement ps = connx.prepareStatement(sql);
            ps.setString(1,r.getNomrest());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RestoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     @Override
    public int modifierResto(Resto r) {
        String sq13="UPDATE `resto` SET `idrest`=?,`nomrest`=?,`adresserest`=?,`menurest`=?,`numrest`=?,`imgrest`=? WHERE `idrest` =?";
            
        try {
            PreparedStatement ps = connx.prepareStatement(sq13);
            ps.setString(1, String.valueOf(r.getIdrest()));
            ps.setString(2, r.getNomrest());
            ps.setString(3, r.getAdresserest());
            ps.setString(4, r.getMenurest());
            ps.setString(5, String.valueOf(r.getNumrest()));
            ps.setString(6, r.getImgrest());
            ps.setString(7, String.valueOf(r.getIdrest()));
            
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(RestoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }
    @Override
    public ArrayList<Resto> afficherResto() {
        ArrayList<Resto> Resto = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `resto`";
            ResultSet res = ste.executeQuery(sql1);
            
            Resto r;
        while (res.next()) {
            
            r = new Resto(  res.getInt("idrest"),res.getString("nomrest"),res.getString("adresserest")
                    ,res.getString("menurest"),res.getInt("numrest"),res.getString("imgrest"));
    Resto.add(r);
   
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(RestoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connecté");
        Resto.forEach((r) -> {
            System.out.println (r);
        });
return Resto;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Resto> getDataTeam() {
      
        ObservableList<Resto> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from resto");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Resto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)) );            
            }
        } catch (Exception e) {
        }
        return list;
    }
    @Override
    public ArrayList<Resto> rechercherResto(String R,String C) {
     ArrayList<Resto> Resto = new ArrayList<>();
     try {
         String sql1="select * from resto where " + C + " =\""+R+"\"" ;
            
        
            System.out.println(sql1);
            
            ResultSet res = ste.executeQuery(sql1);
            Resto r;
            
        while (res.next()) {
           r = new Resto(  res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getString(6));
 
           Resto.add(r);
            
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(RestoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
     Resto.forEach((r) -> {
         System.out.println (r);
        });
     
     return Resto;
    }
     public List<Resto> AfficherUser(String nom) {
        List<Resto> listu = new ArrayList<>();
 
        try {
            String requete = "Select * from `resto` where `resto`.`nomrest`='"+nom+"'";
            PreparedStatement pst = connx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) 
            {
                Resto r = new Resto(); 
                  r.setIdrest(rs.getInt("idrest"));
            r.setNomrest(rs.getString("nomrest"));
            r.setAdresserest(rs.getString("adresserest"));
            r.setMenurest(rs.getString("menurest"));
            r.setNumrest(rs.getInt("numrest"));
            r.setImgrest(rs.getString("imgrest"));
                      
                        
                        listu.add(r);
            }
        } catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
        }

        return listu;
    }
    
}
