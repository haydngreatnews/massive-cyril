package ui;

import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JLabel;

import backend.Region;

import canvas.ScatterCanvas;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindow main = new MainWindow("StatYou");
		System.out.println(Region.Australia_and_New_Zealand);
		System.out.println(Region.fromString(Region.Central_America.toString()));
	}

}
