package com.gen.maze;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class GView {
    private final MediaPlayer media = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/audio/click-21156.mp3")).toExternalForm()));
    private final SimpleBooleanProperty buttonDisableProperty = new SimpleBooleanProperty(false);
    private final SimpleDoubleProperty animationDelayProperty = new SimpleDoubleProperty(10);
    protected final Button btnBacktracking;
    private final AnchorPane rectGridPane;
    protected final Button btnKruskalMST;
    protected final Button btnBinaryTree;
    protected final Button btnPrimMST;
    private final BorderPane root;
    private final VBox algorithmVBox;
    private final Slider slider;

    public GView() {
        root = new BorderPane();
        rectGridPane = new AnchorPane();
        algorithmVBox = new VBox();
        slider = new Slider();
        btnBinaryTree = new Button("\uD83C\uDF33 BinaryTree");
        btnKruskalMST = new Button("\uD83C\uDF32 Kruskal's");
        btnBacktracking = new Button("➰ Backtracking");
        btnPrimMST = new Button("\uD83C\uDF42 Prim's");

        root.setStyle("-fx-background-color: BLACK");

        drawGrid();
        setUserControls();
    }

    private void setUserControls() {
        // Buttons
        algorithmVBox.setSpacing(5.0d);
        algorithmVBox.setPadding(new Insets(30.0d, 10.0d, 0, 10.0d));
        algorithmVBox.getChildren().addAll(btnBacktracking, btnBinaryTree, btnKruskalMST, btnPrimMST);
        algorithmVBox.getChildren().forEach(v -> v.disableProperty().bind(buttonDisableProperty));
        root.setRight(algorithmVBox);

        // Slider
        var sliderPane = configSliderPane();
        root.setBottom(sliderPane);
        BorderPane.setAlignment(sliderPane, Pos.CENTER);
    }

    private AnchorPane configSliderPane() {
        var sliderPane = new AnchorPane(slider);

        sliderPane.setMaxSize(100, 10);
        sliderPane.setPadding(new Insets(20, 100, 0, 0));

        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(10);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        animationDelayProperty.bindBidirectional(slider.valueProperty());

        return sliderPane;
    }

    protected void drawGrid() {
        for (int y = 30; y < GApp.GRID_HEIGHT - 30; y = y + GApp.Scale_Y) { // 600 - 60 = (540 / 15) = 36
            for (int x = 30; x < GApp.GRID_WIDTH - 30; x = x + GApp.Scale_X) { // 810 - 60 = (750 / 15) = 50
                Rectangle rect = getRectangle(x, y);
                rectGridPane.getChildren().add(rect);
            }
        }

        root.setCenter(rectGridPane);
    }

    private Rectangle getRectangle(int x, int y) {
        Rectangle rect = new Rectangle(GApp.Scale_X, GApp.Scale_Y);
        rect.setStrokeWidth(2.5);
        rect.setStroke(Color.MEDIUMPURPLE);
        rect.setX(x);
        rect.setY(y);

        return rect;
    }

    protected Region getRoot() {
        return root;
    }

    protected VBox getAlgorithmVBox() {
        return algorithmVBox;
    }

    protected ObservableList<Node> getChildren() {
        return rectGridPane.getChildren();
    }

    public SimpleDoubleProperty animationDelayPropertyProperty() {
        return animationDelayProperty;
    }

    public SimpleBooleanProperty buttonDisablePropertyProperty() {
        return buttonDisableProperty;
    }

    protected void soundBtnClick() {
        media.stop();
        media.play();
    }
}
