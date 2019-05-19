### ![alt text][logo]

[logo]:https://www.underconsideration.com/brandnew/archives/dropbox_2017_logo.png "Dropbox API with Java Play Framework"

Dropbox is a modern workspace designed to reduce busywork-so you can focus on the things that matter. Sign in and put your creative energy to work


#### **Project Requirements**

| Tool  | Version | 	Description  |
|:-----:|:-------:| -------------- |
| Java	| 1.8 		| JDK ia a development environment for building applications and components. |
| Scala | 2.12.8  | Scala combines object-oriented and functional programming in one concise, high-level language.|
| SBT   | 1.2.8   | Interactive build tool. |
| Play Framework 	| 2.7.x | The High Velocity Web Framework For Java and Scala. |
| Dropbox	| 2 | Dropbox is a modern workspace designed to reduce busywork-so you can focus on the things that matter. |

> [Visit Dropbox for more details on how to setup account](https://www.dropbox.com/h.)

# JAVA-PLAY-DROPBOX-WEBSOCKET
Dropbox credentials that need to get ready.

* Application Name
* App key: XXXXXXXXXXXXXXXXXXX
* App secret: XXXXXXXXXXXXXXXXXXX
* Access Token: XXXXXXXXXXXXXXXXXXX
	*- This access token can be used to access your account (user@email.com) via the API. Don’t share your access token with anyone.*

>

#### **Java Play Configuration API**

application.conf

	dropbox {
		ACCESS_TOKEN = "" <!-- your_app_key -->
		APP_NAME = "" <!-- your_application_name -->
	}



#### **Websocket Requests**

Upload Files

	{
		"command": "upload",
		"path": "object path",
		"folder": "folder name"
	}


Reads Files and Folders

	{
		"command": "read",
		"folder": "folder name",
		"name": "file name"
	}


Delete files and folder

	{
		"command": "delete",
		"path": "object path"
	}


View Files and Folders

	{ "command": "view-folders" }


create folder

	{
		"command": "create-folder",
		"folder": "folder name"
	}


#### **Implement Using Java**

New Instance of DropBoxCoreAPI.

	DropBoxCoreAPI api = new DropBoxCoreAPI();

Initialize DropBoxCoreAPI API.

	api.initialize();

Generate Folder Name.

	String folderName = "/test_java_createFolder" + System.currentTimeMillis();

View Lists of Folders on the Account.

	ListFolderResult listFolder = api.listFolder();

Create new Folder on the Server.

	FolderMetadata folder = api.createFolder(folderName);

Upload or Add new Files or Folders.

	FileMetadata uploaded = api.uploadFile(fileUploadPath, folderName + fileName);

Read specific file on the server.

	FileMetadata read = api.readFile(folderName + fileName, fileUploadPath);

Delete/Remove file on the server using file path.

	Metadata deleted = api.deleteFile(folderName + fileName);
