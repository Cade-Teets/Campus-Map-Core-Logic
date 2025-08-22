package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // So here I want to add all the values one by one, and with the TST there is a put method, which
        // contains the logic that can compare the values lexigraphically
        // TO MAKE A TST, EACH NODE CONTAINS ONE CHAR, AND A LEFT/RIGHT/MIDDLE CHILDREN NODES

        // Need a helper method for insertion
        // if it's not empty then start adding

        for (CharSequence word : terms) {
            overallRoot = putTerms(overallRoot, word, 0);
        }
    }
    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();

        // Base Case: if the prefix is empty/null, just return the empty list
        if (prefix == null || prefix.isEmpty())  {
            return result;
        }
        // So we have a prefix, and we want to use the helper method to traverse to the point
        // that all of the characters in prefix are matched with a words prefix
        Node start = getTerms(overallRoot, prefix,0);

        if (start == null) {
            return result;
        }
        // If the starting node is a word containing the prefix, then add it to the results
        if (start.isTerm) {
            result.add(prefix);
        }
        collectTerms(start.mid, prefix.toString(), result);

        return result;
    }

    private void collectTerms (Node x, String term, List<CharSequence> result) {
        if (x == null) {
            return;
        }
        collectTerms(x.left, term, result);

        // If the isTerm flag is true, then it is a word and add it to the results list
        if (x.isTerm) {
            //result.add(term += x.data);
            result.add(term + x.data);
        }

        // Call the collect terms with the middle node and the prefix now includes the last char
        //collectTerms(x.mid, term += x.data, result);
        collectTerms(x.mid, term + x.data, result) ;
        collectTerms(x.right, term, result);

    }

    private Node getTerms(Node x, CharSequence term, int d) {
        char letter = term.charAt(d);
        if (x == null) {
            return null;
        }
        if (letter < x.data) {
            return getTerms(x.left, term, d);
        } else if (letter > x.data) {
            return getTerms(x.right, term, d);
        } else if (d < term.length() - 1) {
            return getTerms(x.mid, term, d + 1);
        } else {
            return x;
        }
    }

    // This helper method is for adding char's to the TST
    private Node putTerms(Node x, CharSequence term, int d) {
        // Initialize variables for the char at a given index
        char letter = term.charAt(d);

        // Base Case for when the terms are empty
        if (term.isEmpty()) {
            throw new IllegalArgumentException("Calling putTerms with no terms to add");
        }
        if (x == null) {
            x = new Node(letter);
        }

        // Logic to compare the TST node char to the letter being added
        // This allows for proper insertion into TST
        // Root node children get compared to the term that we want to add
        // If the value we want to insert is greater than the left node, check right
        // if the value is less than the left node, check the left node
        if (letter > x.data) {
            x.right = putTerms(x.right, term, d);
        } else if (letter < x.data) {
            x.left = putTerms(x.left, term, d);
        // If letter is not > / < than the left/right node values, then check if
        // still within the bounds of the string, then return the next node level to
        // keep searching for a match
        } else if (d < term.toString().length() - 1) {
            x.mid = putTerms(x.mid, term, d+1);
        }
        // When at the end of a word, change the isTerm boolean
        else {
            x.isTerm = true;
        }
        return x;
    }
    /*
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
