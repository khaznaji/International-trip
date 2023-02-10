/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import Services.HotelServices;
import Services.ReclamationServices;
import entite.Hotel;
import entite.Reclamation;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AffichageReclamationController implements Initializable {
             private Connection myConnex = MyConnexion.getInstanceConnex().getConnection();

    @FXML
    private TableView<Reclamation> TableViewReclamation;
    @FXML
    private Button btnSupprimer;
    @FXML
    private TableColumn<Reclamation, String> col_num;
    @FXML
    private TableColumn<Reclamation, String> col_type;
    @FXML
    private TableColumn<Reclamation, String> col_description;
    @FXML
    private TableColumn<Reclamation, String> col_objet;
    @FXML
    private TableColumn<Reclamation, String> col_nom;
    @FXML
    private TableColumn<Reclamation, String> col_prenom;
    @FXML
    private TableColumn<Reclamation, String> col_email;
    @FXML
    private TableColumn<Reclamation, String> col_screenshot;
    @FXML
    private TableColumn<Reclamation, Integer> col_id_trip;
    @FXML
    private TableColumn<Reclamation, Integer> col_id_hotel;
    @FXML
    private TextField tfid;
        static Reclamation selectionedReclamation;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                  affichelisteReclamation(); 
                   
           
              
      }
    public ObservableList<Reclamation> show1()
    {
           try {
               ObservableList<Reclamation> obl =FXCollections.observableArrayList();
                  //exécution de la réquette et enregistrer le resultat dans le resultset
                 PreparedStatement pt= myConnex.prepareStatement("select numero_mobile, type,description,objet,nom,prenom,email,screenshot,id_trip,id_hotel  from reclamation ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Reclamation ls = new Reclamation();
                 ls.setId_hotel(rs.getInt("id_hotel"));
                 ls.setId_trip(rs.getInt("id_trip"));

                 ls.setNumero_mobile(rs.getString("numero_mobile"));
                                  ls.setType(rs.getString("type"));
                                                   ls.setDescription(rs.getString("description"));

                                                   ls.setObjet(rs.getString("objet"));

                 ls.setNom(rs.getString("nom"));
                                  ls.setPrenom(rs.getString("prenom"));

                 ls.setEmail(rs.getString("email"));
                                ls.setScreenshot(rs.getString("screenshot"));

             

                  
                  System.out.println("");
         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
          
    } 
      public void affichelisteReclamation() {
        
           
                         
      //ajouter les valeurs au tableview
      col_num.setCellValueFactory(new PropertyValueFactory<>("numero_mobile"));
      col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
      col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
      col_objet.setCellValueFactory(new PropertyValueFactory<>("objet"));
      col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                  col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
                  col_screenshot.setCellValueFactory(new PropertyValueFactory<>("screenshot"));
                   
                  col_id_trip.setCellValueFactory(new PropertyValueFactory<>("id_trip"));
                  col_id_hotel.setCellValueFactory(new PropertyValueFactory<>("id_hotel"));

      ObservableList<Reclamation> obl =FXCollections.observableArrayList();
     // tableview.setItems(null);
     obl=show1(); 
      TableViewReclamation.setItems(obl);
      System.out.println(""+obl);

                      
    }
    @FXML
    private void SupprimerReclamation(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la suppression de cet Reclamation ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation
        if (option.get() == ButtonType.OK) {
        String tit = tfid.getText().toString();
        ReclamationServices sp = new ReclamationServices();
        Reclamation e = new Reclamation();
        e.setObjet(tit);
        sp.supprimerReclamation(e);
        
                     
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de le suppression!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("La Reclamation a été bien supprimée");
                    alert2.show();
                    affichelisteReclamation();       }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("La Reclamation n'a pas été supprimée");
            alert2.show();
    }
    
    
}}
