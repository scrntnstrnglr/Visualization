package com.tcd.visualization.cs7ds4.gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.tcd.visualization.cs7ds4.minards.Minards;
import com.tcd.visualization.cs7ds4.nightingale.CoxComb;

import processing.core.PApplet;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;

public class Launcher {

	private JFrame frame;
	private static JButton btnLaunch;
	private static JComboBox comboBox;
    private static JLabel imageLabel;
    private Label labelTitle;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher window = new Launcher();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Launcher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 503, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnLaunch = new JButton("Launch");
		btnLaunch.setBounds(178, 183, 115, 29);
		frame.getContentPane().add(btnLaunch);
		
		comboBox = new JComboBox();
		comboBox.setBounds(154, 113, 169, 26);
		frame.getContentPane().add(comboBox);
		
		comboBox.addItem("Nightangle's CoxComb");
		comboBox.addItem("Minard's Map");
		
		labelTitle = new Label("Launcher");
		labelTitle.setBounds(138, 10, 198, 97);
		frame.getContentPane().add(labelTitle);
		
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch(comboBox.getSelectedItem().toString()) {
				case "Nightangle's CoxComb" : createCoxComb();
					break;
				case "Minard's Map":  createMinardsMap();
					break;
				}
			}
		});

	}
	
	private void createCoxComb() {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new CoxComb("Apr", "1854", 12));
	}
	
	private void createMinardsMap() {
		String[] a = { "MAIN" };
		try {
			PApplet.runSketch(a, new Minards());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
