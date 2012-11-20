/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.soundelement;

/**
 *
 * @author LostMekka
 */
public interface SoundElement{
	
	public abstract void tick(double elapsedTime);
	public abstract double getCurrValue(double input);
	
}
