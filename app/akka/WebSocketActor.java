package akka;

import java.io.*;
import akka.actor.*;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import com.fasterxml.jackson.databind.*;
import dropbox.DropBoxCoreAPI;

public class WebSocketActor extends AbstractActor {
  private final DropBoxCoreAPI api = new DropBoxCoreAPI();

  public static Props props(ActorRef out) {
    return Props.create(WebSocketActor.class, out);
  }

  private <T> T ObjectToJSON(T data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    if (data.getClass().equals(String.class)) {
      // Separate Json from String response.
      return (T) mapper.readTree(data.toString().split(":", 2)[1]);
    }
    else return (T) mapper.readTree(data.toString());
  }

  private final ActorRef out;

  public WebSocketActor(ActorRef out) {
    this.out = out;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(JsonNode.class, json -> { // match every users request.
        try {
          switch (json.findPath("command").textValue()) {
            case "delete":
              out.tell(delete(json), getSelf());
              break;

            case "read":
              out.tell(read(json), getSelf());
              break;

            case "upload":
              out.tell(upload(json), getSelf());
              break;

            case "create-folder":
              out.tell(createFolder(json), getSelf());
              break;

            case "view-folders":
              out.tell(viewFolders(), getSelf());
              break;

            default: break;
          }
        } catch(Exception e) {
          out.tell(ObjectToJSON(e.getMessage()), getSelf());
        }
      })
      .matchAny(v -> System.out.println("received unknown message"))
      .build();
  }

  private final <T> T delete(JsonNode json) throws DbxException, IOException {
    String   path       = json.findPath("path").textValue();
    Metadata deleteFile = api.deleteFile(path);

    return (T) ObjectToJSON(deleteFile);
  }

  private final <T> T read(JsonNode json) throws DbxException, IOException {
    String   folder       = json.findPath("folder").textValue();
    String   name         = json.findPath("name").textValue();
    FileMetadata readFile = api.readFile(folder, name);

    return (T) ObjectToJSON(readFile);
  }

  private final <T> T upload(JsonNode json) throws DbxException, IOException {
    String   path         = json.findPath("path").textValue();
    String   folder       = json.findPath("folder").textValue();
    FileMetadata readFile = api.uploadFile(path, folder);

    return (T) ObjectToJSON(readFile);
  }

  private final <T> T createFolder(JsonNode json) throws DbxException, IOException {
    String   folder       = json.findPath("folder").textValue();
    FileMetadata readFile = api.createFolder(folder);

    return (T) ObjectToJSON(readFile);
  }

  private final <T> T viewFolders() throws DbxException, IOException {
    FileMetadata readFile = api.listFolder();

    return (T) ObjectToJSON(readFile);
  }

  public void preStart() throws Exception {
    // running main Akka defaults
    super.preStart();

    // Intialize Dropbox API.
    api.initialize();
  }
}
