import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		WaiterParser waiterParser = new WaiterParser(); 
		System.out.println("sonPositionModels size" + jsonPositionModels.size());
		List<WaiterCompetencyModel> waiterCompetencyModels = waiterParser.parseWaiters(jsonPositionModels);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 try (FileWriter writer = new FileWriter("waiters_formal_cvv.json")) {
	            gson.toJson(waiterCompetencyModels, writer);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 		
	}

}
