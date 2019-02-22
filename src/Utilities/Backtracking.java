package Utilities;

/**
 * Classe astratta che veste i panni di ABSTRACTCLASS
 * la quale definisce le operazioni primitive, che sono
 * i metodi che verranno implementati poi dalle CLASSICONCRETE.
 * 
 * 
 * {@link SudokuBack}. 
 * 
 * 
 *
 */
public abstract class Backtracking {

	private int minVal;
	private int maxVal;

	public void setMinMaxVal(int min_val, int max_val){
		this.minVal = min_val;
		this.maxVal = max_val;
	}

	/**
	 * Metodo Modello
	 * @return
	 */
	public boolean solve(){
		int x = minVal;
		while( x<=maxVal){
			if(canAdd(x)){
				add(x);
				if(isComplete())  return true;
				else if(solve())  return true;
				remove();
				x++;
				System.err.println("back x: "+x);
 			}
 			else 
 			{
 				System.err.println("else back x: "+x);
 				x++;
 			}
 		}
 		return false;
 	}

	void next(int x){  x++; }

	/*
	 * Primitive
	 */
	abstract boolean canAdd(int x);
	abstract void add(int x);
	abstract boolean isComplete();
	abstract void remove();
	 
}
