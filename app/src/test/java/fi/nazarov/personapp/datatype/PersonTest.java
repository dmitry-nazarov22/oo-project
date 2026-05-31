package fi.nazarov.personapp.datatype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void constructorShouldAssignValues() {
        Person p = new Person("testfirst", "testlast", 123, "test@test.com");

        assertEquals("testfirst", p.getFirstName());
        assertEquals("testlast", p.getLastName());
        assertEquals(123, p.getAge());
        assertEquals("test@test.com", p.getEmail());
    }

    @Test
    void settersShouldAssignValues() {
        Person p = new Person("oldfirst", "oldlast", 0, "");

        p.setFirstName("newfirst");
        p.setLastName("newlast");
        p.setAge(1);
        p.setEmail("new@test.com");

        assertEquals("newfirst", p.getFirstName());
        assertEquals("newlast", p.getLastName());
        assertEquals(1, p.getAge());
        assertEquals("new@test.com", p.getEmail());
    }

    @Test
    void validNamePassesValidation() {
        assertTrue(Person.validateName("Valid"));
    }

    @Test
    void validAgePassesValidation() {
        assertTrue(Person.validateAge(123));
    }

    @Test
    void validEmailPassesValidation() {
        assertTrue(Person.validateEmail(""));
        assertTrue(Person.validateEmail("test@test.com"));
        assertTrue(Person.validateEmail("test-testing@test.com"));
    }

    @Test
    void invalidNameFailsValidation() {
        assertFalse(Person.validateName("f"));
    }

    @Test
    void invalidAgeFailsValidation() {
        assertFalse(Person.validateAge(-1));
    }

    @Test
    void invalidEmailFailsValidation() {
        assertFalse(Person.validateEmail("f"));
        assertFalse(Person.validateEmail("test@test"));
        assertFalse(Person.validateEmail("test-testing.com"));
    }
}
