package com.gen.maze;

import com.gen.maze.data.Cell;
import com.gen.maze.data.Dim;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class GController {
    private final GView view;
    private final GModel model;

    public GController() {
        model = new GModel();
        view = new GView();

        view.setBtnBacktrackingHandler(this::startBacktrackingAlgorithm);
    }

    private void startBacktrackingAlgorithm(MouseEvent e) {
        precondition();

        Thread.ofPlatform().start(new Task<Void>() {
            @Override
            protected Void call() {
                view.buttonDisablePropertyProperty().set(true);

                int y = model.getCells().length;
                int x = model.getCells()[0].length;
                iterativeBacktrackingAlgorithm(new boolean[y][x], new SecureRandom());

                view.buttonDisablePropertyProperty().set(false);
                return null;
            }
        });
    }

    private void precondition() {
        view.getChildren().clear();
        view.drawGrid();
        view.soundBtnClick();
    }

    public Region getView() {
        return view.getView();
    }

    private void iterativeBacktrackingAlgorithm(boolean[][] visited, SecureRandom random) {
        Deque<Cell> stack = new ArrayDeque<>();

        stack.push(model.getCells()[0][0]);
        visited[0][0] = true;

        while (!stack.isEmpty()) {
            Cell C_C = stack.pop();// C_C(Current Cell)
            var r = (Rectangle) view.getChildren().get(C_C.getX() + C_C.getY() * model.getCells()[0].length);
            r.setFill(Color.BLUEVIOLET);


            List<Cell> U_NCS = C_C.getAdjacentCells().stream().filter(neighbor -> !visited[neighbor.getY()][neighbor.getX()]).toList();

            if (!U_NCS.isEmpty()) {
                stack.push(C_C);

                Cell U_NC = U_NCS.get(random.nextInt(U_NCS.size())); // U_NC(Unvisited Neighbour Cell)
                removeWall(C_C, U_NC);
                visited[U_NC.getY()][U_NC.getX()] = true;

                stack.push(U_NC);

                wait_((int) view.animationDelayPropertyProperty().get() * 10);
            }

            r.setFill(Color.BLACK);
        }
    }

    public void wait_(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void removeWall(Cell currentCell, Cell unvisitedNeighbourCell) {
        if (currentCell.getY() - 1 == unvisitedNeighbourCell.getY()) { // UP
            removeWall(currentCell.getUpWall());
        } else if (currentCell.getY() + 1 == unvisitedNeighbourCell.getY()) { // DOWN
            removeWall(currentCell.getDownWall());
        } else if (currentCell.getX() - 1 == unvisitedNeighbourCell.getX()) { // LEFT
            removeWall(currentCell.getLeftWall());
        } else {
            removeWall(currentCell.getRightWall());
        }
    }

    private void removeWall(Dim upWall) {
        var removedWall = new Line(upWall.x1(), upWall.y1(), upWall.x2(), upWall.y2());
        removedWall.setStroke(Color.BLACK);

        Platform.runLater(() -> view.getChildren().add(removedWall));
    }
}
