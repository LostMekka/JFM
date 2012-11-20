/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.soundelement;

/**
 *
 * @author LostMekka
 */
public class Envelope implements CanPlayNotes{
	
	public static final double DEFAULT_RELEASE_CUTOFF = 0.05;
	
	public double attack, decay, sustain, release, releaseCutoff = DEFAULT_RELEASE_CUTOFF;
	private int state;
	private double totalTime, envLevel;

	public Envelope(double attack, double decay, double sustain, double release) {
		this.attack = attack;
		this.decay = decay;
		this.sustain = sustain;
		this.release = release;
	}
	
	@Override
	public void tick(double elapsedTime){
		if(state == -1) return;
		totalTime += elapsedTime;
		switch(state){
			case 0:
				if(totalTime < attack){
					envLevel = totalTime / attack;
				} else {
					envLevel = 1;
					state = 1;
					totalTime = 0;
				}
				break;
			case 1:
				envLevel *= decay;
				if(totalTime < decay){
					envLevel = 1d - (1d - sustain) * totalTime / decay;
				} else {
					envLevel = sustain;
					state = 2;
					totalTime = 0;
				}
			case 3:
				envLevel *= release;
				if(envLevel < releaseCutoff){
					envLevel = 0;
					state = -1;
				}
				break;
		}
	}
	
	@Override
	public double getCurrValue(double input){
		return envLevel;
	}
	
	@Override
	public boolean isPlaying(){
		return (state != -1);
	}
	
	@Override
	public void reset(){
		state = -1;
		totalTime = 0;
		envLevel = 0;
	}
	
	@Override
	public void noteKeyPressed(double frequency, double volume){
		reset();
		state = 0;
	}
	
	@Override
	public void noteKeyReleased(){
		if(state != -1) state = 3;
	}
	
}
