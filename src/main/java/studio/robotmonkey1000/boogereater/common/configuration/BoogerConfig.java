package studio.robotmonkey1000.boogereater.common.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import studio.robotmonkey1000.boogereater.BoogerMain;

public class BoogerConfig {

	//Booger Eater sayings
	@Expose public ArrayList<String> BoogerSayings = new ArrayList<String>(); 
	@Expose public boolean enableSpeechBubble = false;
	
	private static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	private String rootLocation = "config/" + BoogerMain.MOD_ID + "/";
	
	
	public void generateConfig() {
		reset();
		
		try {
			writeConfig();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getConfigFile() {
		return new File(rootLocation + "boogereater.json");
	}
	
	public BoogerConfig readConfig() {
		try {
			return GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());
		} catch(FileNotFoundException e) {
			generateConfig();
		}
		
		return this;
	}
	
	public void reset() {
		BoogerSayings.add("Hermitcraft 7! When!!");
		BoogerSayings.add("Y ban! I did no wrong.");
		enableSpeechBubble = false;
	}
	
	public void writeConfig() throws IOException {
		
		//Folder location for booger eater config
		File dir = new File(this.rootLocation);
		
		//Booger eater config file
		File configFile = getConfigFile();
		
		//Checks and creates directory. Throws exception if it cannot.
		if(!dir.exists()) {
			if(!dir.mkdirs()) {
				return;
			}
		}
		
		//Checks to make sure the file exists. If it does not exist try to create it.
		if(!configFile.exists()) {
			if(!configFile.createNewFile()) {
				return;
			}
		}
		
		//Create a file writer for writing the json file to disk.
		FileWriter fileWriter = new FileWriter(configFile);
		
		//Write the json to disk
		GSON.toJson(this, fileWriter);
		
		//Flush and close the writer
		fileWriter.flush();
		fileWriter.close();
		
		
	}
}
