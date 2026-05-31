package fi.nazarov.personapp.lists;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {

    @Test
    void addAndGetShouldWork() {
        MyList<String> list = new MyArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");

        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    void removeWithIndexShouldWork() {
        MyList<String> list = new MyArrayList<>();

        list.add("a");

        list.remove(0);


        assertEquals(0, list.size());
    }

    @Test
    void removeWithObjectShouldWork() {
        MyList<String> list = new MyArrayList<>();

        list.add("b");
        list.remove("b");


        assertEquals(0, list.size());
    }

    @Test
    void removeByIndexShouldShiftValues() {
        MyList<String> list = new MyArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");

        list.remove(1);

        assertEquals("c", list.get(1));
    }

    @Test
    void sizeShouldIncrease() {
        MyList<String> list = new MyArrayList<>();

        assertEquals(0, list.size());

        list.add("a");
        list.add("b");

        assertEquals(2, list.size());
    }
}
