import java.util.Comparator;
import java.util.Arrays;

/**
 * Autocomplete term representing a (query, weight) pair.
 * 
 */
public class Term implements Comparable<Term> {
    private final String query;
	private final long weight;

    /**
     * Initialize a term with the given query and weight.
     * This method throws a NullPointerException if query is null,
     * and an IllegalArgumentException if weight is negative.
     */
    public Term(String query, long weight) {
    if (query == null) {
        throw new NullPointerException();
    }
		if (weight < 0) {
            throw new IllegalArgumentException();
        }
		this.query = query;
		this.weight = weight;
    }

    /**
     * Compares the two terms in descending order of weight.
     */
    public static Comparator<Term> byDescendingWeightOrder() {
   return new ComparatorByReverseOrderWeight();
    }

    /**
     * Compares the two terms in ascending lexicographic order of query,
     * but using only the first length characters of query. This method
     * throws an IllegalArgumentException if length is less than or equal
     * to zero.
     */
    public static Comparator<Term> byPrefixOrder(int length) {
       if (length < 0) {
           throw new IllegalArgumentException();
       }
		return new ComparatorByPrefixOrderQuery(length);
    
    }

    /**
     * Compares this term with the other term in ascending lexicographic order
     * of query.
     */
    @Override
    public int compareTo(Term other) {
    return this.query.compareTo(other.query);
    }

    /**
     * Returns a string representation of this term in the following format:
     * query followed by a tab followed by weight
     */
    @Override
    public String toString(){
    return query + "\t" + weight;
    }

    private static class ComparatorByReverseOrderWeight implements Comparator<Term> {
		@Override
		public int compare(Term one, Term two) {
			if (one.weight == two.weight) {
                return 0;
            }
			if (one.weight > two.weight) {
                return -1;
            }
			return 1;
		}
	}
	
	private static class ComparatorByPrefixOrderQuery implements Comparator<Term> {
		private int i;
		
		private ComparatorByPrefixOrderQuery(int i) {
			this.i = i;
		}

		@Override 
		public int compare(Term one, Term two) {
			String prefixA;
			String prefixB;
			
			if (one.query.length() < i) {
                prefixA = one.query;
            }
			else {
                prefixA = one.query.substring(0, i);
            }
			
			if (two.query.length() < i) {
                prefixB = two.query;
            }
			else {
                prefixB = two.query.substring(0, i);
            }

			return prefixA.compareTo(prefixB);
		}
	}
	

}


