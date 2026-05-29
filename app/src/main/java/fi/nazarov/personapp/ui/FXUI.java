package fi.nazarov.personapp.ui;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.controllers.PersonController;
import fi.nazarov.personapp.lists.MyList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXUI extends Application {

    private static PersonController controller;

    public static void setController(PersonController controller) {
        FXUI.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setPadding(new Insets(30));
        root.setSpacing(10);

        TableView<Person> table = createPersonTable();
        root.getChildren().add(table);
        refreshTable(table);

        HBox addPersonComponent = createAddPersonComponent(table);

        root.getChildren().add(addPersonComponent);

        Scene scene = new Scene(root, 800, 400);

        primaryStage.setTitle("Contacts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Person> createPersonTable() {
        TableView<Person> contacts = new TableView<>();

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName")
        );

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName")
        );

        TableColumn<Person, Number> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(
                new PropertyValueFactory<Person, Number>("age")
        );

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("email")
        );

        TableColumn<Person, Void> editCol = getEditColumn(contacts);

        TableColumn<Person, Void> deleteCol = getDeleteColumn(contacts);

        contacts.getColumns().addAll(
                firstNameCol,
                lastNameCol,
                ageCol,
                emailCol,
                editCol,
                deleteCol
        );

        return contacts;
    }

    private HBox createAddPersonComponent(TableView<Person> table) {
        HBox addPersonComponent = new HBox();

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");


        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();

            Person p = new Person(firstName, lastName, age, email);
            controller.createPerson(p);

            refreshTable(table);

            firstNameField.clear();
            lastNameField.clear();
            ageField.clear();
            emailField.clear();
        });

        addPersonComponent.getChildren().addAll(
                firstNameField,
                lastNameField,
                ageField,
                emailField,
                addButton
        );

        return addPersonComponent;
    }

    private TableColumn<Person, Void> getDeleteColumn(TableView<Person> contacts) {
        TableColumn<Person, Void> deleteCol = new TableColumn<>();
        deleteCol.setCellFactory(cell -> new TableCell<>() {
            final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    controller.deletePerson(index);
                    refreshTable(contacts);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        return deleteCol;
    }

    private TableColumn<Person, Void> getEditColumn(TableView<Person> contacts) {
        TableColumn<Person, Void> editCol = new TableColumn<>();
        editCol.setCellFactory(cell -> new TableCell<>() {
            final Button editButton = new Button("Edit");
            {
                editButton.setOnAction(event -> {
                    int index = getIndex();
                    Person p = contacts.getItems().get(index);
                    openEditDialog(p, index, contacts);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
        return editCol;
    }

    private void openEditDialog(Person p, int index, TableView<Person> contacts) {
        Stage dialog = new Stage();
        dialog.setTitle("Edit contact");

        TextField firstNameField = new TextField(p.getFirstName());
        firstNameField.setPromptText("First name");

        TextField lastNameField = new TextField(p.getLastName());
        lastNameField.setPromptText("Last name");

        TextField ageField = new TextField(String.valueOf(p.getAge()));
        ageField.setPromptText("Age");

        TextField emailField = new TextField(p.getEmail());
        emailField.setPromptText("Email");

        Label firstNError = new Label();
        Label lastNError = new Label();
        Label ageError = new Label();
        Label emailError = new Label();

        firstNError.setStyle("-fx-text-fill: red;");
        lastNError.setStyle("-fx-text-fill: red;");
        ageError.setStyle("-fx-text-fill: red;");
        emailError.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {

            boolean valid = true;

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String ageString = ageField.getText();
            String email = emailField.getText();

            if (!Person.validateName(firstName)) {
                firstNError.setText("First name must be at lest 2 characters.");
                valid = false;
            }

            if (!Person.validateName(lastName)) {
                lastNError.setText("Last name must be at lest 2 characters.");
                valid = false;
            }

            int age = -0;

            try {
                age = Integer.parseInt(ageString);
                if (!Person.validateAge(age)) {
                    ageError.setText("Age must be 0 or greater.");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                ageError.setText("Age must be a valid number!");
                valid = false;
            }

            if (!Person.validateEmail(email)) {
                emailError.setText("Invalid email");
                valid = false;
            }

            if (!valid) return;

            p.setFirstName(firstName);
            p.setLastName(lastName);
            p.setAge(age);
            p.setEmail(email);

            controller.editPerson(index, p);
            refreshTable(contacts);
            dialog.close();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            dialog.close();
        });

        VBox layout = new VBox(5,
            firstNameField, firstNError,
            lastNameField, lastNError,
            ageField, ageError,
            emailField, emailError,
            saveButton,
            closeButton
        );
        layout.setPadding(new Insets(10));

        dialog.setScene(new Scene(layout, 300, 300));
        dialog.show();
    }

    private void refreshTable(TableView<Person> table) {
        table.getItems().clear();

        MyList<Person> persons = controller.fetchAllPersons();

        for (int i = 0; i < persons.size(); i++) {
            table.getItems().add(persons.get(i));
        }
    }
}
