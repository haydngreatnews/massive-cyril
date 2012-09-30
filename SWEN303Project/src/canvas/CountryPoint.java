package canvas;

import backend.Country;

public class CountryPoint extends StatPoint {
	Country country;
	public CountryPoint(double xVal, double yVal, Country c){
		super(xVal, yVal, c.getCode());
		country = c;
	}
}
