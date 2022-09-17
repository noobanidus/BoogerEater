package studio.robotmonkey1000.boogereater.common.configuration;

public class ModConfigurations {

  public static BoogerConfig BOOGER_CONFIG;

  public static void setupConfigs() {
    BOOGER_CONFIG = new BoogerConfig().readConfig();
  }

}
