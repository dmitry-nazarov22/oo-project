package fi.nazarov.personapp.ui;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.controllers.PersonController;
import fi.nazarov.personapp.lists.MyList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TerminalUI {
    private boolean isRunning;
    private final PersonController controller;

    public TerminalUI(PersonController controller) {
        this.controller = controller;
        isRunning = true;
    }

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

    public void printMenu() {
        IO.println("""
                x----------------------------x
                |   – Contacts App Maxi –    |
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

    private void createContact() {
        Scanner s = new Scanner(System.in);

        try {

            IO.println("First name:    ");
            String firstname = s.nextLine();
            IO.println("Last name:    ");
            String lastname = s.nextLine();
            IO.println("Age:    ");
            int age = s.nextInt();
            s.nextLine();
            IO.println("Email:    ");
            String email = s.nextLine();

            Person p = new Person(firstname, lastname, age, email);
            controller.createPerson(p);
            IO.println("Contact added!");
        } catch (IllegalArgumentException e) {
            IO.println(e);
        } catch (InputMismatchException e) {
            IO.println("Age must be a number.");
        }
    }

    private void deleteContact() {
        Scanner s = new Scanner(System.in);

        IO.println("Which contact to delete?");

        MyList<Person> persons = controller.fetchAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            int displayIndex = i + 1;
            IO.println(displayIndex + ".   " + p.getLastName() + ", " + p.getFirstName());
        }

        int index = s.nextInt() - 1;
        s.nextLine();

        controller.deletePerson(index);
        IO.println("Contact deleted successfully!");
    }

    private void editContact() {
        IO.println("Which contact to edit?");

        MyList<Person> persons = controller.fetchAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            int displayIndex = i + 1;
            IO.println(displayIndex + ".   " + p.getLastName() + ", " + p.getFirstName());
        }

        Scanner s = new Scanner(System.in);

        IO.println("Index of the contact:    ");
        int index = s.nextInt() - 1;
        s.nextLine();

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
                IO.println("Input new first name:   ");
                toEdit.setFirstName(s.nextLine());
                break;
            case "2":
                IO.println("Input new last name:   ");
                toEdit.setLastName(s.nextLine());
                break;
            case "3":
                IO.println("Input new age:   ");
                toEdit.setAge(s.nextInt());
                s.nextLine();
                break;
            case "4":
                IO.println("Input new email:   ");
                toEdit.setEmail(s.nextLine());
                break;
            case "5":
                IO.println("First name:    ");
                String firstname = s.nextLine();
                IO.println("Last name:    ");
                String lastname = s.nextLine();
                IO.println("Age:    ");
                int age = s.nextInt();
                s.nextLine();
                IO.println("Email:    ");
                String email = s.nextLine();

                try {
                    toEdit.setFirstName(firstname);
                    toEdit.setLastName(lastname);
                    toEdit.setAge(age);
                    toEdit.setEmail(email);

                    controller.editPerson(index, toEdit);
                    IO.println("Contact edited successfully!");
                } catch (IllegalArgumentException e) {
                    IO.println(e);
                }
                break;
            default:
                IO.println("There's no such option.");
        }
    }
}
