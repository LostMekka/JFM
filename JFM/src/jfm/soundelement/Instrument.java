/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jfm.soundelement;

/**
 *
 * @author LostMekka
 */
public abstract class Instrument implements CanPlayNotes{
	
	private Operator[] ops;
	private Delay[] dels;

	public Instrument(int opCount, int delayCount) {
		ops = new Operator[opCount];
		dels = new Delay[delayCount];
	}
	
	protected double getOpVal(int opNr, double input){
		return ops[opNr].getCurrValue(input);
	}

	protected double getDelayVal(int delNr, double input){
		return dels[delNr].getCurrValue(input);
	}

	protected Operator getOp(int opNr) {
		return ops[opNr];
	}

	protected void setOp(int opNr, Operator op) {
		ops[opNr] = op;
	}

	// oh the convenience!!
	protected void setOp(int opNr, Envelope env) {
		ops[opNr] = new Operator(env);
	}

	protected Delay getDel(int delNr) {
		return dels[delNr];
	}

	protected void setDel(int delNr, Delay del) {
		dels[delNr] = del;
	}
	
	protected double getFinalFrequency(int opNr, double originalFrequency){
		return originalFrequency;
	}
	
	protected double getFinalVolume(int opNr, double originalVolume){
		return originalVolume;
	}
	
	@Override
	public void tick(double time){
		for(Operator op:ops) op.tick(time);
		for(Operator op:ops) op.tick(time);
	}

	@Override
	public boolean isPlaying() {
		for(Operator op:ops) if(op.isPlaying()) return true;
		return false;
	}

	@Override
	public void reset() {
		for(Operator op:ops) op.reset();
	}

	@Override
	public void noteKeyPressed(double frequency, double volume) {
		for(int opNr=0; opNr<ops.length; opNr++){
			ops[opNr].noteKeyPressed(getFinalFrequency(opNr, frequency), getFinalVolume(opNr, volume));
		}
	}

	@Override
	public void noteKeyReleased() {
		for(Operator op:ops) op.noteKeyReleased();
	}
	
}
