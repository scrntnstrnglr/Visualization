package com.tcd.visualization.cs7ds4.nightingale.utils;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PreProcessor {
	private final File pythonFile;
	public PreProcessor(File pythonFile) {
		this.pythonFile=pythonFile;
	}
	
	public void process() throws FileNotFoundException {
		PythonInterpreter python = new PythonInterpreter();
		python.execfile(new FileInputStream(pythonFile));
		python.close();
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		PreProcessor preProc = new PreProcessor(new File("python\\preprocess.py"));
		preProc.process();
	}

}
