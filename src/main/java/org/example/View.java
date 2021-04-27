package org.example;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.Constants.*;

public class View {

    private final Model model;
    private final ImageView[][] images;
    private Map<Integer, Image> picturesMap;

    public View(Model model) {
        this.model = model;

        int countX = model.getCountCellsX();
        int countY = model.getCountCellsY();
        images = new ImageView[countX][countY];

        setPicturesMap();
        for (int x = 0; x < countX; x++) {
            for (int y = 0; y < countY; y++) {
                ImageView newImage = new ImageView();
                newImage.setImage(picturesMap.get(model.getCell(x, y)));
                newImage.setFitWidth(FLOW_PANE_WIDTH / countX);
                newImage.setFitHeight(FLOW_PANE_HEIGHT / countY);
                images[x][y] = newImage;
            }
        }
    }

    private void setPicturesMap() {
        picturesMap = new HashMap<>();
        picturesMap.put(0, new Image(Objects.requireNonNull
                (Main.class.getResourceAsStream("images/0.png"))));
        for (int i = 2; i <= MAX_CELL_STATE; i = i * 2) {
            picturesMap.put(i, new Image(Objects.requireNonNull
                    (Main.class.getResourceAsStream("images/" + i + ".png"))));
        }
    }

    public ImageView[][] getImages(){
        return images;
    }

    public void updateScreen() {
        for (int y = 0; y < model.getCountCellsY(); y++) {
            for (int x = 0; x < model.getCountCellsX(); x++) {
                int value = model.getCell(x, y);
                images[x][y].setImage(picturesMap.get(value));
            }
        }
        checkLoseAlert();
    }

    private void checkLoseAlert() {
        if (!model.isThereMoves()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("2048");
            alert.setHeaderText("Игра закончена! Счет: " + model.counter);
            alert.show();
        }
    }

    public void setWinAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("2048");
        alert.setHeaderText("Вы выиграли!");
        alert.show();
    }

}
