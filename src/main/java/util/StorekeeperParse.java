package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import trud.model.CVPosition;
import trud.model.JSONPositionModel;
import trud.model.StorekeeperCompetencyModel;
import trud.model.WaiterCompetencyModel;

public class StorekeeperParse {
	
	final String SENTENSES_MATCHER = "\\n.*(?=\\n)";
	final String SPHERE_MATCHER = "одежд|продукт|стройматер|строительные|продовольств";
	final String PROG_MATCHER = "програм|1с|microsoft|windows|word|excel|sap";
	final String TOOLS_MATCHER = "терминал|тсд|рохл|погрузчик|рокар|транспор|молот";
	final String WORK_TYPES_MATCHER = "выполнение|прием|приним|выдавал|выдача|обучение|контрол|загрузк|разгруз|планирова|уборка";

	
	
	public List<StorekeeperCompetencyModel> parseStoreKeepers(Set<JSONPositionModel> jsonPositionModels) {
		return jsonPositionModels
		.stream()
		.map(e -> {
			ArrayList<String> sentenses = new ArrayList<>();
			StorekeeperCompetencyModel storekeeperCompetencyModel = new StorekeeperCompetencyModel();
			List<CVPosition> positions = e.getPositions();
			String s = e.getRawData().stream().collect(Collectors.joining(", "));
			Pattern pattern = Pattern.compile(SENTENSES_MATCHER);		
			Matcher matcher = pattern.matcher(s);
		    while (matcher.find()) {
		        sentenses.add(s.substring(matcher.start(), matcher.end()));
		    }
			storekeeperCompetencyModel.setQ1(
					positions.size() == 0 
					? "Кладовщик" 
					: positions.get(positions.size() - 1).getPosition());
			
			Optional<CVPosition> hasExOpt = positions
					.stream()
					.filter(el -> (el.getPosition().contains("Кладовщик") || el.getPosition().contains("кладовщик"))).findFirst();
			Boolean hasEx = hasExOpt.isPresent() ? true : false;
			storekeeperCompetencyModel.setQ2(hasEx);
			storekeeperCompetencyModel.setQ2d("Да");
			Long exInYear = positions
				.stream()
				.filter(p -> p.getEnd() != 0L)
				.map(p -> p.getEnd() - p.getStart()).collect(Collectors.summingLong(Long::longValue));
			exInYear =(exInYear/1000) / 31536000;
			storekeeperCompetencyModel.setQ3(exInYear);
			List<String> sphere = sentenses.stream().filter(el -> {
				Pattern spherePattern = Pattern.compile(SPHERE_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher sphereMatcher = spherePattern.matcher(el);
				while (sphereMatcher.find()) {
					return true;
			    }
				return false;
			}).collect(Collectors.toList());
			storekeeperCompetencyModel.setQ4(sphere);
			storekeeperCompetencyModel.setQ5(positions);
			storekeeperCompetencyModel.setQ6(new ArrayList<String>());
			
			List<String> programs = sentenses.stream().filter(el -> {
				Pattern progPattern = Pattern.compile(PROG_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher progMatcher = progPattern.matcher(el);
				while (progMatcher.find()) {
					return true;
			    }
				return false;
			}).collect(Collectors.toList());
			storekeeperCompetencyModel.setQ7(programs);
			List<String> tools = sentenses.stream().filter(el -> {
				Pattern toolsPattern = Pattern.compile(TOOLS_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher toolsMatcher = toolsPattern.matcher(el);
				while (toolsMatcher.find()) {
					return true;
			    }
				return false;
			}).collect(Collectors.toList());
			storekeeperCompetencyModel.setQ8(tools);
			List<String> types = sentenses.stream().filter(el -> {
				Pattern typesPattern = Pattern.compile(WORK_TYPES_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher typesMatcher = typesPattern.matcher(el);
				while (typesMatcher.find()) {
					return true;
			    }
				return false;
			}).collect(Collectors.toList());
			storekeeperCompetencyModel.setQ9(types);
			storekeeperCompetencyModel.setQ10(0);
			storekeeperCompetencyModel.setQ11(0);
			
			return storekeeperCompetencyModel;
		})
		.collect(Collectors.toList());
		
		
	}

}
