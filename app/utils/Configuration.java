package utils;

import javax.inject.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigException;

@Singleton
public class Configuration {
	private final Config conf = ConfigFactory.load();

  public String token() {
  	if (conf.hasPath("dropbox.ACCESS_TOKEN")) {
        return conf.getString("dropbox.ACCESS_TOKEN");
    } else {
        throw new ConfigException.Missing("dropbox.ACCESS_TOKEN");
    }
  }

  public String appName() {
  	if (conf.hasPath("dropbox.APP_NAME")) {
        return conf.getString("dropbox.APP_NAME");
    } else {
        throw new ConfigException.Missing("dropbox.APP_NAME");
    }
  }
}
