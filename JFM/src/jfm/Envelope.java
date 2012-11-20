/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm;

/**
 *
 * @author LostMekka
 */
public class Envelope {
	
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
	
	public boolean isPlaying(){
		return (state != -1);
	}
	
	public void reset(){
		state = -1;
		totalTime = 0;
		envLevel = 0;
	}
	
	public void startNote(){
		reset();
		state = 0;
	}
	
	public void endNote(){
		if(state != -1) state = 3;
	}
	
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
	
	public double getCurrValue(){
		return envLevel;
	}
	
}
