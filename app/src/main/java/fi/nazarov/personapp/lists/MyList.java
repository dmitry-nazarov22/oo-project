package fi.nazarov.personapp.lists;

/**
 * Defines the structure for a list collection. This interface provides methods
 * for manipulating and accessing elements within the list.
 *
 * @param <T> generic parameter.
 */
public interface MyList<T> {

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e the element to add
     */
    void add(T e);

    /**
     * Removes all the elements from this list. The list will be empty after
     * this call returns.
     */
    void clear();

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= size())
     */
    T get(int index);

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements, otherwise false
     */
    boolean isEmpty();

    /**
     * Removes the element at the specified position in this list.
     * Returns the removed element.
     *
     * @param index the index of the element to remove
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= size())
     */
    T remove(int index);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present. Returns true if the list contained the specified
     * element.
     *
     * @param o the element to be removed from this list, if present
     * @return true if the list contained the specified element, otherwise false
     */
    boolean remove(T o);

    /**
     * Set new value to the specified index.
     *
     * @param index specified index where value should be updated.
     * @param updated value to be updated to the specified index.
     */
    void set(int index, T updated);

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    int size();
}
