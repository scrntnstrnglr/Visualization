package com.tcd.visualization.cs7ds4.cholera;
import processing.core.*;
import processing.data.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane.*;

import java.io.File; 

public class CholeraOutbreak extends PApplet {
	
	private static File dataFile;
	
	CholeraOutbreak(){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, "Select the csv file");
		boolean chosen=false;
		while(!chosen) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
			    dataFile = fileChooser.getSelectedFile();
			    System.out.println("Selected file: " + dataFile.getAbsolutePath());
				chosen=true;
			}

		}

		frame.setVisible(true);
		
	}
	
	public void settings() {
		size(1024,930);
	}
	
	public void draw() {
		PImage img;
		img = loadImage("C:\\Users\\SIDDHARTHA\\Dropbox\\Trinity Data Science\\Data Visualization\\mapclean.jpg");
		image(img, 10, 10);
		Table table;
		
		table=loadTable(dataFile.toString(),"header");
		for(TableRow row: table.rows()){
		 int count=row.getInt("count"); 
		 float x_screen=row.getFloat("x_screen");
		 float y_screen=row.getFloat("y_screen");
		 if(count>0){
		   for(int i=1;i<=count;i++){
		       fill(255,0,0,150);
		       //stroke(400,0,0);
		       ellipse(x_screen,y_screen,sqrt(count)*5,sqrt(count)*5); 
		   }
		 }else{
		    fill(90,90,255,150); //R,G,B,opacity
		    stroke(90,90,255);
		    rect(x_screen-5,y_screen-5,10,10);
		    
		 }
		}
	}
	
	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CholeraOutbreak());
	}

}
