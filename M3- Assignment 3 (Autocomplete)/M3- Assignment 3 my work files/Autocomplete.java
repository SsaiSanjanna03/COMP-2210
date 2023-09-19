import java.util.Arrays;

/**
 * Autocomplete.
 */
public class Autocomplete {

	private Term[] terms;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
	public Autocomplete(Term[] terms) {
	if (terms == null) {
        throw new NullPointerException();
    }
    	this.terms = new Term[terms.length];
    	for (int i = 0; i < terms.length; i++) { 
    		this.terms[i] = terms[i];
        }
    	Arrays.sort(this.terms);
    }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
	public Term[] allMatches(String prefix) {
	if (prefix == null) {
        throw new NullPointerException();
    }
    	
    	int first = BinarySearch.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    	if (first == -1) {
            return new Term[0];
        }
    	int last  = BinarySearch.lastIndexOf (terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    	Term[] matched = new Term[1 + last - first];
    	
    	for (int i = 0; i < matched.length; i++) {
    		matched[i] = terms[first++];
        }

    	Arrays.sort(matched, Term.byDescendingWeightOrder());
    	
		return matched;
    }

}

