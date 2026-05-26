package fi.nazarov.personapp.ui;

import fi.nazarov.personapp.controllers.PersonController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXUI extends Application {

    private static PersonController controller;

    public static void setController(PersonController controller) {
        FXUI.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();

        Scene scene = new Scene(pane, 400, 400);

        primaryStage.setTitle("Contacts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
