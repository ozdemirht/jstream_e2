import java.util.HashSet;
import java.util.stream.Stream;

/**
 * The FilterMatchAll class is an implementation of the IFilter interface.
 * It represents a filter that evaluates whether a given set of terms contains all the
 * filter terms defined by this filter. A match is achieved only if all terms are found
 * in the input set of terms.
 */
public class FilterMatchAll implements IFilter {
    /**
     * Represents the unique identifier for a filter instance.
     * This identifier is an integer value used to uniquely distinguish filters
     * within the context of the filter management system.
     * The value of {@code filterIdentifier} is defaulted to {@code Integer.MAX_VALUE}.
     */
    Integer filterIdentifier = Integer.MAX_VALUE;
    /**
     * Represents the terms used by the filter to evaluate matches.
     * This variable is an array of strings, each element corresponding to a term
     * derived from the provided filter string.
     * The terms are tokenized, normalized (lowercased, trimmed), and stored in this array
     * for use during match evaluations.
     * The presence of all terms in the input set indicates a match for the filter.
     */
    String[] filterTerms;

    /**
     * Constructs a FilterMatchAll instance with the specified filter identifier and filter string.
     * The filter identifier uniquely distinguishes the filter, and the filter string defines
     * the terms against which the filter will evaluate matches.
     *
     * @param filterIdentifier an integer representing the unique identifier of the filter
     * @param filterString a string containing the terms used by the filter, separated by spaces
     */
    public FilterMatchAll(int filterIdentifier, String filterString) {
        this.filterIdentifier = filterIdentifier;
        buildFilterTerms(filterString);
    }

    /**
     * Retrieves the unique identifier of this filter.
     * The identifier is an integer value that uniquely distinguishes this filter instance.
     *
     * @return the unique integer identifier of the filter
     */
    @Override
    public int getFilterIdentifier() {
        return filterIdentifier;
    }

    /**
     * Evaluates whether all filter terms defined by this filter are present in the provided set of terms.
     * A match is achieved only if the entire set of filter terms is found in the input.
     *
     * @param line a HashSet containing the set of terms to be evaluated
     * @return true if all filter terms are present in the given set of terms, false otherwise
     */
    @Override
    public boolean doMatch(HashSet<String> line) {
        int i;
        boolean match = true;
        for(i=0;match && i<filterTerms.length;i++)
            match = line.contains(filterTerms[i]);
        return match;
    }

    /**
     * Constructs a space-separated string from the filter terms associated with the filter.
     * If there are no filter terms, an empty string is returned.
     *
     * @return a string containing all filter terms separated by a single space, or an empty string if no terms are present
     */
    @Override
    public String getTerms() {
        String ret = "";
        if (filterTerms.length > 0) {
            ret=filterTerms[0];
            for (int i = 1; i < filterTerms.length; i++)
            ret += " "+filterTerms[i];
        }
        return ret;
    }

    /**
     * Processes the provided filter string to populate the filter terms.
     * The input string is normalized by trimming whitespace, converting to lowercase,
     * and splitting the words based on spaces to create an array of terms.
     *
     * @param filterString the raw filter string that defines the terms for the filter
     */
    private void buildFilterTerms(String filterString){
        // Normalize the filter string
        String normalizeFilterString = filterString.trim().toLowerCase();
        // Tokenize the normalized filter string and store the terms in an array
        filterTerms = normalizeFilterString.split("\s+");
        for(int i=0;i<filterTerms.length;i++) filterTerms[i] = filterTerms[i].trim();
        //Stream.of(filterTerms).forEach(x->System.out.println("|"+x+"|"));
    }
}
