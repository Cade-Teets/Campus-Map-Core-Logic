package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public UnsortedArrayMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size());
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        PriorityNode<E> priorityNode = new PriorityNode<>(element, priority);
        elements.add(priorityNode);
    }

    @Override
    public boolean contains(E element) {
        return elements.contains(new PriorityNode<>(element, 0));
    }

    @Override
    public double getPriority(E element) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        int index = elements.indexOf(new PriorityNode<>(element, 0));

        PriorityNode<E> elem = elements.get(index);
        return elem.getPriority();
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // Find the min element
        int index = 0;
        for (PriorityNode<E> node : elements) {
            double priority = node.getPriority();

            if (priority < elements.get(index).getPriority()) {
                index = elements.indexOf(node);
            }
        }
        return elements.get(index).getElement();

    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<E> temp = new PriorityNode<>(peekMin(), 0);
        elements.remove(temp);
        return temp.getElement();
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        } else {
            // if the element is not in the priority queue, then make a priority node
            int index = elements.indexOf(new PriorityNode<>(element, 0));
            PriorityNode<E> temp = elements.get(index);
            temp.setPriority(priority);
        }
    }

    @Override
    public int size() { return elements.size(); }
}
