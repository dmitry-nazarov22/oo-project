package fi.nazarov.personapp;

public class MyLinkedList<T> implements MyList<T> {
    private int size;
    private Node head;

    private class Node {
        private T value;
        private Node next;
        /**
         * Parameterized constructor.
         *
         * @param value T to be stored.
         */
        Node(final T value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Default consturctor.
     */
    public MyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e the element to add
     */
    @Override
    public void add(final T e) {
        Node n1 = new Node(e);
        if (head == null) {
            head = n1;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = n1;
        }
        size++;
    }

    /**
     * Removes all of the elements from this list. The list will be empty after
     * this call returns.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= size())
     */
    @Override
    public T get(final int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.value;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes the element at the specified position in this list.
     * Returns the removed element.
     *
     * @param index the index of the element to remove
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= size())
     */
    @Override
    public T remove(final int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        T toReturn;
        if (index == 0) {
            toReturn = head.value;
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            toReturn = current.next.value;
            current.next = current.next.next;
        }

        size--;
        return toReturn;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present. Returns true if the list contained the specified
     * element.
     *
     * @param o the element to be removed from this list, if present
     * @return true if the list contained the specified element, otherwise false
     */
    @Override
    public boolean remove(final T o) {
        Node current = head;
        Node previous = null;

        while (current != null) {
            if (current.value.equals(o)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }

        return false;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }
}
