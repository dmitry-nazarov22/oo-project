package fi.nazarov.personapp.ui;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.controllers.PersonController;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        TableView<Person> table = createPersonTable();
        root.getChildren().add(table);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Contacts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Person> createPersonTable() {
        TableView<Person> contacts = new TableView<>();

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFirstName()
        ));

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getLastName()
        ));

        TableColumn<Person, Number> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue().getAge()
        ));

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getEmail()
        ));

        contacts.getColumns().addAll(
                firstNameCol,
                lastNameCol,
                ageCol,
                emailCol
        );

        return contacts;
    }
}
