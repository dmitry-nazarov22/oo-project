package fi.nazarov.personapp.ui;

import java.util.Scanner;

public class TerminalUI {
    private boolean isRunning;
    private int choice;

    public TerminalUI() {
        isRunning = true;
    }

    public void start() {
        Scanner s = new Scanner(System.in);

        while(isRunning) {
            printMenu();
            choice = s.nextInt();
            s.nextLine();

            switch(choice) {
                case 0:
                    isRunning = false;
                    break;
                case 1:
                    showContacts();
                    break;
                case 2:
                    createContact();
                    break;
                case 3:
                    deleteContact();
                    break;
                case 4:
                    editContact();
                    break;

            }
        }
    }

    public void printMenu() {
        IO.println("""
                – Contacts App Maximus –
                Choose one of the options:
                    1. View all contacts
                    2. Add new contact
                    3. Delete a contact
                    4. Edit a contact
                    0. Quit
                """);
    }

    private void showContacts() {
    }

    private void createContact() {
    }

    private void deleteContact() {
    }

    private void editContact() {
    }
}
