package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import static org.example.Constants.*;

public class ControllerStart {

    public ComboBox<Integer> comboBoxXCells;
    public ComboBox<Integer> comboBoxYCells;

    private Stage stage;

    public void setStartScreen() {
        ObservableList<Integer> listXY = FXCollections.observableArrayList(COUNT_CELLS_XY);
        comboBoxXCells.setItems(listXY);
        comboBoxYCells.setItems(listXY);
    }

    @FXML
    public void startGame() throws Exception {
        int countX = (comboBoxXCells.getValue() == null )? 4 : comboBoxXCells.getValue();
        int countY = (comboBoxYCells.getValue() == null )? 4 : comboBoxYCells.getValue();

        Model model = new Model(countX, countY, WIN_CELL);
        model.newGame();
        View view = new View(model);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("interface.fxml"));
        Parent content = loader.load();
        Controller controller = loader.getController();

        controller.setModel(model);
        controller.setView(view);

        Scene scene = new Scene(content, SCREEN_WIDTH, SCREEN_HEIGHT);
        content.requestFocus();
        stage.setScene(scene);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
