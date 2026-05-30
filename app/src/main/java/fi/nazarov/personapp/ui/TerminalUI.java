package fi.nazarov.personapp.ui;

import fi.nazarov.personapp.datatype.Person;
import fi.nazarov.personapp.controllers.PersonController;
import fi.nazarov.personapp.lists.MyList;

import java.util.Scanner;

public class TerminalUI {
    private boolean isRunning;
    private final PersonController controller;

    /**
     * Constructor assigning PersonController and setting isRunning to be true.
     * @param controller PersonController to connect to repository.
     */
    public TerminalUI(PersonController controller) {
        this.controller = controller;
        isRunning = true;
    }

    /**
     * Launches ui setting the initial loop of using the application in the TerminalUI.
     */
    public void start() {
        Scanner s = new Scanner(System.in);

        while(isRunning) {
            printMenu();
            String choice = s.nextLine();

            switch(choice) {
                case "0":
                    IO.println("Closing the application...");
                    isRunning = false;
                    break;
                case "1":
                    showContacts();
                    break;
                case "2":
                    createContact();
                    break;
                case "3":
                    deleteContact();
                    break;
                case "4":
                    editContact();
                    break;
                default:
                    IO.println("There's no such option. Please, choose one form the menu");
            }
        }
    }

    /**
     * Prints the main menu.
     */
    public void printMenu() {
        IO.println("""
                x----------------------------x
                |      – Contacts App –      |
                |                            |
                | Choose one of the options: |
                |     1. View all contacts   |
                |     2. Add new contact     |
                |     3. Delete a contact    |
                |     4. Edit a contact      |
                |     0. Quit                |
                x----------------------------x
                """);
    }

    /**
     * Fetches persons from the repository and prints all persons.
     */
    private void showContacts() {
        MyList<Person> persons = controller.fetchAllPersons();

        IO.println("Contacts:\n");

        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);

            IO.println((i + 1) + ".");
            IO.println("Name:    " + p.getLastName() + ", " + p.getFirstName());
            IO.println("Age:     " + p.getAge());
            IO.println("Email:   " + p.getEmail() + "\n");
        }
    }

    /**
     * Prompts for new contact data and creates a new Person with controller.
     */
    private void createContact() {
        Scanner s = new Scanner(System.in);

        String firstname = promptValidName(s, "First name");
        String lastname = promptValidName(s, "Last name");
        int age = promptValidAge(s);
        String email = promptValidEmail(s);

        Person p = new Person(firstname, lastname, age, email);
        controller.createPerson(p);
        IO.println("Contact added!");
    }

    /**
     * Prompts for a contact to be deleted, validates to delete and deletes contact using controller.
     */
    private void deleteContact() {
        Scanner s = new Scanner(System.in);

        IO.println("Which contact to delete?");

        MyList<Person> persons = controller.fetchAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            int displayIndex = i + 1;
            IO.println(displayIndex + ".   " + p.getLastName() + ", " + p.getFirstName());
        }
        boolean invalidIndex = true;
        int index = 0;

        while (invalidIndex) {
            try {
                index = Integer.parseInt(s.nextLine()) - 1;
                invalidIndex = false;
            } catch (NumberFormatException e) {
                IO.println("Index must be a valid integer. Please, try again.");
            }
        }

        IO.println("Are you sure to delete contact number " + (index + 1) + "?");
        IO.println("Input 'y' if you are sure.");
        String verification = s.nextLine();

        if (verification.equals("y")) {
            controller.deletePerson(index);
            IO.println("Contact deleted successfully!");
        } else {
            IO.println("Contact was not deleted.");
        }
    }

    /**
     * Prompts for Person and its data to be edited, prompts for new data and edits Person using controller.
     */
    private void editContact() {
        IO.println("Which contact to edit?");

        MyList<Person> persons = controller.fetchAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            int displayIndex = i + 1;
            IO.println(displayIndex + ".   " + p.getLastName() + ", " + p.getFirstName());
        }

        Scanner s = new Scanner(System.in);
        int index;

        try {
            IO.println("Index of the contact:    ");
            index = Integer.parseInt(s.nextLine()) - 1;
        } catch (NumberFormatException e) {
            IO.println("Index must be a valid integer.");
            return;
        }


        Person toEdit = persons.get(index);

        IO.println("""
                x-------------------x
                | Field to edit:    |
                | 1. First name     |
                | 2. Last name      |
                | 3. Age            |
                | 4. Email          |
                | 5. All fields     |
                x-------------------x
                """);
        String choice = s.nextLine();

        switch(choice) {
            case "1":
                toEdit.setFirstName(promptValidName(s, "New first name"));
                IO.println("Contact edited successfully!");
                IO.println(toEdit);
                break;
            case "2":
                toEdit.setLastName(promptValidName(s, "New last name"));
                IO.println("Contact edited successfully!");
                IO.println(toEdit);
                break;
            case "3":
                toEdit.setAge(promptValidAge(s));
                IO.println("Contact edited successfully!");
                IO.println(toEdit);
                break;
            case "4":
                toEdit.setEmail(promptValidEmail(s));
                IO.println("Contact edited successfully!");
                IO.println(toEdit);
                break;
            case "5":
                toEdit.setFirstName(promptValidName(s, "New first name"));
                toEdit.setLastName(promptValidName(s, "New last name"));
                toEdit.setAge(promptValidAge(s));
                toEdit.setEmail(promptValidEmail(s));

                controller.editPerson(index, toEdit);
                IO.println("Contact edited successfully!");
                IO.println(toEdit);
                break;
            default:
                IO.println("There's no such option.");
        }
    }

    /**
     * Prompts valid name as long unless a valid name is given.
     * @param s Scanner.
     * @param fieldName name type to be validated (lastName | firstName).
     * @return validated name.
     */
    private String promptValidName(Scanner s, String fieldName) {
        IO.println(fieldName + ": [at least 2 characters]");
        String value = s.nextLine();
        while (!Person.validateName(value)) {
            IO.println("Invalid " + fieldName + ". Please, input it again:");
            value = s.nextLine();
        };

        return value;
    }

    /**
     * Prompts for a valid age unless a valid value is given.
     * @param s Scanner.
     * @return the valid age.
     */
    private int promptValidAge(Scanner s) {
        IO.println("Age: [age >= 0]");
        while (true) {
            try {
                int value = Integer.parseInt(s.nextLine());
                if (Person.validateAge(value)) return value;
                IO.println("Invalid age. Please, input it again:    [age >= 0]");
            } catch (NumberFormatException e) {
                IO.println("Age must be a valid integer. Please, input it again:");
            }
        }
    };

    /**
     * Prompts for valid email unless valid value is given.
     * @param s Scanner.
     * @return the valid value.
     */
    private String promptValidEmail(Scanner s) {
        IO.println("Email:    (optional | [test-mail@test.com])");
        String value = s.nextLine();
        while (!Person.validateEmail(value)) {
            IO.println("Invalid email. Please, input it again:    (optional | [test-mail@test.com])");
            value = s.nextLine();
        };

        return value;
    }
}
