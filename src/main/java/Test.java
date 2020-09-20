import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import trud.model.JSONPositionModel;
import trud.model.StorekeeperCompetencyModel;
import trud.model.WaiterCompetencyModel;
import util.CVModelParser;
import util.StorekeeperParse;
import util.UidParser;
import util.WaiterParser;

public class Test {
	public static void main(String[] args) {
		final UidParser cvModelParser = new UidParser();
		Set<JSONPositionModel> jsonPositionModels = cvModelParser.CVModelParse("resume_to_hackaton.csv");
		StorekeeperParse waiterParser = new StorekeeperParse(); 
		List<StorekeeperCompetencyModel> waiterCompetencyModels = waiterParser.parseStoreKeepers(jsonPositionModels);
		Gson gson = new Gson();
		 try (FileWriter writer = new FileWriter("storeceepers_demo.json")) {
	            gson.toJson(waiterCompetencyModels, writer);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 		
	}

}
