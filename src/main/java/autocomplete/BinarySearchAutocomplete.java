package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        elements.addAll(terms);
        Collections.sort(elements, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();

        // BASE CASE 1
        // Check if the prefix is null or empty, if true return matches as result
        if (prefix == null || prefix.isEmpty()) {
            return result;
        }

        // Assigns the index of the search key if found to indexOfKey,
        // which, if found, its value is >= 0, otherwise its < 0.
        int indexOfKey = Collections.binarySearch(elements, prefix, CharSequence::compare);
        CharSequence term;

        if (indexOfKey < 0) {
            // If the returned indexOfKey is less than 0, match is not found, so recover
            // the insertion point returned by Collections.binarySearch()
            int start = indexOfKey;
            start = -(start + 1);
            indexOfKey = start;
        }

        // If the returned indexOfKey is greater or equal to 0, match is found so add match
        // and keep iterating until the end is found
        while (indexOfKey < elements.size()) {
            term = elements.get(indexOfKey);
            if (Autocomplete.isPrefixOf(prefix, term)) {
                result.add(term);
            }

            // If prefix is lexigraphically greater than term, then break,
            // If its less then add the term and increment indexOfKey.
            indexOfKey++;
        }

    return result;
    }
}
