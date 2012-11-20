/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.soundelement;

import java.util.LinkedList;

/**
 *
 * @author LostMekka
 */
public class Delay implements SoundElement{

	public double length;

	private double currLength, lastInput, nextOutput;
	private LinkedList<Double> buffer, lengths; // omg, am i really doing this???
	
	public Delay(double length) {
		this.length = length;
		currLength = 0;
		buffer = new LinkedList<>();
		lengths = new LinkedList<>();
		lastInput = 0;
		nextOutput = 0;
	}
	
	@Override
	public double getCurrValue(double input) {
		lastInput = input;
		return nextOutput;
	}

	@Override
	public void tick(double elapsedTime) {
		currLength += elapsedTime;
		buffer.addFirst(lastInput);
		lengths.addFirst(elapsedTime);
		while(currLength > length){
			nextOutput = buffer.removeLast();
			currLength -= buffer.removeLast();
		}
	}
	
}
