/**
 *
 */
package local.projects.betting;

/**
 * @author aricciardiello
 *
 */
public class Test {
	public static Integer[] getAllLists(Integer[] elements, int lengthOfList)
	{
	    //initialize our returned list with the number of elements calculated above
		Integer[] allLists = new Integer[(int)Math.pow(elements.length, lengthOfList)];

	    //lists of length 1 are just the original elements
	    if(lengthOfList == 1) return elements;
	    else {
	        //the recursion--get all lists of length 3, length 2, all the way up to 1
	    	Integer[] allSublists = getAllLists(elements, lengthOfList - 1);

	        //append the sublists to each element
	        int arrayIndex = 0;

	        for(int i = 0; i < elements.length; i++){
	            for(int j = 0; j < allSublists.length; j++){
	                //add the newly appended combination to the list
	                allLists[arrayIndex] = elements[i] + allSublists[j];
	                arrayIndex++;
	            }
	        }
	        return allLists;
	    }
	}

	public static void main(String[] args){
//		Integer[] database = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
		Integer[] database = {1,2};
	    for(int i=1; i<=database.length; i++){

	    	Integer[] result = getAllLists(database, i);
	        for(int j=0; j<result.length; j++){
	            System.out.println(result[j] + " ");
	        }
	    }
	}
}
