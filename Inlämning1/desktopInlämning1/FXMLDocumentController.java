/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlämning1;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author rille
 */
public class FXMLDocumentController implements Initializable {
    
    ProgramLogic logic = ProgramLogic.getInstance();
   
//    public static ArrayList<Developer> developerArrayList = new ArrayList<Developer>();
    public ObservableList<Developer> obDeveloperList = FXCollections.observableArrayList(logic.getDeveloperList());
    
    
    @FXML
    private TextField developerTextField;
    @FXML
    private TextField gameTextField;
    @FXML
    private ListView developerListView;
    @FXML
    private ListView gameListView;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
      
       
       
       
       for(Developer i : logic.getDeveloperList()){
           System.out.println("Arraylisten: " + i.toString());
       }
       
       for(Developer i : obDeveloperList){
           System.out.println("Oblisten: " + i.toString());
       }
         
        
       developerListView.setItems(obDeveloperList);
        

    }    
    
}
