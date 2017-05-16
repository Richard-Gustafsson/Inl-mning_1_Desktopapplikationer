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
//        if(!developerTextField.getText().isEmpty()){    // Felhantering för att se till att man inte kan lägga till en developer utan namn (tom sträng).
//            boolean exist = logic.setDeveloperList(developerTextField.getText());   // Tar in det textvärde som användaren anger och anropar metoden som finns i logic-klassen. Där skapas det nya objektet för en developer.
//        
//            if(exist == true){
//                getChat(true);
//                
//                noMatchLabel.setText("Developer already exists. Try another name.");
//            }
//            else{
//                noMatchLabel.setText("");
//            }
//        }
//        else{
//            getChat(true);
//            noMatchLabel.setText("Developer name can't be empty.");
//        }

        logic.addDeveloper(developerTextField.getText());
        developerTextField.clear();
    }
    
    // Med den här metoden kan användaren lägga till spel till en utvecklare i listan för spel. 
    @FXML
    private void handleGameButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        boolean selected = true;
        boolean exist = false;
        String x = ""; // Sätter värdet till sträng x, skall ersättas med toString från klickad rad i listview.
        
        try{    // En felhantering, om man inte valt en developer innan man lägger till ett spel, så skall ett felmeddelande dyka upp, programmet ska inte heller krasha.
             x = developerListView.getSelectionModel().getSelectedItem().toString();  //  Sparar dem variabler som behövs för metoderna i logic klassen
             
        }catch(NullPointerException e){
            
            selected = false;
            getChat(true);
            noMatchLabel.setText("Select a developer to add a game.");   
        }
                                                           
        try{    
            // Sparar dem variabler som behövs för att skapa ett spel.
            String n = gameNameTextField.getText();
            String y = gameYearOfReleaseTextField.getText(); // Sätter till "0" för att kunna kolla av med en try-catch.
            String g = gameGenreTextField.getText();



            for(Developer d : logic.getDeveloperList()){        // Mindre felhantering, kollar igenom varje developer och dess spel, för att kolla så att spelet som man
                for(Game i : logic.getDeveloperGameList()){     // Vill lägga till inte redan finns. Detta ska inte gå att göra.
                    if(i.getGameName().equals(n)){              // Finns spelet så skriver programmet ut ett felmeddelande till användaren.
                        exist = true;
                        break;        
                    }
                    else{
                        exist = false;
                    }
                }
            }

            if(exist == false && selected == true){ 
                logic.addGameToDeveloper(x, n, y, g); // Här kallar man på metoden där man lägger till ett spel för en utvecklare. Den metoden finns i singleton klassen ProgramLogic.
                noMatchLabel.setText("");  
            }
            else if(selected == true){
                getChat(true);
                noMatchLabel.setText("Game already exists.");   // Ifall spelet redan finns i listan för spel.
            }
        }catch(NumberFormatException e){
            getChat(true);
            noMatchLabel.setText("Input is not correct when adding game.");
        }
            
        gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));    
        yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
        genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
        gameTableView.setItems(logic.getDeveloperGameList());                                         // Sätter listan för tableview

        gameTableView.getItems().clear();   // Samma typ av loop som finns i metoden nedan. Detta för att spelet skall dyka upp direkt i listan när användaren trycker på "lägga till" - knappen. 
        for(Developer d : logic.getDeveloperList()){
            
            for(Game f : d.getGameList()){
                if(d.getDeveloperName().equals(x)){
                    gameTableView.getItems().add(f);
                    System.out.println("Spelet som finns för developer: " + d + " är: " + f.getGameName() + ".");
                }
            }
        }

        gameNameTextField.clear();          // Bara för att få ett snyggare och mer praktiskt GUI så rensas textfälten varje gång metoden körs. 
        gameYearOfReleaseTextField.clear();
        gameGenreTextField.clear();
    }
    
    // Med den här metoden så visas alla dem spel som finns för en developer, som användaren klickat på i listview.
    @FXML
    private void handleMouseClick(){
        getChat(false);
        noMatchLabel.setText("");
        gameTableView.getItems().clear(); // För att tableview inte ska visa de gamla objekten så rensas den varje gång.
        String x = developerListView.getSelectionModel().getSelectedItem().toString();
        System.out.println("Kommer hit och hittar: " + x);
        
        for(Developer d : logic.getDeveloperList()){    // Går igenom listan för developer för att hitta rätt
            for(Game g : d.getGameList()){              // Kollar vilka spel som den developern har
                if(d.getDeveloperName().equals(x)){     // Om det strängvärde som vi klickat på i listview finns med i listan för developer, så skriver vi ut alla spel som finns för denna i Tableview.
                    gameTableView.getItems().add(g);
                    System.out.println("Spelet som finns för developer: " + d + " är: " + g.getGameName() + ".");
                }
            }
        }  
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
            noMatchLabel.setText("Please select a developer to delete.");
        }
        
        for(Iterator<Developer> iter = logic.getDeveloperList().iterator(); iter.hasNext();){   // Använder en Iterator-loop för att ta bort en developer. Den markerade developern i listview är den developer som vi vill ta bort. 
            Developer d = iter.next();
            if(d.getDeveloperName().equals(x)){
                iter.remove();  // Här raderas det objekt som vi hittar med Iterator-loopen.
            }
        } 
        gameTableView.getItems().clear(); // Tömmer tableview för att visa att spel-objekten försvinner med developern. 
        
    }
    
    // Med den här metoden så tar vi bort det spel som användaren markerat i listview.
    @FXML
    private void handleDeleteGameButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String gameItem = ""; 
        String x = "";
        System.out.println("Detta får jag när jag trycker på ett objekt i gameTableView: " + gameItem);
       
        try{
            x = developerListView.getSelectionModel().getSelectedItem().toString();
            gameItem = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
        }catch(Exception e){
            getChat(true);
            noMatchLabel.setText("Please select a game to delete.");
        }
        
        for(Developer d : logic.getDeveloperList()){    // Går först igenom listan med developer för att jämföra med det strängvärde som vi markerat i listview
            
            if(d.getDeveloperName().equals(x)){
                for(Iterator<Game> iter = d.getGameList().iterator(); iter.hasNext();){     // Använder en Iterator-loop för att hitta det gameName-strängvärde som vi klickat på i tableview.
                    Game g = iter.next();
                    System.out.println("Kommer in i loopen för att leta upp objektet som skall tas bort.");
                    if(g.getGameName().equals(gameItem)){       // Här hittar den det värdet som vi klickat på
                        System.out.println("Den har hittat rätt objekt: " + g.getGameName() + " = " + gameItem);
                        iter.remove();  // Tar bort det markerade spelobjektet.
                        gameTableView.getItems().remove(g);     // Tar även bort den från tableview direkt, för att visa för användaren att den faktiskt försvinner.
                    }                           
                }      
            }
        }
    }
    
    // Med den här metoden så kan användaren redigera ett developer-objekt.
    @FXML
    private void handleEditDeveloperButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText("");
        String x = "";
        String y = developerTextField.getText();
        if("".equals(y)){
            getChat(true);
            noMatchLabel.setText("You must input a new name.");
        }else{
            try{
                x = developerListView.getSelectionModel().getSelectedItem().toString();
            }catch(Exception e){
                getChat(true);
                noMatchLabel.setText("Please select a developer to edit.");
            }

            for(Developer d : logic.getDeveloperList()){        // Letar upp den developer som vi vill redigera, jämför med det strängvärde som finns från att vi klickat på en developer.
                if(d.getDeveloperName().equals(x)){
                    d.setDeveloperName(y);  // Sätter ett nytt namn på en developer.
                }
            }

            developerListView.refresh();    // För att listan skall uppdateras i samband med att man trycket på "edit-knappen", så uppdateras listan direkt. 
            developerTextField.clear();
        }
        
    }
    
    // Med den här metoden så kan användaren redigera ett game-objekts Game name.
    @FXML
    public void handleEditAction1(CellEditEvent<Game, String> t) {
        getChat(false);
        noMatchLabel.setText("");
        boolean exist = false;
        
        for(Developer d : logic.getDeveloperList()){            // Felhantering för att se till att det nya gameName inte finns hos varken den markerade developern
            for(Game g : logic.getDeveloperGameList()){         // eller hos någon annan developer.
                if(g.getGameName().equals(t.getNewValue())){
                    exist = true;
                    break;
                }
            }
        }
        if(exist == true){
            getChat(true);
            noMatchLabel.setText("That game already exists.");
        }
        else{
           ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGameName(t.getNewValue()); 
        }
 
    }
    
    // Med den här metoden så kan användaren redigera ett game-objekts Year of Release.
    @FXML
    public void handleEditAction2(CellEditEvent<Game, String> t) {
        getChat(false);
        noMatchLabel.setText("");
 
        ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setYearOfRelease(t.getNewValue());

    }
    
    // Med den här metoden så kan användaren redigera ett game-objekts Genre.
    @FXML
    public void handleEditAction3(CellEditEvent<Game, String> t) {
        getChat(false);
        noMatchLabel.setText("");
        ((Game) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setGenre(t.getNewValue());
        
        
    }
    
    
    // Nedanstående metod är min gamla metod för att ändra i ett gameobjekt. Vill inte ta bort den då koden kan vara intressant i framtiden. 
    //Här använder jag mig utav textfält och knappar för att redigera ett gameobjekt.
    // Med den här metoden så kan användaren redigera ett game-objekt.
//    @FXML
//    private void handleEditGameButtonAction(ActionEvent event){
//        getChat(false);
//        noMatchLabel.setText("");
//        String gName = "";
//        String gYor = "";
//        String gGenre = "";
//        boolean compare = false; // FÖr att kolla om GameName redan finns hos någon annan developer.
//        
//        try{
//            gName = gameTableView.getSelectionModel().selectedItemProperty().get().getGameName();
//            gYor = gameTableView.getSelectionModel().selectedItemProperty().get().getYearOfRelease();
//            gGenre = gameTableView.getSelectionModel().selectedItemProperty().get().getGenre();
//        }catch(Exception e){
//            getChat(true);
//            noMatchLabel.setText("Please select a game to edit.");
//        }
//        
//        for(Developer d : logic.getDeveloperList()){            // Går igenom listan för att hitta den developer vars spelobjekt man vill redigera.
//            System.out.println("KOMMER IN PÅ FÖRSTA LOOPEN");
//            for(Game g : logic.getDeveloperGameList()){
//                System.out.println("KOMMER IN PÅ ANDRA LOOPEN");
//                if(g.getGameName().equals(gName)){
//                    if(gameNameTextField.getText().trim().equals("")){ // Ifall gameName-fätet är tomt ska inget redigeras.
//                        System.out.println("GameName textfield är tomt.");
//                    }
//                    else{
//                        for(Developer j : logic.getDeveloperList()){        // Felhantering, kollar så att det spelnamn man vill ändra till inte finns hos varken den developer man är på eller på
//                            for(Game i : logic.getDeveloperGameList()){     // någon av dem andra.
//                               if(i.getGameName().equals(gameNameTextField.getText())){
//                                   compare = true;
//                                   break;
//                               }else{
//                                   compare = false;
//                               } 
//                            }
//                        }
//                        if(compare == true){
//                            getChat(true);
//                            noMatchLabel.setText("That game already exists.");
//                        }
//                        else{
//                        System.out.println("Här sätts det nya GameName till: " + gameNameTextField.getText());  
//                        g.setGameName(gameNameTextField.getText());                 // Sätter gameName till de som användaren matat in i gameName-textfield.
//                        gameName.setCellValueFactory(new PropertyValueFactory<Game,String>("gameName"));
//                        gameNameTextField.clear();
//                        }
//                    }
//                    if(gameYearOfReleaseTextField.getText().trim().equals("")){     // Ifall yearOfRelease-fätet är tomt ska inget redigeras.
//                        System.out.println("YearOfRelease textfield är tomt.");
//                    }
//                    else{
//                        try{        // Felhantering så att programmet inte kraschar om användaren matar in en sträng av bokstäver i Year of release-textfield.
//                            System.out.println("Här sätts det nya Year of release till: " + Integer.parseInt(gameYearOfReleaseTextField.getText()));
//                            g.setYearOfRelease(gameYearOfReleaseTextField.getText());     // Sätter yearOfRelease till det som användaren matat in i yearOfRelease-textfield.
//                            yearOfRelease.setCellValueFactory(new PropertyValueFactory<Game,String>("yearOfRelease"));
//                            gameYearOfReleaseTextField.clear();
//                        }catch(Exception e){
//                            getChat(true);
//                            noMatchLabel.setText("Year of release-textfield has to be a number.");
//                        }
//                    }
//                    if(gameGenreTextField.getText().trim().equals("")){     // Ifall gameGenre-fältet är tomt så ska inget redigeras.
//                        System.out.println("GameGenre textfield är tomt.");
//                    }
//                    else{
//                        System.out.println("Här sätts det nya Genre till: " + gameGenreTextField.getText());
//                        g.setGenre(gameGenreTextField.getText());       // Sätter gameGenre till de som användaren matat in i gameGenre-textfield.
//                        genre.setCellValueFactory(new PropertyValueFactory<Game,String>("genre"));
//                        gameGenreTextField.clear();
//                    }
//                }
//            }
//        }
//        gameTableView.refresh();  // Uppdaterar Tableview så att användaren kan se de nya ändringarna såfort man klickar på "edit-knappen".
//    }
    
    // Med den här metoden så kan användaren söka efter ett developer-objekt och därefter se endast det objektet i listview. 
    @FXML
    private void handleSearchButtonAction(ActionEvent event){
        getChat(false);
        noMatchLabel.setText(""); // Sätter label till "ingenting".
        String s = searchTextField.getText(); // Sparar det strängvärde som användaren skriver in, i variabeln s.
        System.out.println(s);
        ObservableList<Developer> tempDeveloperList = FXCollections.observableArrayList(); // Här skapas en temporär Observablelista som används för att visa en utvecklare.
        
        if(searchTextField.getText().trim().isEmpty()){ // Om textfältet är tomt när man trycker på "sök". Då ska den visa alla developers som finns i den riktiga listan.
            System.out.println("Sökfältet är tomt.");
            for(Developer d : logic.getDeveloperList()){
                System.out.println("Finns i listan för developer: " + d.getDeveloperName());
            }
            
            developerListView.setItems(logic.getDeveloperList());
            getChat(true);
            noMatchLabel.setText("Showing all the developers!");
            
        }
        else{                                               // Om textfältet inte är tomt ska den börja söka i listan för developers efter det objekt som användaren sökt efter.
            for(Developer d : logic.getDeveloperList()){
                System.out.println("Kommer in i loopen för att leta, hittar: " + d.getDeveloperName());
                
                
                
                    if(d.getDeveloperName().equals(s)){ // Om det finns en developer vars namn stämmer överens med det användaren sökt efter, så skall ett temporärt objekt skapas till den nya listan.
                        tempDeveloperList.add(new Developer(d.getDeveloperName()));
                        developerListView.setItems(tempDeveloperList);  // Här sätter vi listview med den temporär listan.
                        noMatchLabel.setText("");
                        searchTextField.clear();
                        break;   
                    }
                    else if(!d.getDeveloperName().equals(s)){
                        getChat(true);
                        noMatchLabel.setText("Can't find developer."); // Om man inte kan hitta den developer som användaren sökt efter, skall en label i GUI visa att den inte finns.
                        searchTextField.clear();
                    }
                    
            }
        }            
    }
    
    // Med den här metoden så kan användaren söka efter ett game-objekt och därefter se endast det objektet i tableview.
    @FXML
    private void handleSearchGameAction(ActionEvent event){
        getChat(false);
        boolean found = false; // En extra variabel som behövs för att se till att labeln inte sätter att objektet hittats, då det har blivit hittat i en annan developer. 
        ObservableList<Game> tempGameList = FXCollections.observableArrayList(); // En temporär observablelista för att visa det game-objekt som användaren sökt efter.
        noMatchLabel.setText(""); // Sätter label till "ingenting".
        String s = searchTextField.getText(); // Sparar det strängvärde som användaren skriver in i variabeln s.
        
            if(searchTextField.getText().trim().isEmpty()){ // Om textfältet är tomt när man trycker på "sök". Då ska den visa alla spel som finns för den tillfälligt markerade Developern.
                System.out.println("Sökfältet är tomt.");
                
                try{
                    String x = developerListView.getSelectionModel().getSelectedItem().toString();
                    getChat(true);
                    noMatchLabel.setText("Showing all games for the developer");
                    gameTableView.getItems().clear();
                    for(Developer d : logic.getDeveloperList()){
                        for(Game g : d.getGameList()){
                            if(d.getDeveloperName().equals(x)){
                            gameTableView.getItems().add(g);
                            System.out.println("Spelet som finns för developer: " + d + " är: " + g.getGameName() + ".");
                            }
                        }
                    } 
                }catch(Exception e){
                    getChat(true);
                    noMatchLabel.setText("There are no games.");
                }
            }
            else{
                for(Developer d : logic.getDeveloperList()){
                    
                    for(Game g : d.getGameList()){      // Går igenom listan med spel för en deleloper, för att hitta det som användaren sökt efter.
                        System.out.println("Spel som finns i listan för " + d.getDeveloperName() + " är: " + g.getGameName());
                        if(g.getGameName().equals(s)){      // Här hittar den rätt spel.
                            System.out.println("Nu har den hittat rätt spel.");
                            noMatchLabel.setText("");
                            found = true;   // Här sätter vi variabeln "found" till true, detta för att den går igenom alla developers spel, så kommer den annars att skriva ut att spelet inte finns, även om den hittat spelet för en tidigare developer. 
                            developerListView.requestFocus();       // När vi hittat spelet, så markerar vi den developer som spelet tillhör, detta för att göra det bekvämt för användaren. Då denna trycket "sök" när textfältet är tomt
                            developerListView.getSelectionModel().select(d);    // så kommer alla spel visas för den markerade developern. 
                            
                            tempGameList.add(new Game(g.getGameName(), g.getYearOfRelease(), g.getGenre()));    // Här lägger vi till det temporar gameobjektet och sätter sedan tableview med detta.
                            gameTableView.setItems(tempGameList);
                            searchTextField.clear();
                            break;
                        }
                        else if(!g.getGameName().equals(s)){
                            System.out.println("Nu har den hittat fel spel.");
                            if(found == false){         // Så länge att den aldrig hittat ett spel-objekt som finns för någon developer, så kan programmet skriva ut att det användaren sökt efter inte finns med.
                            getChat(true);
                            noMatchLabel.setText("Can't find game.");
                            searchTextField.clear();
                            }
                        }
                    }
                }
            }
    
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
        
       Client client = ClientBuilder.newClient(); 
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
         
       developerListView.setItems(logic.getDeveloperList()); // Det första som händer i programmet är att man laddar in en lista med några developers. 
//        logic.getAllDevelopers();
       
       gameName.setCellFactory(TextFieldTableCell.forTableColumn());
       yearOfRelease.setCellFactory(TextFieldTableCell.forTableColumn());
       genre.setCellFactory(TextFieldTableCell.forTableColumn());
       
    }    
    
}
