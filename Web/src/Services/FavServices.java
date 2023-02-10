/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Fav;
import Utils.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


/**
 *
 * @author HP
 */
public class FavServices implements IFavServices {
     Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public FavServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
     @Override   
    public int ajouterFav(Fav f) {
        int x = 0;
        try {
           String sql ="INSERT INTO `fav`( `nomfav`, `numfav`) VALUES ('"+f.getNomfav()+"', "+f.getNumfav()+");";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
    @Override
    public int supprimerFav(Fav f) {
        String sql ="Delete from `fav` where `nomfav`= ? ";
         try {
           
            PreparedStatement ps = connx.prepareStatement(sql);
            ps.setString(1,f.getNomfav());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     @Override
    public ArrayList<Fav> afficherFav() {
        ArrayList<Fav> Fav = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `fav`";
            ResultSet res = ste.executeQuery(sql1);
            
            Fav f;
        while (res.next()) {
            
            f = new Fav( res.getString("`nomfav`"),res.getInt("numfav"));
    Fav.add(f);
    
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("affiché");
        Fav.forEach((f) -> {
            System.out.println (f);
        });
return Fav;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Fav> getDataTeam() {
      
        ObservableList<Fav> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from `fav`");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Fav(rs.getString(1),rs.getInt(2))  );            
            }
        } catch (Exception e) {
        }
        return list;
    }
     public List<Fav> ListClasse() {
        List<Fav> Mylist = new ArrayList<>();
        try {
            String requete = "select * from `fav`";
            PreparedStatement ps = connx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Fav f = new Fav();
              
             
            f.setNomfav(rs.getString("nomfav"));
            f.setNumfav(rs.getInt("numfav"));
            
                Mylist.add(f);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
      public List<Fav> ListClasse1(String s ) {
        List<Fav> list = new ArrayList<>();
        try {
            String requete = "select * from `fav` where `nomfav` =? ";
            PreparedStatement ps = connx.prepareStatement(requete);
            ps.setString(1, s);
      ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Fav f = new Fav();
              
            
            f.setNomfav(rs.getString("nomfav"));
           
            f.setNumfav(rs.getInt("numfav"));
            
                list.add(f);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    
    
}
