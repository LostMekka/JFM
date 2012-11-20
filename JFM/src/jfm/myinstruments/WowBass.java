/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.myinstruments;

import jfm.soundelement.Envelope;
import jfm.soundelement.Instrument;
import jfm.soundelement.Operator;

/**
 *
 * @author LostMekka
 */
public class WowBass extends Instrument{

	public WowBass() {
		super(2, 0);
		setOp(0, new Envelope(2, 1, 0.6, 0.999985));
		setOp(1, new Envelope(2.5, 1.4, 0.7, 0.999992));
	}

	@Override
	public double getCurrValue(double input) {
		return getOpVal(0, getOpVal(1, 0));
	}

	@Override
	protected double getFinalFrequency(int opNr, double originalFrequency) {
		if(opNr == 1){
			return originalFrequency * 0.492;
		}
		return originalFrequency;
	}

	@Override
	protected double getFinalVolume(int opNr, double originalVolume) {
		if(opNr == 1){
			return originalVolume * 14;
		}
		return originalVolume;
	}
	
}
