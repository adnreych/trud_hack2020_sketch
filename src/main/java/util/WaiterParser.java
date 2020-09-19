package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import trud.model.CVPosition;
import trud.model.JSONPositionModel;
import trud.model.WaiterCompetencyModel;

public class WaiterParser {
	
	public Set<WaiterCompetencyModel> parseWaiters(Set<JSONPositionModel> jsonPositionModels) {
		jsonPositionModels
		.stream()
		.map(e -> {
			WaiterCompetencyModel waiterCompetencyModel = new WaiterCompetencyModel();
			List<CVPosition> positions = e.getPositions();
			waiterCompetencyModel.setWork(
					positions.size() == 0 
					? "Официант" 
					: positions.get(positions.size()).getPosition());
			waiterCompetencyModel.setTargetEdu(false);
			waiterCompetencyModel.setEducation("");
			waiterCompetencyModel.setYearGraduate(0);
			waiterCompetencyModel.setExperience(true);
			waiterCompetencyModel.setPositions(positions);
			waiterCompetencyModel.setAdditionalSkills(new ArrayList<String>());
			waiterCompetencyModel.setLanguages(new ArrayList<Map<String,String>>());
			waiterCompetencyModel.setDishes(new ArrayList<String>());
			waiterCompetencyModel.setTimetable(new ArrayList<String>());
			
			return waiterCompetencyModel;
		})
		.collect(Collectors.toList());
		return null;
		
	}
}
