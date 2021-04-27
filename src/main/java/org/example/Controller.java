package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

public class Controller {
    private Model model;
    private View view;
    public FlowPane flowPane;
    public Label score;

    @FXML
    public void keyboardPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W: {
                model.up();
                updateScreen();
                break;
            }
            case A: {
                model.left();
                updateScreen();
                break;
            }
            case S: {
                model.down();
                updateScreen();
                break;
            }
            case D: {
                model.right();
                updateScreen();
                break;
            }
        }
    }

    @FXML
    private void newGame() {
        model.newGame();
        updateScreen();
    }

    public void setImagesFlowPane() {
        flowPane.getChildren().clear();
        ImageView[][] images = view.getImages();
        for (int y = 0; y < model.getCountCellsY(); y++) {
            for (int x = 0; x < model.getCountCellsX(); x++) {
                flowPane.getChildren().add(images[x][y]);
            }
        }
    }

    private void updateScreen() {
        view.updateScreen();
        setScore();
        checkWin();
    }

    private void checkWin() {
        if (model.isWinEndOfGame()) {
            view.setWinAlert();
        }
    }

    public void setScore() {
        score.setText("Счет: " + model.getCounter());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
        setImagesFlowPane();
    }

}
