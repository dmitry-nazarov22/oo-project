package fi.nazarov.personapp.lists;

public class MyArrayList<T> implements MyList<T> {
    private int size;
    private T[] array;
    private static final int START_SIZE = 5;
    /**
     * Default constructor.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        array = (T[]) new Object[START_SIZE];
        size = 0;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e the element to add
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(final T e) {
        if (size == array.length) {
            var tmp = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                tmp[i] = array[i];
            }
            array = tmp;
        }
        array[size] = e;
        size++;
    }

    /**
     * Removes all the elements from this list. The list will be empty after
     * this call returns.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        array = (T[]) new Object[START_SIZE];
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
        return array[index];
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

        var toReturn = array[index];

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        array[size - 1] = null;
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
        for (int i = 0; i < size; i++) {
            if (array[i] != null && array[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Set new value to the specified index.
     *
     * @param index specified index where value should be updated.
     * @param updated value to be updated to the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    @Override
    public void set(final int index, final T updated) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        array[index] = updated;
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

