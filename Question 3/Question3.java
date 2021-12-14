import java.util.ArrayList;
import java.util.Arrays;

public class Question3 
{
	/**
	 * 
	 * @param <E>
	 * @param list
	 * @param key the value you are looking for within the list.
	 * @return the first index value if the key is found in the list, return -1 otherwise.
	 */
	public static <E extends Comparable<E>> int linearSearch(E[] list, E key) 
	{
		ArrayList<E> tempList = new ArrayList<E>(Arrays.asList(list));
		//INSERT YOUR IMPLEMENTATION HERE
		for(E e : tempList)
		{
			if(e == key) return tempList.indexOf(e);
		}
		return -1;
	}

	public static void main(String[] args) 
	{
		Integer[] list = {3, 4, 5, 1, -3, -5, -1};
		System.out.println(linearSearch(list, 2)); //expect -1
		System.out.println(linearSearch(list, 5)); //expect 2
	}
}
