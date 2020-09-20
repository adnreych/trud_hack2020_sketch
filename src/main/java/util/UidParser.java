package util;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;

import trud.model.CVPosition;
import trud.model.JSONPositionModel;

public class UidParser {
	
	DateFormat formatter = new SimpleDateFormat("yyyy-MM");
	String datePatternString = "^[0-9]{4}\\-[0-9]{2}$";
	
	
	public Set<JSONPositionModel> CVModelParse(String filePath) {
		Set<JSONPositionModel> jsonPositionModels = new HashSet<JSONPositionModel>();
		try {
			CSVReader reader = new CSVReader(new FileReader(filePath), ';', '"', 1);
			Set<JSONPositionModel> waitersId;
			CSVReader waitersReader = new CSVReader(new FileReader("storekeepers.csv"), ';', '"', 1);
			Set<String> waiterIds = new HashSet<>();
			Set<String> empIds = new HashSet<>();
		    String[] nextLine;
		    
		    Long i = 1L;
			while ((nextLine = waitersReader.readNext()) != null) { 
				waiterIds.add(nextLine[0]);
			}
			
			while ((nextLine = reader.readNext()) != null  && i++ < 500) { //   
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
						if (waiterIds.contains(nextLine[0])) {
							jsonPositionModel.setIsWaiter(false);
							jsonPositionModel.setIsLoaderDriver(false);
							jsonPositionModel.setIsStorekeeper(true);
						} else {
							jsonPositionModel.setIsWaiter(false);
							jsonPositionModel.setIsLoaderDriver(false);
							jsonPositionModel.setIsStorekeeper(false);
						}
						List<CVPosition> cvPositions = new ArrayList<>();
						CVPosition cvPosition = parseDescription(nextLine);
						cvPositions.add(cvPosition);
						List<String> rawData = cvPositions
								.stream()
								.map(e -> e.getRawData())
								.collect(Collectors.toList());
						jsonPositionModel.setRawData(rawData);
						jsonPositionModel.setPositions(cvPositions);
						if(jsonPositionModel.getIsStorekeeper()) {
							jsonPositionModels.add(jsonPositionModel);
							empIds.add(nl);
						}					
					}								    
				  }
				}
		} catch (IOException e) {
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
				cvPosition.getPosition() + "\n" +
				cvPosition.getOrganization() + "\n" +
				cvPosition.getDescription()
				);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return cvPosition;
		
	}

}
