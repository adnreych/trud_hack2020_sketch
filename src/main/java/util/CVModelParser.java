package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVReader;

import trud.model.CVPosition;
import trud.model.JSONPositionModel;

public class CVModelParser {
	
	DateFormat formatter = new SimpleDateFormat("yyyy-MM");
	String datePatternString = "^[0-9]{4}\\-[0-9]{2}$";
	
	public Set<JSONPositionModel> CVModelParse(String filePath) {
		Set<JSONPositionModel> jsonPositionModels = new HashSet<JSONPositionModel>();
		try {
			CSVReader reader = new CSVReader(new FileReader(filePath), ';', '"', 1);
			Set<String> empIds = new HashSet<>();
		    String[] nextLine;
		    
		    Long i = 1L;
			while ((nextLine = reader.readNext()) != null) { //  && i++ < 40
				JSONPositionModel jsonPositionModel = new JSONPositionModel();
				  if (nextLine != null) {
					final String nl = nextLine[0];
					if (empIds.contains(nextLine[0])) {
						JSONPositionModel findRes = jsonPositionModels
						.stream()
						.filter(e -> e.getEmployerId()
						.equals(nl))
						.findFirst()
						.get();
						List<CVPosition> findResPositions = findRes.getPositions();
						findResPositions.add(parseDescription(nextLine));
						findRes.setPositions(findResPositions);
						jsonPositionModels.add(findRes);
					} else {
						jsonPositionModel.setId(i);
						jsonPositionModel.setEmployerId(nextLine[0]);
						List<CVPosition> cvPositions = new ArrayList<>();
						CVPosition cvPosition = parseDescription(nextLine);
						cvPositions.add(cvPosition);
						jsonPositionModel.setPositions(cvPositions);
						jsonPositionModels.add(jsonPositionModel);
						empIds.add(nl);
					}					
					//System.out.println("line" + i);
					//System.out.println("extLine length" + nextLine.length);
					//System.out.println(Arrays.toString(nextLine));			    
				  }
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonPositionModels;
	}
	
	public CVPosition parseDescription(String[] line) {
		CVPosition cvPosition = new CVPosition();
		try {	
		if (line.length < 5) {
			return new CVPosition();
		}
		Long startDate = (!line[4].equals(""))
				? formatter.parse(line[4]).getTime() : 0L;
		
		Long endDate = (line.length > 5 && !line[5].equals(""))
				? formatter.parse(line[5]).getTime() : 0L;
		cvPosition.setPosition(line[1]);
		cvPosition.setOrganization(line[2]);
		cvPosition.setDescription(line[3]);
		cvPosition.setStart(startDate);
		cvPosition.setEnd(endDate);
		cvPosition.setRawData(
				Arrays.asList(
						cvPosition.getPosition(), 
						cvPosition.getOrganization(), 
						cvPosition.getDescription())
				);
		System.out.println("cvPosition " + cvPosition.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return cvPosition;
		
	}

}
