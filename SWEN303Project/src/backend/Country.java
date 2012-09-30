package backend;

public class Country {
	
	private String name;
	private String code;
	private Region region;
	private boolean oecd;
	
	public Country(String name, String code, Region region){
		this.name = name;
		this.code = code;
		this.region = region;
		this.oecd = findOecd(name);
	}
	
	
	
	public String getName() {
		return name;
	}



	public String getCode() {
		return code;
	}



	public Region getRegion() {
		return region;
	}



	public boolean isOecd() {
		return oecd;
	}



	private boolean findOecd(String name){
		switch(name){
		case "Australia":
		case "Austria":
		case "Belgium":
		case "Canada":
		case "Chile":
		case "Czech Republic":
		case "Denmark":
		case "Estonia":
		case "Finland":
		case "France":
		case "Germany":
		case "Greece":
		case "Hungary":
		case "Iceland":
		case "Ireland":
		case "Israel":
		case "Italy":
		case "Japan":
		case "South Korea":
		case "Luxembourg":
		case "Mexico":
		case "Netherlands":
		case "New Zealand":
		case "Norway":
		case "Poland":
		case "Portugal":
		case "Slovakia":
		case "Slovenia":
		case "Spain":
		case "Sweden":
		case "Switzerland":
		case "Turkey":
		case "United Kingdom":
		case "United States":
			return true;
		default: return false;
		}
	}
}
