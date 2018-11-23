/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author ryanb
 */
public class MainPaneController implements Initializable {

    @FXML
    private ListView<Reminder> reminderListView;
    @FXML
    private Button newReminderButton;
    @FXML
    private TextArea selectedReminderTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reminderListView.setOnMouseClicked(val -> {
            Reminder rem = reminderListView.getSelectionModel().getSelectedItem();
            selectedReminderTextArea.setText(rem.getMessage() + "\n" + rem.getTime().toString());
         });
        
        reminderListView.setCellFactory(param -> {
            ListCell<Reminder> cell = new ListCell<Reminder>() {
                @Override
                protected void updateItem(Reminder item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null) {
                        setText(item.getTime().toString() + "\n" + item.getMessage());
                    }
                }
            };
            return new ListCell<Reminder>();
        });
    }    

    @FXML
    private void newReminderAction(ActionEvent event) {
    }
    
}
