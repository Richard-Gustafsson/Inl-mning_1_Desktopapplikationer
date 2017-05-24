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
/*
    -------------------LITE KORT DOKUMENTATION--------------------
    Då det kan anses vara lite dåligt kommenterat för visa metoder 
    och handleevents i Controller-klassen så har jag skrivit detta.
    Det som jag lagt mycket fokus på i just denna delen utav programmet
    är att få till det med felhantering. Programmet skall inte krascha
    om användaren skulle missklicka eller mata in något felaktigtvärde. 
    Så därför finns det rätt mycket if-else-satser i de olika 
    metoderna. Det blev även att jag skrev om programmet efterhand, 
    då jag beslöt mig för att ha en BackendCommunicationLogic för all
    serverkommunikation och en ProgramLogic för att kunna spara listor för 
    spel och utvecklare i lokala listor. Så därför kan jag själv tycka att
    det ser lite stökigt ut på vissa ställen.
*/
public class FXMLDocumentController implements Initializable {
    
    ProgramLogic logic = ProgramLogic.getInstance(); // Skapar instansen för att nå de metoder som finns i logic-klassen.
    BackendCommunicationLogic backend = new BackendCommunicationLogic(); // Skapar ett nytt objekt av Backend-klassen som kommunicerar med server.
    
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
        boolean exist = true; // En extra försäkringar om att Developern inte redan existerar.
        
        if(!developerTextField.getText().isEmpty()){ // Kollar så att man inte skickar in en tom sträng som namn.
            if(logic.getDeveloperList().isEmpty()){
                backend.addDeveloper(developerTextField.getText());
                logic.setDeveloperList(backend.getAllDevelopers()); 
                developerListView.setItems(logic.getDeveloperList());
                logic.setGameList();
            }
            else{
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
                if(exist == false){ // Finns den inte sen tidigare så lägger man till i databasen
                    backend.addDeveloper(developerTextField.getText());
                    developerListView.getItems().clear();
                    logic.setDeveloperList(backend.getAllDevelopers());
                    developerListView.setItems(logic.getDeveloperList());
                    logic.setGameList();
                }
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
            noMatchLabel.setText("You have to select a developer to delete.");
        }
        backend.deleteDeveloper(x); // Kallar på metoden i backend och skickar in den Developer vi vill ta bort.
        developerListView.getItems().remove(developerListView.getSelectionModel().getSelectedItem());
        developerListView.setItems(logic.getDeveloperList());
    }
    
    // Med den här metoden så kan vi söka efter en developer som finns i en lista.
    @FXML
    private void handleSearchDevButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        
        String s = searchTextField.getText(); // Sparar det strängvärde som användaren skriver in, i variabeln s.
        
        if(searchTextField.getText().isEmpty()){ // Om det är tomt i sökfältet när man klickar "sök" så visas alla developers som finns.
            developerListView.refresh();
            developerListView.setItems(logic.getDeveloperList());
            getChat(true);
            noMatchLabel.setText("Showing all the developers!");
        }
        else{
            ObservableList list = backend.getDeveloper(s);
            
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
    
    // Med den här metoden kan man uppdatera ett namn för en Developer i databasen.
    @FXML
    private void handleEditDeveloperButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        boolean exist = false;
        String x = "";
        String y = developerTextField.getText();
        try{                                                                        
            x = developerListView.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
            getChat(true);
            noMatchLabel.setText("Select a developer to edit.");
        }
        
        if(!y.isEmpty()){   // Kollar så att det inte är en tom sträng
            for(Developer d : logic.getDeveloperList()){
                if(d.getDeveloperName().equals(y)){ // Finns developern redan så skall det inte gå sätta namnet
                    exist = true;
                    break;
                }
            }
            if(exist == false){
            backend.updateDeveloper(y, x);                      // Uppdaterar listan med developer och spel
            developerListView.getItems().clear();
            logic.setDeveloperList(backend.getAllDevelopers());
            developerListView.setItems(logic.getDeveloperList());
            logic.setGameList();
            }
            else{
                getChat(true);
                noMatchLabel.setText("That game already exist.");
            }
        }
        else{
            getChat(true);
            noMatchLabel.setText("Input a new name please.");
        }
        developerTextField.clear();
    }
    
    // Med den här metoden kan man lägga till ett nytt Game i databasen.
    @FXML
    private void handleGameButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        boolean exist = false;
        String x = "";
        
        try{    
             x = developerListView.getSelectionModel().getSelectedItem().toString();   
        }catch(NullPointerException e){
            
        }
        if(x != ""){
        
            String n = gameNameTextField.getText();
            String y = gameYearOfReleaseTextField.getText(); 
            String g = gameGenreTextField.getText();
            
            // Man ska inte kunna lägga till något spel om något utav inputfälten är tomma.
            if(gameNameTextField.getText().isEmpty() || gameYearOfReleaseTextField.getText().isEmpty() || gameGenreTextField.getText().isEmpty()){
                getChat(true);
                noMatchLabel.setText("Can't add game!");
            }
            else{
                for(Developer d : logic.getDeveloperList()){
                    for(Game i : d.getGameList()){
                        if(i.getGameName().equals(n)){
                            exist = true;
                            break;
                        }
                    }
                }
                if(exist == false){
                    
                    backend.addGame(x, n, y, g); // skickar iväg de värden som behövs till backend för att kunna skapa det nya Game-objektet.
                    logic.setGameList();

                    gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));    
                    yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
                    genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
                    gameTableView.setItems(logic.getGameList(x));
                }
                else{
                    getChat(true);
                    noMatchLabel.setText("That game already exist!");
                }
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
    
    // När man klickar på en viss Developer skall dess Games visas om den har några.
    @FXML
    private void handleMouseClick(){
         
        gameTableView.getItems().clear();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
       
        gameTableView.setItems(logic.getGameList(x)); // Hämtar från den lokala listan med spel.
       
        gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));    
        yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
        genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
     
    }
    
    // Med den här metoden kan man söka efter ett Game i databasen.
    @FXML
    private void handleSearchGameAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String s = searchTextField.getText();

        if(searchTextField.getText().isEmpty()){
            getChat(true);
            noMatchLabel.setText("Type in a game to search!");
        }
        else{
            ObservableList list = backend.getGame(s); // Får tillbaka det man sökt efter i form utav en ny lista för att sätta tableview.
            
            if(list.isEmpty()){
                getChat(true);
                noMatchLabel.setText("Can't find Game.");
            }
            else if(!list.isEmpty()){
                gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));    
                yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
                genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
                gameTableView.setItems(list);
            }
        }

        searchTextField.clear();
 
    }
    
    // Med den här metoden kan man radera det spel som man markerat i TableView.
    @FXML
    private void handleDeleteGameButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String gameItem = ""; 
        String x = "";
        
        try{
            x = developerListView.getSelectionModel().getSelectedItem().toString();
            gameItem = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        }catch(Exception e){
            
        }
        if(x == ""){ // Inget ska hända om man bara trycker på knappen utav att markerat varken en developer eller spel.
            getChat(true);
            noMatchLabel.setText("First select a developer, then game to delete.");
        }
        else if(gameItem == ""){
            getChat(true);
            noMatchLabel.setText("Select a game to delete.");
        }else{
            backend.deleteGame(x, gameItem);
            logic.setGameList();
            gameTableView.setItems(logic.getGameList(x));
        }
    }
   
    // Sätter det nya värdet för ett Game-namn
   @FXML
    public void handleEditAction1(CellEditEvent<Game, String> t) {
        getChat(false);
        noMatchLabel.setText("");
        String y = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        String oldVal = t.getOldValue();
        Boolean exist = false;
        
        for(Developer d : logic.getDeveloperList()){    // Felhantering för att se till att namnet inte redan finns med
            for(Game g : d.getGameList()){
                if(g.getGameName().equals(t.getNewValue())){
                    exist = true;
                    break;
                }
            }
        }
        
       
        if(exist == false){ // Sätter det nya värdet ifall det inte finns sedan tidigare.
            ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGameName(t.getNewValue());
            backend.updateGame(x, y, oldVal, t.getNewValue());
        }
        else{
            getChat(true);
            noMatchLabel.setText("That game already exist!");
        }
    }
    
    // Sätter det nya värdet för Game-Year of Release
    @FXML
    public void handleEditAction2(CellEditEvent<Game, String> t) {
        String y = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        String oldVal = t.getOldValue();
        
        
        
        ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setYearOfRelease(t.getNewValue());
        
      
        
        backend.updateGame(x, y, oldVal, t.getNewValue());
    }
    
    // Sätter det nya värdet för en Game-genre
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
       logic.setGameList(); // Sätter spel till alla Developers lokalt.
       developerListView.setItems(logic.getDeveloperList()); // Sätter lokal Developer lista.
       
       gameName.setCellFactory(TextFieldTableCell.forTableColumn());
       yearOfRelease.setCellFactory(TextFieldTableCell.forTableColumn());
       genre.setCellFactory(TextFieldTableCell.forTableColumn());
       
    }    
    
}
