import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}


object User {
  trait MySerializable
  final case class PublicMessage(from: String, text: String) extends MySerializable                           //обычное сообщение в группу
  final case class PrivateMessage(from: String, to: String, text: String) extends MySerializable              //приватное сообщение пользователя
  final case class MyOnlineStatus(nickname: String) extends MySerializable                                    //добавляет ник пользователя в список онлайна
  final case class SystemLogout(nickname: String) extends MySerializable                                      //выход
  final case class GetOnlineStatus() extends MySerializable                                                   //узнать статус онлайна

  def apply(chatControllerImpl: ChatControllerImpl): Behavior[MySerializable] =Behaviors.setup({context => new UserBehavior(context, chatControllerImpl)})

  class UserBehavior(context: ActorContext[MySerializable], chatControllerImpl: ChatControllerImpl) extends AbstractBehavior[MySerializable](context) {
    val nickname = UserInfo.getInstance.myNickname
    override def onMessage(msg: MySerializable): Behavior[MySerializable] = {
      msg match {
        case PublicMessage(from, text) => chatControllerImpl.messagesArea.appendText(s"[$from]: $text\n")
          this
        case PrivateMessage(from, to, text) => if (from.equals(chatControllerImpl.nickname) | to.equals(chatControllerImpl.nickname))
          chatControllerImpl.messagesArea.appendText(s"[PRIVATE] [from $from ]: $text\n")
          this
        case MyOnlineStatus(nickname) =>
          if(!chatControllerImpl.onlineUsersList.getText.contains(nickname))
            chatControllerImpl.onlineUsersList.appendText(s"\n$nickname")
          this
        case SystemLogout(nickname) => chatControllerImpl.onlineUsersList.setText(chatControllerImpl.onlineUsersList.getText.replace(s"\n${nickname}", "").trim)
          this
        case GetOnlineStatus() => chatControllerImpl.system ! MyOnlineStatus(nickname)
          this
        case _ => this
      }
    }
  }
}

