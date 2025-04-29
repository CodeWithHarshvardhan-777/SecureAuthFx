package org.hauthfx.secureauthfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Try extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button loadButton = new Button("Start Loading");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false); // Hide initially

        loadButton.setOnAction(e -> {
            // Show loading
            progressIndicator.setVisible(true);
            loadButton.setDisable(true);

            // Simulate loading with Task
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(3000); // Wait 3 seconds
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                progressIndicator.setVisible(false);
                loadButton.setDisable(false);
            });

            new Thread(task).start(); // Run task in background
        });

        VBox root = new VBox(20, loadButton, progressIndicator);
        root.setStyle("-fx-padding: 20; -fx-alignment: center");

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Simple JavaFX Loading");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
