package controllers;

import play.mvc.*;
import javax.inject.*;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import akka.WebSocketActor;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
  private final ActorSystem actorSystem;
  private final Materializer materializer;

  @Inject
  public HomeController(ActorSystem actorSystem, Materializer materializer) {
    this.actorSystem = actorSystem;
    this.materializer = materializer;
  }

  public WebSocket ws() {
    return WebSocket.Json.accept( request ->
      ActorFlow.actorRef(WebSocketActor::props, actorSystem, materializer));
  }

  public Result index() {
    return ok(views.html.index.render());
  }
}

