/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.soundelement;

/**
 *
 * @author LostMekka
 */
public final class Operator implements CanPlayNotes{
	
	public double frequency, volume;
	public Envelope envelope;
	private double phase;

	public Operator(Envelope envelope) {
		this.envelope = envelope;
		frequency = 0;
		volume = 0;
		reset();
	}
	
	@Override
	public void tick(double elapsedTime){
		phase += elapsedTime * frequency;
		phase %= 1d;
		envelope.tick(elapsedTime);
	}
	
	@Override
	public double getCurrValue(double input){
		return Math.sin(phase * 2f * Math.PI + input) * envelope.getCurrValue(0) * volume;
	}

	@Override
	public boolean isPlaying(){
		return envelope.isPlaying();
	}
	
	@Override
	public void reset(){
		phase = 0;
		envelope.reset();
	}
	
	@Override
	public void noteKeyPressed(double frequency, double volume) {
		this.frequency = frequency;
		this.volume = volume;
		phase = 0;
		envelope.noteKeyPressed(frequency, volume);
	}

	@Override
	public void noteKeyReleased() {
		envelope.noteKeyReleased();
	}
	
}
