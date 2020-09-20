package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import trud.model.CVPosition;
import trud.model.JSONPositionModel;
import trud.model.WaiterCompetencyModel;

public class WaiterParser {
	
	final String SENTENSES_MATCHER = "\\n.*(?=\\n)";
	final String EDU_MATCHER = "факультет|(.*ГУ)|(.*ГИ)";
	final String EDU_YEAR_MATCHER = "[0-9]{4}";
	final String ADD_SKILLS_MATCHER = "умею|бариста|сомелье|способен|курсы|обучен";
	final String LANGUAGES_MATCHER = "язык|английский";
	
	
	public List<WaiterCompetencyModel> parseWaiters(Set<JSONPositionModel> jsonPositionModels) {
		return jsonPositionModels
		.stream()
		.map(e -> {
			ArrayList<String> sentenses = new ArrayList<>();
			WaiterCompetencyModel waiterCompetencyModel = new WaiterCompetencyModel();
			List<CVPosition> positions = e.getPositions();
			String s = e.getRawData().stream().collect(Collectors.joining(", "));
			Pattern pattern = Pattern.compile(SENTENSES_MATCHER);		
			Matcher matcher = pattern.matcher(s);
		    while (matcher.find()) {
		        sentenses.add(s.substring(matcher.start(), matcher.end()));
		    }
			waiterCompetencyModel.setQ1(
					positions.size() == 0 
					? "Официант" 
					: positions.get(positions.size() - 1).getPosition());
			
			String eduInfo = "";
			Optional<String> eduInfoOpt = sentenses.stream().filter(el -> {
				Pattern eduPattern = Pattern.compile(EDU_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher eduMatcher = eduPattern.matcher(el);
				while (eduMatcher.find()) {
					return true;
			    }
				return false;
			}).findFirst();
			eduInfo = eduInfoOpt.isPresent() ? eduInfoOpt.get() : eduInfo;
		    
			waiterCompetencyModel.setQ2(false);
			waiterCompetencyModel.setQ3(eduInfo);
			Integer eduYear = 0;
			Pattern eduYearPattern = Pattern.compile(EDU_YEAR_MATCHER);		
			Matcher eduYearMatcher = eduYearPattern.matcher(eduInfo);
			eduYearMatcher.matches();
			while (eduYearMatcher.find()) {
				eduYear = Integer.parseInt(eduInfo.substring(eduYearMatcher.start(), eduYearMatcher.end()));
				break;
		    }
			waiterCompetencyModel.setQ4(eduYear);
			waiterCompetencyModel.setQ5(true);
			waiterCompetencyModel.setQ6(positions);
			
			List<String> addSkills = sentenses.stream().filter(el -> {
				Pattern eduPattern = Pattern.compile(ADD_SKILLS_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher eduMatcher = eduPattern.matcher(el);
				while (eduMatcher.find()) {
					return true;
			    }
				return false;
			}).collect(Collectors.toList());
			waiterCompetencyModel.setQ7(addSkills);
			
			List<String> languages = sentenses.stream().filter(el -> {
				Pattern langPattern = Pattern.compile(LANGUAGES_MATCHER, Pattern.CASE_INSENSITIVE);	
				Matcher langMatcher = langPattern.matcher(el);
				while (langMatcher.find()) {
					return true;
			    }
				return false;
			}).collect(Collectors.toList());
			waiterCompetencyModel.setQ8(languages);
			waiterCompetencyModel.setQ9(new ArrayList<String>());
			waiterCompetencyModel.setQ10(new ArrayList<String>());
			return waiterCompetencyModel;
		})
		.collect(Collectors.toList());
		
		
	}

}
