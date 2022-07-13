import javafx.application.Platform
import javafx.event.ActionEvent
import java.net.URL
import java.util.ResourceBundle
import User._
import akka.actor.typed.ActorSystem
import akka.cluster.typed.Cluster
import com.typesafe.config.ConfigFactory


class ChatControllerImpl extends ChatController {
  var system: ActorSystem[MySerializable] = _
  val nickname = UserInfo.getInstance.myNickname
  val port = UserInfo.getInstance.myPort;

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    val nickname = UserInfo.getInstance.myNickname
    if (!port.equals("")) {
      val config = ConfigFactory.parseString(
        s"""
            akka.remote.artery.canonical.port=$port
            """).withFallback(ConfigFactory.load())
      this.system = ActorSystem(MainActor(this), "ClusterSystem", config)
      val cluster = Cluster(this.system)
      chatInfo.appendText(s"Welcome to chat <$nickname>\n To send private message use command without brackets: \n'/p;Nickname;Text'        where Nickname is receiver and Text is your message\n\n")
      getOnline
    }
  }

  override def pressSendMessageButton(event: ActionEvent): Unit = {
    val nickname = UserInfo.getInstance.myNickname // дублируется, потому что иначе в TextArea nickname = null
    val message = messageInput.getText.trim
    if (message != "") {
      message match {
        // /p;nickname;message    для приватного
        case input: String if message.contains(s"/p") =>
          val command = input.split(";").toVector
          val to = command(1)
          val text = command(2)
          system ! PrivateMessage(nickname, to, text)
        case _ => system ! PublicMessage(nickname, message)
      }
      messageInput.clear()
    }
  }

  override def pressLogoutButton(event: ActionEvent): Unit = {
    val nickname = UserInfo.getInstance.myNickname
    this.system ! SystemLogout(nickname)
    Platform.exit()
    System.exit(0)
  }

  private def getOnline: Unit = {
    Thread.sleep(3000)
    this.system ! GetOnlineStatus()
  }




}