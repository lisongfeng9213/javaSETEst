import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Integer> sets = new HashSet();
		for(int i = 0;i<100;i++){
			sets.add(i);
		}
		
		Iterator<Integer> iterable = sets.iterator();
		while(iterable.hasNext()){
			System.out.println("!!"+iterable.next());
		}
		
		iterable = sets.iterator();
		while(iterable.hasNext()){
			System.out.println(iterable.next());
		}

	}

}
