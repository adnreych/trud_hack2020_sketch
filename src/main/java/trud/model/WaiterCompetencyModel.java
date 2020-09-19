package trud.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class WaiterCompetencyModel {
	
	String work;
	Boolean targetEdu;
	String education;
	Integer yearGraduate;
	Boolean experience;
	List<CVPosition> positions;
	List<String> additionalSkills;
	List<Map<String, String>> languages;
	List<String> dishes;
	List<String> timetable;
	
}
