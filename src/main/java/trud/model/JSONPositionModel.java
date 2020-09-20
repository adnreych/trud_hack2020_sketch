package trud.model;


import java.util.List;

import lombok.Data;

@Data
public class JSONPositionModel {
	
	Long id;
	String employerId;
	Boolean isWaiter;
	Boolean isStorekeeper;
	Boolean isLoaderDriver;
	List<CVPosition> positions;
	List<String> rawData;

}
