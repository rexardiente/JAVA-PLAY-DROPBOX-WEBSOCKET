package dropbox;

import java.io.*;
import java.net.URL;
import com.dropbox.core.*;
import com.dropbox.core.http.SSLConfig;
import com.dropbox.core.v1.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.SpaceUsage;

public class DropBoxCoreAPI {
  private static final String ACCESS_TOKEN =
  	"rNwcbKqj54AAAAAAAAAAVciCcQiVAFcn681bwBwp8VobBlGoIvAJerlYvkTWqr49";

  // Create Dropbox client
  private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/google-oauth2-impl").build();
  private DbxClientV2 client      = new DbxClientV2(config, ACCESS_TOKEN);
  private FullAccount account     = null;

  public void initialize() {
		try { FullAccount account = client.users().getCurrentAccount(); }
    catch (DbxException dbxe) { dbxe.printStackTrace(); }
  }

  public <T> T createFolder(String folderName) throws DbxException, IOException {
    return (T) client.files().createFolder(folderName);
  }

  public <T> T listFolder() throws DbxException, IOException {
		return (T) client.files().listFolder("");
	}

  // Upload "test.file" to Dropbox
  public <T> T uploadFile(String path, String foldername) throws DbxException, IOException {
    InputStream  in       = new FileInputStream(path);
    FileMetadata metadata = client.files().uploadBuilder(foldername).uploadAndFinish(in);
    return (T) metadata;
  }

  public <T> T readFile(String foldername, String filename) throws DbxException, IOException {
    // output file for download --> storage location on local system to download file
    FileOutputStream downloadFile = new FileOutputStream("" + filename);
    FileMetadata metadata = client.files().downloadBuilder(foldername).download(downloadFile);

    // Close after reading..
  	downloadFile.close();
    return (T) metadata;
  }

  public <T> T deleteFile(String path) throws DbxException, IOException {
    return (T) client.files().delete(path);
  }

  public <T> T getDropboxSize() throws DbxException, IOException {
    return (T) client.users().getSpaceUsage().getAllocation();
  }
}
