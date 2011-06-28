package uk.ac.shef.importance.framework;

public interface AlphaBetaComputer<X>
{
	public double computeAlpha(X obj);
	public double computeBeta(X obj);
	public double scoreObject(X obj);
}
