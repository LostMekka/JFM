/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author LostMekka
 */
public class AudioController {

	public static final int SAMPLE_RATE = 44100;
	public static final double SAMPLE_LENGTH = 1d / (double)SAMPLE_RATE;
	
	SourceDataLine line = null;
	Operator op1 = new Operator(100, 1, new Envelope(2, 1, 0.6, 0.999985));
	Operator op2 = new Operator(49.2, 12, new Envelope(2, 1, 0.85, 0.99999));

	public boolean init(){
		AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
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
	
	public void playANoteAndMakeMeAHappyBeing(){
		line.start();
		byte[] buffer = new byte[512];
		long samples = 0;
		boolean stop = false;
		System.out.println("starting note");
		op1.startNote();
		op2.startNote();
		while(op1.isPlaying()){
			for(int i=0; i<512; i+=2){
				op1.tick(SAMPLE_LENGTH);
				op2.tick(SAMPLE_LENGTH);
				short val = (short)(op1.getCurrValue(op2.getCurrValue(0)) * 7000d);
				buffer[i] = (byte) ((val & 0xff00) >> 8);
				buffer[i + 1] = (byte) (val & 0x00ff);
			}
			line.write(buffer, 0, 512);
			if(stop) continue;
			samples += 512;
			if(samples > SAMPLE_RATE * 8){
				stop = true;
				System.out.println("stopping note");
				op1.endNote();
				op2.endNote();
			}
		}
	}
	
}
