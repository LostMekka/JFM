/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.soundelement;

/**
 *
 * @author LostMekka
 */
public interface CanPlayNotes extends SoundElement{
	
	public boolean isPlaying();
	public void reset();
	public void noteKeyPressed(double frequency, double volume);
	public void noteKeyReleased();
	
}
