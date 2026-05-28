package fi.nazarov.personapp.ui;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.controllers.PersonController;
import fi.nazarov.personapp.lists.MyList;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

        contacts.getColumns().addAll(
                firstNameCol,
                lastNameCol,
                ageCol,
                emailCol
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

    private void refreshTable(TableView<Person> table) {
        table.getItems().clear();

        MyList<Person> persons = controller.fetchAllPersons();

        for (int i = 0; i < persons.size(); i++) {
            table.getItems().add(persons.get(i));
        }
    }
}
