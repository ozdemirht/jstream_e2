import java.util.*;

/**
 * The StreamFilter class is responsible for processing lines of input and handling them in two ways:
 * defining filters and processing log lines against those filters. Filters created by the
 * class are stored internally and matched against log lines based on their tokens.
 */
public class StreamFilter {
    /**
     * A map associating unique integer identifiers with their corresponding filter instances.
     * The keys represent the identifiers of the filters, while the values are instances of the {@link IFilter} interface
     * that defines the behavior and criteria for evaluating matches against a set of terms.
     *
     * This collection acts as the central repository for all filters used by the {@code StreamFilter} class,
     * enabling efficient storage and retrieval of filters for processing input lines and evaluating matches.
     */
    Map<Integer, IFilter> filters;

    /**
     * Constructs a new instance of the StreamFilter class.
     * The class is responsible for managing and processing filters and log lines.
     * It initializes the internal data structure to store filters, preparing the instance
     * for further operations such as adding filters and evaluating log lines.
     */
    public StreamFilter(){
        filters=new HashMap<>();
    }

    /**
     * Processes a given input line by delegating to specific processing methods based on the line's prefix.
     * If the line starts with "QF:", it is processed as a filter definition.
     * If the line starts with "LOL:", it is processed as a log line and evaluated against existing filters.
     *
     * @param line the input line to be processed; it should start with either "QF:" or "LOL:".
     * @return the processed output string for the given line:
     *         - For lines with prefix "QF:", a description of the created filter.
     *         - For lines with prefix "LOL:", a formatted string if it matches any filters, or null if no match is found.
     */
    public String filter(String line){
        String ret = line;
        //System.out.println(line);
        if(line.startsWith("QF:"))
            ret = processFilter(line.substring("QF:".length()));
        else if(line.startsWith("LOL:"))
            ret = processLogOfLine(line.substring("LOL:".length()));
        return ret;
    }

    /**
     * Processes a filter line with the prefix "QF:", normalizes it, creates a new filter, and stores it in the filter set.
     * Returns a formatted string containing the normalized line and the associated filter identifier.
     *
     * @param line the input line with the prefix "QF:" to be normalized and used to create a new filter
     * @return a formatted string indicating the normalized line and the identifier of the created filter
     */
    private String processFilter(String line){
        String ret ;
        // Extract the filter string from the line
        String normalizedLine = line.trim();
        //synchronized - begin
        int filterIdentifier = filters.size()+1;
        IFilter aFilter = new FilterMatchAll(filterIdentifier,normalizedLine);
        filters.put(filterIdentifier,aFilter);
        //synchronized - end
        ret = "A:"+aFilter.getTerms()+"; FID="+filterIdentifier;
        return ret;
    }

    /**
     * Processes a log line with the prefix "LOL:", normalizes it, checks against registered filters,
     * and returns a formatted string if the line matches any filters.
     *
     * @param line the input line with the prefix "LOL:", which will be normalized and checked against filters
     * @return a formatted string containing the normalized line and matching filter identifiers,
     *         or null if no filters matched
     */
    private String processLogOfLine(String line){
        // Extract the normalized line from the log line
        String normalizedLine = line.trim();
        // Remove punctuations and tokenize the normalized line
        String normalizedLine2 = normalizedLine.replaceAll("\\p{Punct}","").trim();
        HashSet<String> lineSet = new HashSet<>(Arrays.asList(normalizedLine2.toLowerCase().split("\s+")));
        // Check if the line matches any of the filters
        List<Integer> filterIdentifiers = checkFilterMatch(lineSet);
        // Prepare response for matching filters
        return (!filterIdentifiers.isEmpty() ? prepareMatchLog(normalizedLine,filterIdentifiers) : null);
    }

    /**
     * Checks which filters in the current filter set match the provided line.
     *
     * @param lineSet a HashSet containing the tokens of a normalized line to be matched against the filters
     * @return a list of integers representing the identifiers of filters that match the given line
     */
    private List<Integer> checkFilterMatch(HashSet<String> lineSet){
        List<Integer> filterIdentifiers = new ArrayList<>();
        filters.values()
                .parallelStream()
                .mapToInt(x -> { if(x.doMatch(lineSet)) return x.getFilterIdentifier(); return 0;})
                .filter(x->x!=0)
                .forEach(filterIdentifiers::add);
        Collections.sort(filterIdentifiers);
        return filterIdentifiers;
    }
    /**
     * Prepares a response string for matching filters based on a normalized line.
     *
     * @param normalizedLine the normalized line that was matched
     * @param filterIdentifiers a list of filter identifiers that matched the provided line
     * @return a formatted string containing the normalized line and the matching filter identifiers,
     *         or null if no filters matched
     */
    private String prepareMatchLog(String normalizedLine,List<Integer> filterIdentifiers){
        String ret = null;
        if(!filterIdentifiers.isEmpty()){
            ret = "M:"+normalizedLine+"; FID="+filterIdentifiers.getFirst();
            for(int i=1;i<filterIdentifiers.size();i++)
                ret += ", "+filterIdentifiers.get(i);
        }
        return ret;
    }
}
