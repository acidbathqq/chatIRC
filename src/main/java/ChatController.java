import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatController implements Initializable {

    @FXML
    public TextArea chatInfo;

    @FXML
    public TextArea messagesArea;

    @FXML
    public TextField messageInput;

    @FXML
    public TextArea onlineUsersList;

    @FXML
    public Button logoutButton;

    @FXML
    public Button sendMessageButton;

    @FXML
    void pressLogoutButton(ActionEvent event) {

    }

    @FXML
    void pressSendMessageButton(ActionEvent event) {

    }

    public void initialize(URL location, ResourceBundle resources) {

    }

}
