/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevapp4;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entite.Types;
import entite.Div;
import Services.TypesServices;
import Services.DivServices;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import utils.MyConnexion;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class GestionDivController implements Initializable {

    private TextField tftitre;
    private TextField tfperiode;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tftype;
    private TextField tfimg;
    @FXML
    private ComboBox<Types> affecter;
    private TextField tfchoix;
    @FXML
    private TableView<Div> tableheb;
    @FXML
    private TableColumn<Div, String> tabtitre;
    @FXML
    private TableColumn<Div, String> tabtype;
    @FXML
    private TableColumn<Div, Integer> tabprix;
    @FXML
    private TableColumn<Div, String> tabimg;
    @FXML
    private TableColumn<Div, String> tabadresse;
    @FXML
    private TableColumn<Div, String> tabperiode;
    @FXML
    private TableColumn<Div, String> tabchoix;
    @FXML
    private TableColumn<Div, String> tabdate;
    @FXML
    private TableColumn<Div, String> tabpaysa;
    @FXML
    private ImageView imghotel;
    @FXML
    private Button upload;
    @FXML
    private DatePicker tfdate;
    private Div c; 
    private String path;
    File selectedFile;
            
            ObservableList<Div>  list =  FXCollections.observableArrayList();
      int  index= -1; 
             Types p ;
                    int id=0;
  
           
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
                    final ObservableList<Types> options= FXCollections.observableArrayList();
    @FXML
    private TableView<?> tvfav;
    @FXML
    private TableColumn<?, ?> nomfav;
    @FXML
    private TableColumn<?, ?> numfav;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try {
            fillcombo();
        } 
   catch (SQLException ex) {
        } 
         ObservableList<Div>  list =  FXCollections.observableArrayList();
          try { 
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("SELECT `types_id`, `nom`, `numtel`, `adresse` FROM `div` ");
            while(rs.next())
            {
                list.add(new Div(p,rs.getString(2), rs.getInt(3),rs.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionDivController.class.getName()).log(Level.SEVERE, null, ex);
        }

      
  
        // TODO
 tableheb.setItems(list);
  tableheb.refresh();
   }  
    ObservableList combo = FXCollections.observableArrayList();
     public void fillcombo() throws SQLException {
       try {
            List<Types> list=new ArrayList<>();
             PreparedStatement pst;
        String query = "select * from types";
        pst = cnx.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
            while(rs.next())
            {

                p =new Types(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(p);
               
            }
            options.addAll(list);
             affecter.getItems().setAll(list);

            System.out.println("aaa");
            System.out.println(options);
        } catch (SQLException ex) {
            Logger.getLogger(GestionHebergementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query1="select id from types where types=?";
                        PreparedStatement pst;

            pst=cnx.prepareStatement(query1);
            pst.setString(1,p.getTypes());
                   ResultSet rs = pst.executeQuery();

            rs=pst.executeQuery();
             while(rs.next())
            {

                id=rs.getInt(1);
            }}
       

    @FXML
    private void selected(MouseEvent event) {
 index=tableheb.getSelectionModel().getSelectedIndex();
        if (index<= -1)
        {return; } 
              affecter.setValue(p);

       
                tftype.setText(tabtype.getCellData(index).toString());
                tfprix.setText(tabprix.getCellData(index).toString());
                //tfimg.setText(tabimg.getCellData(index).toString());
                 tfadresse.setText(tabadresse.getCellData(index).toString());
    }

    @FXML
    private void ajouterheb(ActionEvent event) {
 DivServices ts = new DivServices();
                                       Types p=affecter.getItems().get(affecter.getSelectionModel().getSelectedIndex());
  
                  TypesServices ts1 = new TypesServices();
                  
        DivServices sp = new DivServices();
        Div e = new Div();
        e.setP(affecter.getValue());
       
        e.setNom(tftype.getText()); 
        e.setNumtel(Integer.parseInt(tfprix.getText())); 
        e.setAdresse(tfadresse.getText());  
       
             
              //  e.setImage(path);
  //e.setImg(tfimage.getText());
//if (controlSaisieDescription()){
//    if(controlSaisieNom()){
     
 
        sp.AjouterDiv(e);        

       tableheb.refresh();
               clearFields();
       affiche1();

       
    }
    private void clearFields() {
        tfprix.clear();
        tftype.clear();
        tfadresse.clear();
       
//imghotel.clear();
         /*   imghotel.setImage(null);
 upload.setText("Upload");        
                imghotel.setImage(new Image("file:C:\\Users\\DELL\\Desktop\\InterTrip\\animation2.gif"));*/
        
        
    }

    @FXML
    private void modifierheb(ActionEvent event) {
          Types types =affecter.getValue();
   java.util.Date date = 
    java.util.Date.from(tfdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String nom=tftype.getText();

        String numtel=tfprix.getText();

        // String image=tfimg.getText();
        String adresse=tfadresse.getText();

       


     
        
        DivServices sp = new DivServices();
        Div c = new Div();
                        c.setP(types);

      
        c.setNom(nom);

        c.setNumtel(Integer.parseInt(numtel));
        c.setAdresse(adresse);
        
                      //  c.setImage(path);


                c.setNom(nom);

        
        sp.modifierH(c);
                        affiche1();
                               tableheb.refresh();
    }

    @FXML
    private void supprimerheb(ActionEvent event) {
         DivServices location = new DivServices();
          Div ls = new   Div();
        ls = tableheb.getSelectionModel().getSelectedItem();
                

        location.SupprimerDiv(ls.getNom());
                        affiche1();
    }
     public ObservableList<Div> show1()
    {  
       
 
           try {
                 
               ObservableList<Div> obl =FXCollections.observableArrayList();
  Connection cnx = MyConnexion.getInstanceConnex().getConnection();
 //exécution de la réquette et enregistrer le resultat dans le resultset
                PreparedStatement pt= cnx.prepareStatement("SELECT nom,nultel,adresse,types_id from div  ");

                  ResultSet rs = pt.executeQuery();
                  while(rs.next()){
                  //obl.add(new Note(rs.getFloat(1),rs.getFloat(2),rs.getFloat(3),rs.getInt(4),rs.getString(5)));
                 Div ls = new Div();
                 
                  ls.setNom(rs.getString("nom"));
                  ls.setNumtel(Integer.parseInt(rs.getString("numtel")));
                  //ls.setImage(rs.getString("image"));
                  ls.setAdresse(rs.getString("adresse"));
                  

         obl.add(ls);
                  }
                  return obl;
                  
              } catch (SQLException ex) {
                System.out.println("Erreur"+ex);
              }
           return null;
    } 
    public void affiche1() {
         
           
               
      tabtype.setCellValueFactory(new PropertyValueFactory<>("nom"));
      tabprix.setCellValueFactory(new PropertyValueFactory<>("numtel"));
     // tabimg.setCellValueFactory((new PropertyValueFactory<>("image")));
      tabadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
     
      ObservableList<Div> obl =FXCollections.observableArrayList();
      obl=show1(); 
      tableheb.setItems(obl);
      System.out.println(""+obl);

                      
    }

    @FXML
    private void UploadImage(ActionEvent event) {
    }

    @FXML
    private void Windowresto(MouseEvent event) {
    }

    @FXML
    private void Afficherfav(MouseEvent event) {
    }

    @FXML
    private void ajouterfav(MouseEvent event) {
    }

    @FXML
    private void supprimerfav(MouseEvent event) {
    }

    @FXML
    private void jouer(ActionEvent event) {
    }

    @FXML
    private void ladate(ActionEvent event) {
    }
    
}
