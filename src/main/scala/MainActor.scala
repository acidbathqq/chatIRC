import User.MySerializable
import akka.actor.typed.Behavior
import akka.actor.typed.pubsub.Topic
import akka.actor.typed.scaladsl.Behaviors

object MainActor {
  def apply(chatControllerImpl: ChatControllerImpl): Behavior[MySerializable] = Behaviors.setup({context =>
    val actor = context.spawn(User(chatControllerImpl), "user")
    val topic = context.spawn(Topic[MySerializable]("chat"), "chat")
    topic ! Topic.Subscribe(actor)

    Behaviors.receiveMessage{
      msg => topic ! Topic.Publish(msg)
        Behaviors.same
    }
  })
}
