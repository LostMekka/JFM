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
	
	private static final double epsilon = 0.005;
	
	public double attack, decay, sustain, release, volume, tuning;
	
	private double phase, envLevel;
	private int state;

	public Operator(double attack, double decay, double sustain, double release, double volume, double tuning) {
		this.attack = attack;
		this.decay = decay;
		this.sustain = sustain;
		this.release = release;
		this.volume = volume;
		this.tuning = tuning;
		reset();
	}
	
	public boolean isDone(){
		return (state == 3) && (envLevel < epsilon);
	}
	
	public void reset(){
		phase = 0;
		state = 0;
		envLevel = 0;
	}
	
	public void tick(double time){
		phase += time*tuning;
		phase %= 1f;
		switch(state){
			case 0:
				envLevel += attack;
				if(envLevel >= 1){
					envLevel = 1;
					state = 3;
				}
				break;
			case 1:
				envLevel *= decay;
				if(envLevel <= sustain){
					envLevel = sustain;
					state = 2;
				}
			case 2:
				break;
			case 3:
				envLevel *= release;
				if(envLevel < 0.0001){
					envLevel = 0;
					state = 4;
				}
				break;
		}
	}
	
	public double get(double mod){
		return Math.sin(phase * 2f * Math.PI + mod) * envLevel * volume;
	}
}
