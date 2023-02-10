/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entites.Div;
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
public class DivServices implements IDivServices {
    Connection connx ;
     Statement ste;
               private PreparedStatement pst;

    public DivServices() {
        connx = MyConnexion.getInstanceConnex().getConnection();
        try {
            ste = connx.createStatement();
        } catch (SQLException ex) {
                    System.out.println(ex);
        }
   
    }
    @Override   
    public int ajouterDiv(Div d) {
        int x = 0;
        try {
           String sql ="INSERT INTO `div`( `iddiv` ,`nomdiv`, `typediv`, `adressediv`, `numdiv`) VALUES ("+d.getIddiv()+" ,'"+d.getNomdiv()+"', '"+d.getTypediv()+"', '"+d.getAdressediv()+"', "+d.getNumdiv()+");";
          
            x = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  x;
    }
         @Override
    public int supprimerDiv(Div d) {
        String sql ="Delete from `div` where `iddiv`= ? ";
         try {
           
            PreparedStatement ps = connx.prepareStatement(sql);
            ps.setInt(1,d.getIddiv());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     @Override
    public int modifierDiv(Div d) {
        String sq13="UPDATE `div` SET `iddiv`=?,`nomdiv`=?,`typediv`=?,`adressediv`=?,`numdiv`=? WHERE `iddiv` =?";
            
        try {
            PreparedStatement ps = connx.prepareStatement(sq13);
            ps.setString(1, String.valueOf(d.getIddiv()));
            ps.setString(2, d.getNomdiv());
            ps.setString(3, d.getTypediv());
            ps.setString(4, d.getAdressediv());

            ps.setString(5, String.valueOf(d.getNumdiv()));
            ps.setString(6, String.valueOf(d.getIddiv()));
            
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    
    }
     @Override
    public ArrayList<Div> afficherDiv() {
        ArrayList<Div> Div = new ArrayList<>();
        try {
            String sql1="SELECT * FROM `div`";
            ResultSet res = ste.executeQuery(sql1);
            
            Div d;
        while (res.next()) {
            
            d = new Div(  res.getInt("`iddiv`"),res.getString("nomdiv"),res.getString("typediv")
                    ,res.getString("adressediv"),res.getInt("numdiv"));
    Div.add(d);
    
    
    
}
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("affiché");
        Div.forEach((d) -> {
            System.out.println (d);
        });
return Div;
    }
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public ObservableList<Div> getDataTeam() {
      
        ObservableList<Div> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connx.prepareStatement("select * from `div`");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Div(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5))  );            
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public List<Div> AfficherUser(String nom) {
        List<Div> listu = new ArrayList<>();
 
        try {
            String requete = "Select * from `div` where `div`.`nomdiv`='"+nom+"'";
            PreparedStatement pst = connx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) 
            {
                 Div d = new Div(); 
                  d.setIddiv(rs.getInt("iddiv"));
            d.setNomdiv(rs.getString("nomdiv"));
            d.setTypediv(rs.getString("typediv"));
            d.setAdressediv(rs.getString("adressediv"));
            d.setNumdiv(rs.getInt("numdiv"));
                      
                        
                        listu.add(d);
            }
        } catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
        }

        return listu;
    }
   /*  public ArrayList<Div> rechercherDiv(String ) {
        
    ArrayList<Div> Div = new ArrayList<>();
     try {
         String sql1="select * from `div` where `nomdiv` =?" ;
            System.out.println(sql1);
            ResultSet res = ste.executeQuery(sql1);
            Div d;
            
        while (res.next()) {()
           d = new Div(  res.getInt(1),res.getString(2),res.getString(3)
                    ,res.getString(4),res.getInt(5));
        
 
           Div.add(d);
            
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(DivServices.class.getName()).log(Level.SEVERE, null, ex);
        }
     Div.forEach((d) -> {
         System.out.println (d);
        });
     
     return Div;
    }*/
     public List<Div> ListClasse() {
        List<Div> Mylist = new ArrayList<>();
        try {
            String requete = "select * from `div`";
            PreparedStatement ps = connx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Div d = new Div();
              
             d.setIddiv(rs.getInt("iddiv"));
            d.setNomdiv(rs.getString("nomdiv"));
            d.setTypediv(rs.getString("typediv"));
            d.setAdressediv(rs.getString("adressediv"));
            d.setNumdiv(rs.getInt("numdiv"));
            
                Mylist.add(d);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
      public List<Div> ListClasse1(String s ) {
        List<Div> list = new ArrayList<>();
        try {
            String requete = "select * from `div` where `nomdiv` =? ";
            PreparedStatement ps = connx.prepareStatement(requete);
            ps.setString(1, s);
      ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Div d = new Div();
              
            d.setIddiv(rs.getInt("iddiv"));
            d.setNomdiv(rs.getString("nomdiv"));
            d.setTypediv(rs.getString("typediv"));
            d.setAdressediv(rs.getString("adressediv"));
            d.setNumdiv(rs.getInt("numdiv"));
            
                list.add(d);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<Div> rechercherDiv(String R, String C) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
