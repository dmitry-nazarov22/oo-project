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

    /**
     * Setter fot the PersonController.
     * @param controller controller to be assigned.
     */
    public static void setController(PersonController controller) {
        FXUI.controller = controller;
    }

    /**
     * Launches the JavaFX gui and initializes the main application window.
     * @param primaryStage the primary window.
     * @throws Exception if an unexpected error occurs during UI initialization.
     */
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

        Scene scene = new Scene(root, 900, 400);

        primaryStage.setTitle("Contacts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the TableView containing all needed TableColumns to display Persons.
     * @return the ready TableView.
     */
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

    /**
     * Creates a HBox containing all necessary fields to add new contacts: button, TextFields, error Labels.
     * @param table TableView where data will be added.
     * @return HBox containing all necessary fields to add new contacts: button, TextFields, error Labels.
     */
    private HBox createAddPersonComponent(TableView<Person> table) {

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Label firstNError = new Label();
        Label lastNError = new Label();
        Label ageError = new Label();
        Label emailError = new Label();

        firstNError.setStyle("-fx-text-fill: red;");
        lastNError.setStyle("-fx-text-fill: red;");
        ageError.setStyle("-fx-text-fill: red;");
        emailError.setStyle("-fx-text-fill: red;");


        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            if (!validateInputs(
                    firstNameField,
                    lastNameField,
                    ageField,
                    emailField,
                    firstNError,
                    lastNError,
                    ageError,
                    emailError
            )) return;

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

        return new HBox(5,
                new VBox(5, firstNameField, firstNError),
                new VBox(5, lastNameField, lastNError),
                new VBox(5, ageField, ageError),
                new VBox(5, emailField, emailError),
                addButton
        );
    }

    /**
     * Creates the DeleteColumn with functionality.
     * Creates a button on every cell in the column, with functionality to delete a contact from its own row.
     *
     * @param contacts TableView where the button will be deleting contacts.
     * @return TableColumn for the delete button.
     */
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

    /**
     * Creates the EditColumn with functionality.
     * Creates a button on every cell of the column, with functionality to edit a contact in its row.
     * @param contacts TableView in which the contact data will be taken from.
     * @return TableColumn containing edit buttons.
     */
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

    /**
     * Opens dialog window for editing contact.
     * Creates a dialog window to edit the contact with TextFields, error Labels and save|cancel Buttons.
     * Validates inputs after pressing "save" and shows errors or updates table in successful or error cases.
     *
     * @param p Person to be edited.
     * @param index index of the person.
     * @param contacts TableView to be updated after successful edit.
     */
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
            if (!validateInputs(
                firstNameField,
                lastNameField,
                ageField,
                emailField,
                firstNError,
                lastNError,
                ageError,
                emailError
            )) return;

            p.setFirstName(firstNameField.getText());
            p.setLastName(lastNameField.getText());
            p.setAge(Integer.parseInt(ageField.getText()));
            p.setEmail(emailField.getText());

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

    /**
     * Validates all fields and shows error messages on errors.
     *
     * @param firstNameField TextField for first name.
     * @param lastNameField TextField for last name.
     * @param ageField TextField for age.
     * @param emailField TextField for email.
     * @param firstNError Error Label for firstname.
     * @param lastNError Error Label for lastname.
     * @param ageError Error Label for age.
     * @param emailError Error Label for email.
     * @return boolean of validity of all fields.
     */
    private boolean validateInputs(
            TextField firstNameField,
            TextField lastNameField,
            TextField ageField,
            TextField emailField,
            Label firstNError,
            Label lastNError,
            Label ageError,
            Label emailError
    ) {
        boolean valid = true;

        firstNError.setText("");
        lastNError.setText("");
        ageError.setText("");
        emailError.setText("");

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
        return valid;
    }

    /**
     * Refreshes the table.
     * Clearing the table and fetching all data again from the controller.
     * @param table TableView with refreshed data.
     */
    private void refreshTable(TableView<Person> table) {
        table.getItems().clear();

        MyList<Person> persons = controller.fetchAllPersons();

        for (int i = 0; i < persons.size(); i++) {
            table.getItems().add(persons.get(i));
        }
    }
}
