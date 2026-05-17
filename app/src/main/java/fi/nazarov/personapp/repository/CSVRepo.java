package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.Person;
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

    public CSVRepo(MyList<Person> persons, String fileName) {
        this.persons = persons;
        this.file = fileName;

        loadCSV();
    }

    @Override
    public MyList<Person> findAll() {
        return persons;
    }

    @Override
    public boolean save(Person person) {
        persons.add(person);
        return updateFile();
    }

    @Override
    public boolean delete(int index) {
        try {
            persons.remove(index);
            return updateFile();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public boolean edit(int index, Person updated) {
        try {
            persons.set(index, updated);
            return updateFile();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

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
                String email = parts[3];

                Person person = new Person(firstName, lastName, age, email);
                persons.add(person);
            }
        } catch(IOException e) {
            IO.println("Error while reading file.");
        } catch (InvalidPathException e) {
            IO.println("Invalid path given: " + e);
        }
    }

    private boolean updateFile() {
        try {
            Path path = Paths.get(file);
            System.out.println(path.toAbsolutePath());

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
