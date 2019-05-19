package dropbox;

import javax.inject.*;
import java.io.*;
import java.net.URL;
import com.dropbox.core.*;
import com.dropbox.core.http.SSLConfig;
import com.dropbox.core.v1.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.SpaceUsage;
import com.typesafe.config.Config;
import utils.Configuration;

@Singleton
public class DropBoxCoreAPI {
  private final Configuration conf  = new Configuration();
  private String ACCESS_TOKEN       = conf.token();

  // Create Dropbox client
  private DbxRequestConfig config = DbxRequestConfig.newBuilder(conf.appName()).build();
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
