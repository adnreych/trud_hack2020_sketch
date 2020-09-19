import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import com.google.gson.Gson;

import trud.model.JSONPositionModel;
import util.CVModelParser;

public class Test {
	
	public static void main(String[] args) {
		final CVModelParser cvModelParser = new CVModelParser();
		Set<JSONPositionModel> jsonPositionModels = cvModelParser.CVModelParse("resume_to_hackaton.csv");
		Gson gson = new Gson();
		 try (FileWriter writer = new FileWriter("employers.json")) {
	            gson.toJson(jsonPositionModels, writer);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

}
