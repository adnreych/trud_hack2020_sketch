package trud.model;

import java.util.List;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CVPosition {

	String position;
	String organization;
	String description;	
	List<String> rawData;
	Long start;
	Long end;
	
	@Override
	public String toString() {
		return "CVPosition [position=" + position + ", organization=" + organization + ", description=" + description
				+ " rawData=" + rawData + ", start=" + new Date(start) + ", end=" + new Date(end) + "]";
	}
	
	
}
