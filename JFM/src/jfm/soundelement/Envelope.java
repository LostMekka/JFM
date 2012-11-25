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
	
	public double attack, decay, sustain, release, attackStart;
	private int state;
	private double totalTime, envLevel;

	public Envelope(double attack, double decay, double sustain, double release) {
		this.attack = attack;
		this.decay = decay;
		this.sustain = sustain;
		this.release = release;
		reset();
	}
	
	@Override
	public void tick(double elapsedTime){
		if(state == -1) return;
		totalTime += elapsedTime;
		switch(state){
			case 0:
				if(totalTime < attack){
					envLevel = attackStart + (1d - attackStart) * totalTime / attack;
				} else {
					envLevel = 1;
					state = 1;
					totalTime = 0;
				}
				break;
			case 1:
				if(totalTime < decay){
					envLevel = 1d - (1d - sustain) * totalTime / decay;
				} else {
					envLevel = sustain;
					state = 2;
					totalTime = 0;
				}
				break;
			case 3:
				if(totalTime < release){
					envLevel = sustain * (1d - totalTime / release);
				} else {
					envLevel = 0;
					state = -1;
					totalTime = 0;
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
		attackStart = envLevel;
		reset();
		envLevel = attackStart;
		state = 0;
	}
	
	@Override
	public void noteKeyReleased(){
		if(state != -1){
			state = 3;
			totalTime = 0;
		}
	}
	
}
