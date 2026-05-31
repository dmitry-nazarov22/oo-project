package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.datatype.Person;
import fi.nazarov.personapp.lists.MyList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVRepo implements Repository {
    private final MyList<Person> persons;
    private final String file;

    /**
     * Constructor assigning persons MyList and fileName for the CSV.
     *
     * @param persons MyList containing all Persons.
     * @param fileName name of the CSV-file.
     */
    public CSVRepo(MyList<Person> persons, String fileName) {
        this.persons = persons;
        this.file = fileName;

        loadCSV();
    }

    /**
     * Find all person objects to display them.
     *
     * @return MyList of all Person objects
     */
    @Override
    public MyList<Person> findAll() {
        return persons;
    }

    /**
     * Save new person to the list.
     * @param person person object to be saved.
     * @return true if successful and false if error.
     */
    @Override
    public boolean save(Person person) {
        persons.add(person);
        return updateFile();
    }

    /**
     * Delete a person from the list.
     * @return true if successful and false if error.
     */
    @Override
    public boolean delete(int index) {
        try {
            persons.remove(index);
            return updateFile();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Edit persons data in the list.
     *
     * @return true if successful and false if error.
     */
    @Override
    public boolean edit(int index, Person updated) {
        try {
            persons.set(index, updated);
            return updateFile();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Load data from CSV-file to a persons MyList.
     */
    private void loadCSV() {
        try {
            Path path = Paths.get(file);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split(",");

                String firstName = parts[0];
                String lastName = parts[1];
                int age = Integer.parseInt(parts[2]);
                String email = "";
                if (parts.length == 4) {
                    email = parts[3];
                }

                Person person = new Person(firstName, lastName, age, email);
                persons.add(person);
            }
        } catch(IOException e) {
            IO.println("Error while reading file.");
        } catch (InvalidPathException e) {
            IO.println("Invalid path given: " + e);
        }
    }

    /**
     * Updates CSV-file by rewriting persons MyList again to it.
     *
     * @return true if successful, false if not.
     */
    private boolean updateFile() {
        try {
            Path path = Paths.get(file);

            StringBuilder toWrite = new StringBuilder();

            for (int i = 0; i < persons.size(); i++) {
                Person p = persons.get(i);

                toWrite.append(p.getFirstName())
                        .append(",")
                        .append(p.getLastName())
                        .append(",")
                        .append(p.getAge())
                        .append(",")
                        .append(p.getEmail())
                        .append("\n");
            }

            Files.writeString(path, toWrite);
            return true;
        } catch(IOException e) {
            IO.println("Error while writing to file.");
            return false;
        } catch (InvalidPathException e) {
            IO.println("Invalid path given: " + e);
            return false;
        }
    }
}
