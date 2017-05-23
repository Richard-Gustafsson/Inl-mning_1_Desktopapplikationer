/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;




import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


/**
 *
 * @author rille
 */
public class FXMLDocumentController implements Initializable {
    
    ProgramLogic logic = ProgramLogic.getInstance(); // Skapar instansen för att nå de metoder som finns i logic-klassen.
    BackendCommunicationLogic backend = BackendCommunicationLogic.getInstance(); // Skapar instansen för att nå de metoder som finns i backend.
    
    @FXML
    private TextField developerTextField;
    @FXML
    private TextField gameNameTextField;
    @FXML
    private TextField gameYearOfReleaseTextField;
    @FXML
    private TextField gameGenreTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView developerListView;
    @FXML
    private TableView<Game> gameTableView;
    @FXML
    private TableColumn<Game,String> gameName;
    @FXML
    private TableColumn<Game,String> yearOfRelease;
    @FXML
    private TableColumn<Game,String> genre;
    @FXML
    private Label noMatchLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView topText;
    @FXML
    private ImageView bottomText;
    @FXML
    private ImageView imageView3;
    
    // Den här metoden lägger till ett developer-objekt i listan med developers.
    @FXML
    private void handleDeveloperButtonAction(ActionEvent event){ 
        getChat(false);
        noMatchLabel.setText("");
        boolean exist = true;
        
        if(!developerTextField.getText().isEmpty()){
            for(Developer d : logic.getDeveloperList()){
                if(d.getDeveloperName().equals(developerTextField.getText())){
                    getChat(true);
                    noMatchLabel.setText("Developer already exists. Try another name.");
                    exist = true;
                    break;
                }
                else{
                    exist = false;
                }
            }
            if(exist == false){
                backend.addDeveloper(developerTextField.getText());
                developerListView.getItems().clear();
                logic.setDeveloperList(backend.getAllDevelopers()); 
                developerListView.setItems(logic.getDeveloperList());
            }
        }
        else{
            getChat(true);
            noMatchLabel.setText("Developer name can't be empty."); 
        }
        developerTextField.clear();
    }
    
    // Med den här metoden så tar vi bort den developern som användaren markerat i listview.
    @FXML
    private void handleDeleteDeveloperButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String x = "";
        try{        // Felhantering för att se till att man markerat en developer som man vill ta bort. Programmet ska inte krascha.                                                                    
            x = developerListView.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
             getChat(true);
            noMatchLabel.setText("You have to select a develoer to delete.");
        }
        backend.deleteDeveloper(x);
        developerListView.getItems().clear();
        logic.setDeveloperList(backend.getAllDevelopers()); 
        developerListView.setItems(logic.getDeveloperList());
    }
    
    @FXML
    private void handleSearchDevButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        
        String s = searchTextField.getText(); // Sparar det strängvärde som användaren skriver in, i variabeln s.
        
        if(searchTextField.getText().isEmpty()){
            developerListView.refresh();
            developerListView.setItems(logic.getDeveloperList());
            getChat(true);
            noMatchLabel.setText("Showing all the developers!");
        }
        else{
            ObservableList list = backend.getDeveloper(s);
            System.out.println(list.get(0));
            
            if(list.get(0)==null){
                getChat(true);
                noMatchLabel.setText("Can't find developer.");
            }
            else if(list.get(0)!= null){
                developerListView.setItems(list);
            }
        }

        searchTextField.clear();
    }
    
    @FXML
    private void handleEditDeveloperButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String x = "";
        String y = developerTextField.getText();
        try{                                                                        
            x = developerListView.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
            getChat(true);
            noMatchLabel.setText("Select a developer to edit.");
        }
        
        if(!y.isEmpty()){
            backend.updateDeveloper(y, x);
            developerListView.getItems().clear();
            logic.setDeveloperList(backend.getAllDevelopers()); 
            developerListView.setItems(logic.getDeveloperList());
        }
        else{
            getChat(true);
            noMatchLabel.setText("Input a new name please.");
        }
    
    }
    
    @FXML
    private void handleGameButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String x = "";
        try{    
             x = developerListView.getSelectionModel().getSelectedItem().toString();   
        }catch(NullPointerException e){
            
        }
        if(x != ""){
        
            String n = gameNameTextField.getText();
            String y = gameYearOfReleaseTextField.getText(); 
            String g = gameGenreTextField.getText();
            
            if(gameNameTextField.getText().isEmpty() || gameYearOfReleaseTextField.getText().isEmpty() || gameGenreTextField.getText().isEmpty()){
                getChat(true);
                noMatchLabel.setText("Can't add game!");
            }
            else{
                backend.addGame(x, n, y, g);
                logic.setObGameList(backend.getAllGames(x));
                gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));    
                yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
                genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
                gameTableView.setItems(logic.getObGameList());
            }
        }
        else{
            getChat(true);
            noMatchLabel.setText("Select a developer when adding a game!");
        }
        
        gameNameTextField.clear();
        gameYearOfReleaseTextField.clear();
        gameGenreTextField.clear();
    }
    
    @FXML
    private void handleMouseClick(){
         
        gameTableView.getItems().clear();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        
        logic.setObGameList(backend.getAllGames(x));
        gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));    
        yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
        genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
        gameTableView.setItems(logic.getObGameList());
            
    }
    
    @FXML
    private void handleSearchGameAction(ActionEvent event){
        String s = searchTextField.getText();
        
        
        gameTableView.getItems().clear();
        logic.setObGameList(backend.getGame(s));
        gameTableView.setItems(logic.getObGameList());
        searchTextField.clear();
    }
    
    @FXML
    private void handleDeleteGameButtonAction(ActionEvent event){
        String gameItem = ""; 
        String x = "";
        
        try{
            x = developerListView.getSelectionModel().getSelectedItem().toString();
            gameItem = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        }catch(Exception e){
            
        }
        
        backend.deleteGame(x, gameItem);
        gameTableView.getItems().clear();
        gameTableView.setItems(logic.getObGameList());
    }
    
   @FXML
    public void handleEditAction1(CellEditEvent<Game, String> t) {
        String y = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        String oldVal = t.getOldValue();
        
         
        ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGameName(t.getNewValue());
        
       
        
        backend.updateGame(x, y, oldVal, t.getNewValue());
    }
    
    @FXML
    public void handleEditAction2(CellEditEvent<Game, String> t) {
        String y = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        String oldVal = t.getOldValue();
        
        
        
        ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setYearOfRelease(t.getNewValue());
        
      
        
        backend.updateGame(x, y, oldVal, t.getNewValue());
    }
    
    @FXML
    public void handleEditAction3(CellEditEvent<Game, String> t) {
        String y = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        String oldVal = t.getOldValue();
        
         
         
        ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGenre(t.getNewValue());
        
        
        
        backend.updateGame(x, y, oldVal, t.getNewValue());
    }
    
    // Med den här metoden så kan vi slänga ut en pratbubbla på GUI vid de tillfällen där vi vill berätta något för användaren, tex vid felhantering.
    private void getChat(boolean set){
        if(set == true){
        File file = new File("src/desktopinlämning1/pictures/picture.png");
        Image img2 = new Image(file.toURI().toString());
        imageView2.setImage(img2);
        }
        else{
            imageView2.setImage(null);
        } 
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
       Image img = new Image("http://www.officialpsds.com/images/thumbs/Hello-Kitty-psd26128.png");
       imageView.setImage(img);
       
       File file2 = new File("src/desktopinlämning1/pictures/topText.png");
       Image img3 = new Image(file2.toURI().toString());
       topText.setImage(img3);
       
       File file3 = new File("src/desktopinlämning1/pictures/bottomText.png");
       Image img4 = new Image(file3.toURI().toString());
       bottomText.setImage(img4);
       
       File file4 = new File("src/desktopinlämning1/pictures/hello-kitty.png");
       Image img5 = new Image(file4.toURI().toString());
       imageView3.setImage(img5);
       
       logic.setDeveloperList(backend.getAllDevelopers()); 
       developerListView.setItems(logic.getDeveloperList()); // Det första som händer i programmet är att man laddar in en lista med några developers. 
       
       gameName.setCellFactory(TextFieldTableCell.forTableColumn());
       yearOfRelease.setCellFactory(TextFieldTableCell.forTableColumn());
       genre.setCellFactory(TextFieldTableCell.forTableColumn());
       
    }    
    
}
