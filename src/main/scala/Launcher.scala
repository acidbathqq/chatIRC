import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.stage.Stage
import javafx.scene.{Parent, Scene}


class Launcher extends Application{
  override def start(stage: Stage): Unit = {
    val parent: Parent = FXMLLoader.load(getClass.getResource("LoginWindow.fxml"))
    stage.setScene(new Scene(parent))
    stage.setTitle("Chat")
    stage.setResizable(false)
    stage.show()
  }
}
