package jpp.numbergame.gui;

import javafx.scene.layout.VBox;
import jpp.numbergame.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class NumberGui extends Application{


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("2048");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.show();

        VBox parent = new VBox();
        Scene scene1 = new Scene(parent);
        primaryStage.setScene(scene1);
    }
    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My typa title");
        button = new Button();
        button.setText("Click me");

         StackPane layout = new StackPane();
         layout.getChildren().add(button);

         Scene scene = new Scene(layout, 300,250);
         primaryStage.setScene(scene);
         primaryStage.show();
    }
    */
}
