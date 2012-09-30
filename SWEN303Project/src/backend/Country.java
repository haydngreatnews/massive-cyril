package backend;

public class Country {
	String name;
	String code;
	Region region;
	boolean oecd;
	public Country(String name, String code, Region region, boolean oecd){
		this.name = name;
		this.code = code;
		this.region = region;
		this.oecd = oecd;
	}
}
