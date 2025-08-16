package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /*
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
        elements.add(null);
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
//    public OptimizedHeapMinPQ(Map<E, Double> elementsAndPriorities) {
//        elements = new ArrayList<>();
//        elementsToIndex = new HashMap<>();
//        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
//            // want to get the entry's key/value pair, and update the hashmap so it stores
//            // the index of a given element
//            PriorityNode<E> temp = new PriorityNode<>(entry.getKey(), entry.getValue());
//            add(entry.getKey(), entry.getValue());
//        }
//    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        PriorityNode<E> temp = new PriorityNode<>(element, priority);
        elements.add(temp);
        elementsToIndex.put(element, size());
        //Restore the min heap invariant:
        swim(size());
    }

    @Override
    public boolean contains(E element) {
        return elementsToIndex.containsKey(element);
    }

    @Override
    public double getPriority(E element) {
        return elements.get(elementsToIndex.get(element)).getPriority();
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // First index is 1, as 0 reserved for null
        return elements.get(1).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // First index is 1

        E element = peekMin();
        exchangeNode(1, size());

        elements.remove(size());
        elementsToIndex.remove(element);

        sink(1);
        return element;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        int index = elementsToIndex.get(element);
        elements.get(index).setPriority(priority);

        sink(index);
        swim(index);
    }

    @Override
    public int size() {
        return elements.size() - 1;
    }
    private void swim(int index) {
        // Index goes from n -> size()
        // Swim until its the min priority element
        while (inBounds(index / 2) && elements.get(index).getPriority() < elements.get(index/2).getPriority()) {
            exchangeNode(index, index/2);
            index = index / 2;
        }
    }
    private void sink(int index) {
        // Index goes from n -> size()

        int min_child = isMin(index*2, index*2 + 1);

        while (inBounds(min_child) && elements.get(index).getPriority() > elements.get(min_child).getPriority()) {
            exchangeNode(index, min_child);
            index = min_child;
            min_child = isMin(index*2, index*2 + 1);
        }
    }
    private void exchangeNode(int i, int j) {
        // store element (PriorityNode) in temp
        // element at i assigned to be element at j
        // element at j assigned be temp
        PriorityNode<E> temp1 = elements.get(i);
        PriorityNode<E> temp2 = elements.get(j);

        elements.set(i, temp2);
        elements.set(j, temp1);

        elementsToIndex.put(temp1.getElement(), j);
        elementsToIndex.put(temp2.getElement(), i);
    }
    private boolean inBounds(int n) {
        return n >= 1 && n <= size();
    }
    private int isMin(int i, int j) {
        if (inBounds(i) && inBounds(j)) {
            if (elements.get(i).getPriority() < elements.get(j).getPriority()) {
                return i;
            } else {
                return j;
            }
        } else if (inBounds(i)) {
            return i;
        }
        return 0;
    }
}


