import javafx.fxml.FXMLLoader
import javafx.stage.Stage
import javafx.scene.{Parent, Scene}
import javafx.event.ActionEvent


class LoginControllerImpl extends LoginController {

  override def pressLoginButton(event: ActionEvent): Unit = {
    if(nicknameTextField.getText.trim != "" && portTextField.getText.trim != "") {
      loginButton.getScene.getWindow.hide()
      UserInfo.getInstance.myNickname = nicknameTextField.getText.trim
      UserInfo.getInstance.myPort = portTextField.getText.trim

      val fxmlLoader = new FXMLLoader(getClass.getResource("ChatWindow.fxml"))
      val root = fxmlLoader.load[Parent]()
      val stage = new Stage()

      stage.setScene(new Scene(root))
      stage.setTitle("Chat")
      stage.setResizable(false)
      stage.show()

    }
  }


}
