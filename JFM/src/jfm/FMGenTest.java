/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author LostMekka
 */
public class FMGenTest {

	SourceDataLine line = null;
	Operator op1 = new Operator(0.0008, 0.999, 0.4, 0.999991, 1, 1);
	Operator op2 = new Operator(0.00002, 0.999, 0.4, 0.99998, 7, 4);

	public boolean init(){
		AudioFormat format = new AudioFormat(44100f, 16, 1, true, true);
		line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) {
			// Handle the error.
			System.err.println("line not supported");
			return false;
		}
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format, 1024 * 2);
		} catch (LineUnavailableException ex) {
			// Handle the error.
			System.err.println("line unavailable");
			return false;
		}
		if(line == null){
			System.err.println("line is null");
			return false;
		}
		return true;
	}
	
	public void loop(){
		line.start();
		byte[] buffer = new byte[512];
		double fr = 0.0006;
		while(!op1.isDone()){
			for(int i=0; i<512; i+=2){
				op1.tick(fr);
				op2.tick(fr);
				short val = (short)(op1.get(op2.get(0)) * 7000d);
				buffer[i] = (byte) ((val & 0xff00) >> 8);
				buffer[i + 1] = (byte) (val & 0x00ff);
			}
			line.write(buffer, 0, 512);
		}
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		FMGenTest test = new FMGenTest();
		test.init();
		test.loop();
	}
}
