/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm;

/**
 *
 * @author LostMekka
 */
public final class Operator {
	
	public double frequency, volume;
	public Envelope envelope;
	private double phase;

	public Operator(double frequency, double volume, Envelope envelope) {
		this.frequency = frequency;
		this.volume = volume;
		this.envelope = envelope;
		reset();
	}
	
	public boolean isPlaying(){
		return envelope.isPlaying();
	}
	
	public void reset(){
		phase = 0;
		envelope.reset();
	}
	
	public void startNote(){
		phase = 0;
		envelope.startNote();
	}
	
	public void endNote(){
		envelope.endNote();
	}
	
	public void tick(double elapsedTime){
		phase += elapsedTime * frequency;
		phase %= 1d;
		envelope.tick(elapsedTime);
	}
	
	public double getCurrValue(double mod){
		return Math.sin(phase * 2f * Math.PI + mod) * envelope.getCurrValue() * volume;
	}
	
}
