package backend;

import java.awt.Color;

public enum Region {
	Eastern_Africa, Middle_Africa, Northern_Africa, Southern_Africa, Western_Africa, Latin_America_and_the_Caribbean, Caribbean, Central_America, South_America, Central_Asia, Eastern_Asia, Southern_Asia, Western_Asia, Eastern_Europe, Northern_Europe, Southern_Europe, Western_Europe, Australia_and_New_Zealand, Melanesia, Micronesia, Polynesia;

	public static Region fromString(String in) {
		in = in.replace(' ', '_');
		return valueOf(in);
	}

	public String toString(){
		return super.toString().replace('_', ' ');
	}

	public Color color() {
		switch (this){
		case Australia_and_New_Zealand: return Color.blue;
		case Caribbean: return Color.yellow;
		case Central_America: return Color.pink;
		case Central_Asia: return Color.cyan;
		case Eastern_Africa: return Color.darkGray;
		case Eastern_Asia: return Color.gray;
		case Eastern_Europe: return Color.green;
		case Latin_America_and_the_Caribbean: return Color.lightGray;
		case Melanesia: return Color.magenta;
		case Micronesia: return Color.orange;
		case Middle_Africa: return new Color(0xC90);
		case Northern_Africa:return new Color(0x690);
		case Northern_Europe:return new Color(0x0C9);
		case Polynesia:return new Color(0x60F);
		case  South_America:return new Color(0x603);
		case Southern_Africa:return new Color(0xF60);
		case Southern_Asia:return new Color(0x39f);
		case Southern_Europe:return new Color(0x3c3);
		case Western_Africa:return new Color(0x396);
		case Western_Asia:return new Color(0x900);
		case Western_Europe:return new Color(0x009);
		default: return Color.black;
		}
	}
}