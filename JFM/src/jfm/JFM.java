/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm;

/**
 *
 * @author LostMekka
 */
public class JFM {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		AudioController ctrl = new AudioController();
		ctrl.init();
		ctrl.playANoteAndMakeMeAHappyBeing();
	}
}
