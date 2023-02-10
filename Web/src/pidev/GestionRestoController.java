/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import Entites.Div;
import Entites.Resto;
import Services.DivServices;
import Services.RestoServices;
import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Utils.MyConnexion;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class GestionRestoController implements Initializable {
                 private final Connection myConnex = MyConnexion.getInstanceConnex().getConnection();


    @FXML
    private TextField idr;
    @FXML
    private TextField nomr;
    @FXML
    private TextField adresser;
    @FXML
    private TextField menu;
    @FXML
    private TextField numr;
    @FXML
    private TableView<Resto> tvresto;
    private ImageView imgresto;
    @FXML
    private TextField img;
    @FXML
    private TableColumn<Resto, Integer> colidr;
    @FXML
    private TableColumn<Resto, String> colnomr;
    @FXML
    private TableColumn<Resto, String> coladresser;
    @FXML
    private TableColumn<Resto, String> colmenu;
    @FXML
    private TableColumn<Resto, Integer> colnumr;
    @FXML
    private TableColumn<Resto, String> colimg;
    private String path;
    File selectedFile;
    @FXML
    private TextField rechr;
    @FXML
    private ImageView importimage;
    @FXML
    private Button btbrowse;
    String imag="";
    List<String> type;
 

    /*
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //affichelisteResto();
        type =new ArrayList();
        type.add("*.jpg");
         type.add("*.png");
    } 
     public ObservableList<Resto> setVisible()
    { 
       

           try {
               ObservableList<Resto> obl =FXCollections.observableArrayList();
                  
                 PreparedStatement pt= myConnex.prepareStatement("select idrest, nomrest,adresserest,menurest,numrest,imgrest  from `resto` ");
                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                 Resto r = new Resto();
                 r.setIdrest(rs.getInt("idrest"));

                 r.setNomrest(rs.getString("nomrest"));
                 r.setAdresserest(rs.getString("adresserest"));
                 r.setMenurest(rs.getString("menurest"));
                 r.setNumrest(rs.getInt("numrest"));
                 r.setImgrest(rs.getString("imgrest"));
               
             

                  
                  System.out.println("");
         obl.add(r);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
     public void affichelisteResto() {
        
           
                         
      
      colidr.setCellValueFactory(new PropertyValueFactory<>("idrest"));
      colnomr.setCellValueFactory(new PropertyValueFactory<>("nomrest"));
      coladresser.setCellValueFactory(new PropertyValueFactory<>("adresserest"));
      colmenu.setCellValueFactory(new PropertyValueFactory<>("menurest"));
      colnumr.setCellValueFactory(new PropertyValueFactory<>("numrest"));
      colimg.setCellValueFactory(new PropertyValueFactory<>("imgrest"));
      ObservableList<Resto> obl =FXCollections.observableArrayList();
     
     obl=setVisible(); 
      tvresto.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void Afficherresto(MouseEvent event) {
         int index=-1; 
        index=tvresto.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
        
        idr.setText(colidr.getCellData(index).toString());
        nomr.setText(colnomr.getCellData(index));
        adresser.setText(coladresser.getCellData(index));
        menu.setText(colmenu.getCellData(index));
        numr.setText(colnumr.getCellData(index).toString());
        img.setText(colimg.getCellData(index));
        //imgresto.setImage(new Image(colimg.getCellData(index)));
        imgresto.setFitHeight(100);
        imgresto.setFitWidth(200);
    }

    @FXML
    private void Ajouterresto(ActionEvent event) {
          RestoServices sr = new RestoServices();
        Resto r = new Resto();
        r.setIdrest(Integer.parseInt(idr.getText()));
        r.setNomrest(nomr.getText());
        r.setAdresserest(adresser.getText());
        r.setMenurest(menu.getText());
        r.setNumrest(Integer.parseInt(numr.getText()));
        //r.setImgrest(path);
        r.setImgrest(img.getText());

 
        sr.ajouterResto(r);
        affichelisteResto();     
    }

    @FXML
    private void Modifierresto(ActionEvent event) {
       
        RestoServices sr = new RestoServices();
        Resto r = new Resto();
        r.setIdrest(Integer.parseInt(idr.getText()));

        r.setNomrest(nomr.getText());
        r.setAdresserest(adresser.getText());
        r.setMenurest(menu.getText());
        r.setNumrest(Integer.parseInt(numr.getText()));
        r.setImgrest(img.getText());
        //r.setImgrest(path);
        r.setIdrest(Integer.parseInt(idr.getText()));
        sr.modifierResto(r);
        affichelisteResto(); 
    }

    @FXML
    private void Supprimerresto(ActionEvent event) {
         
        RestoServices sr = new RestoServices();
        Resto r = new Resto();
        r.setNomrest(nomr.getText());
        sr.supprimerResto(r);
        affichelisteResto();      
    }

    @FXML
    private void Retour(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/pidev/GestionDiv.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void handle(DragEvent event) {
    }

    private void UpImage(ActionEvent event) {
           Image myImage = new Image(getClass().getResourceAsStream(path));
        imgresto.setImage(myImage);
    }

    @FXML
    private void Rechercher(ActionEvent event) {
        try {
                   ObservableList<Resto> ofList = FXCollections.observableArrayList();
       colidr.setCellValueFactory(new PropertyValueFactory<>("idrest"));
      colnomr.setCellValueFactory(new PropertyValueFactory<>("nomrest"));
      coladresser.setCellValueFactory(new PropertyValueFactory<>("adresserest"));
      colmenu.setCellValueFactory(new PropertyValueFactory<>("menurest"));
      colnumr.setCellValueFactory(new PropertyValueFactory<>("numrest"));
      colimg.setCellValueFactory(new PropertyValueFactory<>("imgrest"));
        
        /////////////////////////////////////////////////////////////////////////////
        RestoServices rs = new RestoServices();
        List old = rs.AfficherUser(rechr.getText());
        ofList.addAll(old);
        tvresto.setItems(ofList);
        tvresto.refresh();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }   
    }

    @FXML
    private void SMS(MouseEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/pidev/SMS.fxml"));
              Scene scene = new Scene(root);
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(scene);
              stage.show();
    }

    @FXML
    private void importimage(ActionEvent event) {
          FileChooser f=new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png files", type));
        File fc=f.showOpenDialog(null);
        
        if(fc != null)
        {   
            System.out.println(fc.getName());
            imag=fc.getName();
           FileSystem fileSys = FileSystems.getDefault();
           Path srcPath= fc.toPath();
           Path destPath= fileSys.getPath("C:\\xampp\\img"+fc.getName());
            try {
                Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(GestionRestoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(srcPath.toString());
            Image i = new Image(fc.getAbsoluteFile().toURI().toString());
            importimage.setImage(i);
            
        }
    }
    
    
}
