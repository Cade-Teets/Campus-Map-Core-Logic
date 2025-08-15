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
    //private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        //elementsToIndex = new HashMap<>();
        elements.add(null);
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public OptimizedHeapMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>();
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        PriorityNode<E> temp = new PriorityNode<>(element, priority);
        elements.add(temp);
    }

    @Override
    public boolean contains(E element) {
        return elements.contains(new PriorityNode<>(element, 0));
    }

    @Override
    public double getPriority(E element) {
        int index = elements.indexOf(new PriorityNode<>(element, 0));
        PriorityNode<E> elem = elements.get(index);
        return elem.getPriority();
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // First index is 1, as 0 reserved for null
        int index = 1;
        double min_priority = elements.get(1).getPriority();
        for (int i = 2; i < elements.size(); i++) {
            if (elements.get(i).getPriority() < min_priority) {
                min_priority = elements.get(i).getPriority();
                index = i;
            }
        }
        return elements.get(index).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // First index is 1
        int index = 1;
        double min_priority = elements.get(1).getPriority();
        for (int i = 2; i < elements.size(); i++) {
            if (elements.get(i).getPriority() < min_priority) {
                min_priority = elements.get(i).getPriority();
                index = i;
            }
        }
        return elements.remove(index).getElement();
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // if the element is not in the priority queue, then make a priority node
        int index = elements.indexOf(new PriorityNode<>(element, 0));
        PriorityNode<E> temp = elements.get(index);
        temp.setPriority(priority);
    }

    @Override
    public int size() {
        return elements.size() - 1;
    }
}
