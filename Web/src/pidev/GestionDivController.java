/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import Entites.Div;
import Entites.Fav;
import Services.DivServices;
import Services.FavServices;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Utils.MyConnexion;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author HP
 */
public class GestionDivController implements Initializable {
                 private final Connection myConnex = MyConnexion.getInstanceConnex().getConnection();

    
    private Label label;
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField type;
    @FXML
    private TextField adresse;
    @FXML
    private TextField num;
    @FXML
    private TableView<Div> tvdiv;
    @FXML
    private TableColumn<Div, Integer> colid;
    @FXML
    private TableColumn<Div, String> colnom;
    @FXML
    private TableColumn<Div, String> coltype;
    @FXML
    private TableColumn<Div, String> coladresse;
    @FXML
    private TableColumn<Div, Integer> colnum;
    @FXML
    private ComboBox<String> cbtype;
    @FXML
    private TextField rech;
    @FXML
    private TableView<Fav> tvfav;
    @FXML
    private TableColumn<Fav, String> nomfav;
    @FXML
    private TableColumn<Fav, Integer> numfav;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
 ObservableList combo = FXCollections.observableArrayList();
     public void fillcombo() throws SQLException {
        PreparedStatement pst;
        String query = "select nomrest from resto";
        pst = myConnex.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            combo.add(rs.getString("nomrest"));
            cbtype.setItems(combo);
        }
    } 
     public ObservableList<Div> setVisible()
    { 
       

           try {
               ObservableList<Div> obl =FXCollections.observableArrayList();
                 PreparedStatement pt= myConnex.prepareStatement("select iddiv, nomdiv,typediv,adressediv,numdiv from `div` ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  
                 Div d = new Div();
                 d.setIddiv(rs.getInt("iddiv"));

                 d.setNomdiv(rs.getString("nomdiv"));
                 d.setTypediv(rs.getString("typediv"));
                 d.setAdressediv(rs.getString("adressediv"));
                 d.setNumdiv(rs.getInt("numdiv"));
               
             

                  
                  System.out.println("");
         obl.add(d);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    }
     //affiche liste divertissemant
      public void afficheliste() {
        
           
                         
      
      colid.setCellValueFactory(new PropertyValueFactory<>("iddiv"));
      colnom.setCellValueFactory(new PropertyValueFactory<>("nomdiv"));
      coltype.setCellValueFactory(new PropertyValueFactory<>("typediv"));
      coladresse.setCellValueFactory(new PropertyValueFactory<>("adressediv"));
      colnum.setCellValueFactory(new PropertyValueFactory<>("numdiv"));
      ObservableList<Div> obl =FXCollections.observableArrayList();
     
     obl=setVisible(); 
      tvdiv.setItems(obl);
      System.out.println(""+obl);

                      
    }
      //visibilité fav
       public ObservableList<Fav> setVisible1()
    { 
       

           try {
               ObservableList<Fav> obl =FXCollections.observableArrayList();
                 PreparedStatement pt= myConnex.prepareStatement("select nomdiv,numdiv from `div` ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  
                 Fav f = new Fav();
                

                 f.setNomfav(rs.getString("nomdiv"));
                
                 f.setNumfav(rs.getInt("numdiv"));
               
             

                  
                  System.out.println("");
         obl.add(f);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    }
     //affiche liste fav
      public void affichelistefav() {
        
           
                         
      
     
      nomfav.setCellValueFactory(new PropertyValueFactory<>("nomfav"));
      numfav.setCellValueFactory(new PropertyValueFactory<>("numfav"));
      ObservableList<Fav> obl =FXCollections.observableArrayList();
     
     obl=setVisible1(); 
      tvfav.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void ajouter(ActionEvent event) {
         DivServices ds = new DivServices();
        Div d = new Div();
        d.setIddiv(Integer.parseInt(id.getText()));
        d.setNomdiv(nom.getText());
        d.setTypediv(cbtype.getValue());

        d.setAdressediv(adresse.getText());
        d.setNumdiv(Integer.parseInt(num.getText()));
 
         

        ds.ajouterDiv(d);
        afficheliste();  
          
    }

    @FXML
    private void modifier(ActionEvent event) {
          
        String iddiv=id.getText();
        String nomdiv=nom.getText();
        String typediv=type.getText();
        String adressediv=adresse.getText();

        String numdiv=num.getText();
        DivServices ds = new DivServices();
        Div d = new Div();
        d.setIddiv(Integer.parseInt(iddiv));
        d.setNomdiv(nomdiv);
        d.setTypediv(typediv);
        d.setAdressediv(adressediv);
        d.setNumdiv(Integer.parseInt(numdiv));
        d.setIddiv(Integer.parseInt(iddiv));
        ds.modifierDiv(d);
        afficheliste();  

    }

    @FXML
    private void supprimer(ActionEvent event) {
        
        DivServices ds = new DivServices();
        Div d = new Div();
        d.setIddiv(Integer.parseInt(id.getText()));
        ds.supprimerDiv(d);
        afficheliste();  
    }

    @FXML
    private void Afficher(MouseEvent event) {
         int index=-1; 
        index=tvdiv.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                id.setText(colid.getCellData(index).toString());

        nom.setText(colnom.getCellData(index));
        cbtype.setValue(coltype.getCellData(index));
        adresse.setText(coladresse.getCellData(index));
        num.setText(colnum.getCellData(index).toString());
        
    }

    @FXML
    private void Windowresto(MouseEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidev/GestionResto.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
        
    }

    @FXML
    private void Rechercher(ActionEvent event) {
        try {
                   ObservableList<Div> ofList = FXCollections.observableArrayList();
       colid.setCellValueFactory(new PropertyValueFactory<>("iddiv"));
      colnom.setCellValueFactory(new PropertyValueFactory<>("nomdiv"));
      coltype.setCellValueFactory(new PropertyValueFactory<>("typediv"));
      coladresse.setCellValueFactory(new PropertyValueFactory<>("adressediv"));
      colnum.setCellValueFactory(new PropertyValueFactory<>("numdiv")); 
        
        /////////////////////////////////////////////////////////////////////////////
        DivServices ds = new DivServices();
        List old = ds.AfficherUser(rech.getText());
        ofList.addAll(old);
        tvdiv.setItems(ofList);
        tvdiv.refresh();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }   
    }

    @FXML
    private void Afficherfav(MouseEvent event) {
          int index=-1; 
        index=tvfav.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
                

        nom.setText(nomfav.getCellData(index));
        num.setText(numfav.getCellData(index).toString());
    }

    @FXML
    private void ajouterfav(MouseEvent event) {
         FavServices fs = new FavServices();
        Fav f = new Fav();
       
        f.setNomfav(nom.getText());
       
        f.setNumfav(Integer.parseInt(num.getText()));
 
         

        fs.ajouterFav(f);
        affichelistefav();  
          
    }

    @FXML
    private void supprimerfav(MouseEvent event) {
          FavServices fs = new FavServices();
        Fav f = new Fav();
        f.setNomfav(nomfav.getText());
        fs.supprimerFav(f);
        affichelistefav();  
    }

    @FXML
    private void jouer(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidev/jeu.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void ladate(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("/pidev/Date.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }
    
}
