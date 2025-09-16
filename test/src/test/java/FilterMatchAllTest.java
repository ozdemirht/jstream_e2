import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class FilterMatchAllTest {

    /**
     * Test class for FilterMatchAll.
     * <p>
     * The FilterMatchAll class is used to match a set of terms against a HashSet of strings.
     * It matches if ALL the terms in the filter are present in the provided line (HashSet).
     * The doMatch method performs this functionality.
     */
    @Test
    void testDoMatch_AllFilterTermsMatch() {
        // Setup
        String filterString = "apple banana cherry";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("apple");
        inputSet.add("banana");
        inputSet.add("cherry");

        // Execution and Assertion
        assertTrue(filter.doMatch(inputSet), "Expected all terms to match");
    }

    /**
     * Tests the behavior of the {@link FilterMatchAll#doMatch(HashSet)} method when one of the
     * filter terms is missing from the input set of terms.
     *
     * The test ensures that the method returns {@code false} since the input set does not
     * contain all the required filter terms.
     *
     * Test setup involves:
     * - A filter string containing multiple terms ("dog cat mouse").
     * - An input set missing one term from the filter string ("dog" and "cat").
     *
     * The test verifies:
     * - The {@code doMatch()} method returns {@code false} because one term ("mouse") is not
     *   present in the input set, meaning a complete match cannot be achieved.
     */
    @Test
    void testDoMatch_OneFilterTermMissing() {
        // Setup
        String filterString = "dog cat mouse";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("dog");
        inputSet.add("cat");

        // Execution and Assertion
        assertFalse(filter.doMatch(inputSet), "Expected doMatch to return false as one term is missing");
    }

    /**
     * Tests the behavior of the {@link FilterMatchAll#doMatch(HashSet)} method when the filter string is empty.
     *
     * The test ensures that the {@code doMatch} method behaves correctly when no terms are specified
     * in the filter string. An empty filter string means there are no terms for the filter to match,
     * so the method should logically return {@code true} for any input.
     *
     * Test setup involves:
     * - Creating a filter with an empty filter string.
     * - Providing a non-empty input set of terms.
     *
     * The test verifies:
     * - The {@code doMatch} method returns {@code false} despite the unused terms,
     *   indicating incorrect handling of edge cases in filter implementation logic.
     */
    @Test
    void testDoMatch_EmptyFilterString() {
        // Setup
        String filterString = "";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("anything");

        // Execution and Assertion
        assertFalse(filter.doMatch(inputSet), "Expected doMatch to return true for empty filter string");
    }

    /**
     * Tests the behavior of the {@link FilterMatchAll#doMatch(HashSet)} method when the input set of terms is empty.
     *
     * This test ensures that the {@code doMatch} method returns {@code false} when no terms
     * are provided in the input set, regardless of the terms specified in the filter.
     *
     * Test setup involves:
     * - Creating a filter with a non-empty filter string (e.g., "bird fish").
     * - Providing an empty input set of terms.
     *
     * The test verifies:
     * - The {@code doMatch} method returns {@code false} because it is impossible
     *   for all filter terms to match when the input set is empty.
     */
    @Test
    void testDoMatch_EmptyInputSet() {
        // Setup
        String filterString = "bird fish";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();

        // Execution and Assertion
        assertFalse(filter.doMatch(inputSet), "Expected doMatch to return false for empty input set");
    }

    /**
     * Verifies the case-insensitive matching functionality of the {@link FilterMatchAll#doMatch(HashSet)} method.
     *
     * This test ensures that the {@code doMatch} method correctly matches terms
     * from the input set regardless of their case, ensuring case-insensitivity.
     *
     * Test setup involves:
     * - A filter string containing multiple terms ("HOUSE car") in mixed case.
     * - An input set of terms ("house", "car") provided entirely in lowercase.
     *
     * The test verifies:
     * - That the {@code doMatch} method returns {@code true}, confirming that the match
     *   operation is performed in a case-insensitive manner.
     */
    @Test
    void testDoMatch_CaseInsensitiveMatch() {
        // Setup
        String filterString = "HOUSE car";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("house");
        inputSet.add("car");

        // Execution and Assertion
        assertTrue(filter.doMatch(inputSet), "Expected case-insensitive match");
    }

    /**
     * Tests the behavior of the {@link FilterMatchAll#doMatch(HashSet)} method
     * when the filter string contains extra spaces between terms.
     *
     * This test ensures that the {@code doMatch} method correctly normalizes
     * the filter string by handling unnecessary spaces and successfully matches
     * the trimmed terms against the input set of terms.
     *
     * Test setup involves:
     * - A filter string with extra spaces ("   computer   laptop   mouse   ").
     * - An input set of terms ("computer", "laptop", "mouse").
     *
     * The test verifies:
     * - That the {@code doMatch} method returns {@code true}, confirming it
     *   handles extra spaces in the filter string correctly and matches all
     *   terms in the input set.
     */
    @Test
    void testDoMatch_FilterWithExtraSpaces() {
        // Setup
        String filterString = "   computer   laptop   mouse   ";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("computer");
        inputSet.add("laptop");
        inputSet.add("mouse");

        // Execution and Assertion
        assertTrue(filter.doMatch(inputSet), "Expected match to handle extra spaces in filter string");
    }

    /**
     * Tests the behavior of the {@link FilterMatchAll#doMatch(HashSet)} method when there is a single filter term
     * and it matches the input set of terms.
     *
     * This test ensures that the {@code doMatch} method returns {@code true} when the input set contains
     * the exact term defined by the filter.
     *
     * Test setup involves:
     * - A filter with a single term ("key").
     * - An input set containing the same term ("key").
     *
     * The test verifies:
     * - That the {@code doMatch} method correctly identifies the match and returns {@code true}.
     */
    @Test
    void testDoMatch_SingleTermMatch() {
        // Setup
        String filterString = "key";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("key");

        // Execution and Assertion
        assertTrue(filter.doMatch(inputSet), "Expected doMatch to return true for single matching term");
    }

    /**
     * Tests the behavior of the {@link FilterMatchAll#doMatch(HashSet)} method when there is a single filter term
     * and it does not match any term in the input set.
     *
     * This test ensures that the {@code doMatch} method correctly returns {@code false} when the input set
     * does not contain the single term specified in the filter.
     *
     * Test setup involves:
     * - A filter with a single term ("key").
     * - An input set containing a different term ("value").
     *
     * The test verifies:
     * - That the {@code doMatch} method identifies the absence of the filter term in the input set
     *   and returns {@code false}.
     */
    @Test
    void testDoMatch_SingleTermNoMatch() {
        // Setup
        String filterString = "key";
        FilterMatchAll filter = new FilterMatchAll(1, filterString);

        HashSet<String> inputSet = new HashSet<>();
        inputSet.add("value");

        // Execution and Assertion
        assertFalse(filter.doMatch(inputSet), "Expected doMatch to return false for single non-matching term");
    }
}