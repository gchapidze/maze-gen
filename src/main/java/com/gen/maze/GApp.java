package com.gen.maze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class GApp extends Application {
    protected static final int GRID_WIDTH = 810, GRID_HEIGHT = 600, Scale_X = 15, Scale_Y = 15;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new GController().getView());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setHeight(GRID_HEIGHT + 110);
        stage.setWidth(GRID_WIDTH + 110);
        stage.setTitle("Perfect Maze (gc)");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();
    }
}
