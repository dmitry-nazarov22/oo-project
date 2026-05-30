package fi.nazarov.personapp;

import java.util.Objects;
import java.util.regex.Pattern;

public class Person {
    String firstName;
    String lastName;
    int age;
    String email;

    /**
     * Constructor assigning firstName, lastName, age and email.
     *
     * @param firstName first name of the Person.
     * @param lastName last name of the Person.
     * @param age age of the person.
     * @param email email of the person.
     */
    public Person(String firstName, String lastName, int age, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setEmail(email);
    }

    /**
     * Getter for first name.
     *
     * @return first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for first name.
     *
     * @param firstName first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for last name.
     *
     * @return last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for last name.
     * @param lastName last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for age.
     * @return age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter for age.
     * @param age age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter for email.
     * @return email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email.
     * @param email email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Validates names to have at least 2 chars.
     * @param name name to be validated.
     * @return boolean for passing validation or not.
     */
    public static boolean validateName(String name) {
        return name.length() >= 2;
    }

    /**
     * Validates age to be 0 or greater.
     * @param age age to be validated.
     * @return boolean for passing validation or not.
     */
    public static boolean validateAge(int age) {
        return age >= 0;
    }

    /**
     * Validates email to be empty or valid email using regex.
     * @param email email to be validated.
     * @return boolean to match the regex pattern or not.
     */
    public static boolean validateEmail(String email) {
        String regexPattern = "^$|^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(regexPattern)
            .matcher(email)
            .matches();
    }

    /**
     * Show String representation of the Person.
     * @return String representation of the Person.
     */
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * Checks for equality of object compared to this Person.
     * @param o   the reference object with which to compare.
     * @return boolean representing equality of object to Person.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email);
    }

    /**
     * Hashcode method.
     * @return hashcode of the Person.
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, email);
    }
}
