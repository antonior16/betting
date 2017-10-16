/**
 *
 */
package local.projects.betting;

/**
 * @author aricciardiello
 *
 */
public class Generator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	int numeri[] = new int[5];
    	int numero;
    	for(int i =0; i<5;i++){
    		numero = (int)(Math.random()*40);
    		numeri[i] = numero;
    	}

    	for(int n:numeri){
    		System.out.print(n +"\t");
    	}
	}

}
