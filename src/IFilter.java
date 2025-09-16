import java.util.HashSet;

/**
 * The IFilter interface defines the contract for implementing a filter mechanism.
 * A filter is identified by a unique identifier and can evaluate whether a set of terms
 * matches certain criteria defined by the implementation.
 */
public interface IFilter {
    /**
     * Retrieves the unique identifier of the filter.
     * Each filter is assigned a unique integer identifier
     * used to differentiate it from other filters.
     *
     * @return the unique integer identifier of the filter
     */
    public int getFilterIdentifier();
    //public boolean doMatch(String line);

    /**
     * Evaluates whether the given set of terms matches the criteria defined by the filter implementation.
     *
     * @param line a HashSet containing the tokens of a normalized line to be evaluated
     * @return true if the given set of terms matches the filter criteria, false otherwise
     */
    public boolean doMatch(HashSet<String> line);

    /**
     * Retrieves the terms associated with the filter.
     * The terms represent the normalized tokens or conditions
     * used by the filter to evaluate matches against provided input.
     *
     * @return a string containing the terms of the filter, separated by spaces
     */
    public String getTerms();
}
