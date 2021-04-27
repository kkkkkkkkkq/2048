package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static org.example.Constants.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Parent content = loader.load();
        ControllerStart contStart = loader.getController();
        contStart.setStage(stage);
        contStart.setStartScreen();

        Scene scene = new Scene(content, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle(SCREEN_NAME);
        stage.getIcons().add(new Image(Objects.requireNonNull
                (Main.class.getResourceAsStream("images/2048-icon.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
