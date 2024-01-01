package com.gen.maze;

import com.gen.maze.data.Cell;
import com.gen.maze.data.Dim;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
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

        view.getAlgorithmVBox().getChildren().forEach(btn -> btn.setOnMouseClicked(this::startAlgorithm));
    }

    private void startAlgorithm(MouseEvent e) {
        view.getChildren().clear();
        view.drawGrid();
        view.soundBtnClick();

        Thread.ofPlatform().start(new Task<Void>() {
            @Override
            protected Void call() {
                view.buttonDisablePropertyProperty().set(true);

                int y = model.getCells().length;
                int x = model.getCells()[0].length;

                if (e.getSource() instanceof Button btn) {
                    if (btn == view.btnBacktracking) {
                        iterativeBacktracking(new boolean[y][x], new SecureRandom());
                    } else if (btn == view.btnKruskalMST) {
                        // TODO
                    } else if (btn == view.btnPrimMST) {
                        // TODO
                    } else if (btn == view.btnBinaryTree) {
                        binaryTree(y, x, new SecureRandom());
                    }
                }


                view.buttonDisablePropertyProperty().set(false);
                return null;
            }
        });
    }

    private void binaryTree(int y, int x, SecureRandom flip) {
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                animateWalk(model.getCells()[j][i]);

                if (j - 1 >= 0 && i + 1 < x) {// 2 ways
                    var value = flip.nextInt(2);
                    if (value == 0) {
                        removeWall(model.getCells()[j][i].getUpWall());
                    } else {
                        removeWall(model.getCells()[j][i].getRightWall());
                    }
                } else if (j - 1 >= 0) { // north
                    removeWall(model.getCells()[j][i].getUpWall());
                } else if (i + 1 < x) { // east
                    removeWall(model.getCells()[j][i].getRightWall());
                }

                pause();
                deAnimateWalk(model.getCells()[j][i]);
            }
        }
    }

    public Region getView() {
        return view.getRoot();
    }

    private void iterativeBacktracking(boolean[][] visited, SecureRandom random) {
        Deque<Cell> stack = new ArrayDeque<>();

        stack.push(model.getCells()[0][0]);
        visited[0][0] = true;

        while (!stack.isEmpty()) {
            Cell C_C = stack.pop();// C_C(Current Cell)
            animateWalk(C_C);


            List<Cell> U_NCS = C_C.getAdjacentCells().stream().filter(neighbor -> !visited[neighbor.getY()][neighbor.getX()]).toList();

            if (!U_NCS.isEmpty()) {
                stack.push(C_C);

                Cell U_NC = U_NCS.get(random.nextInt(U_NCS.size())); // U_NC(Unvisited Neighbour Cell)
                removeWall(C_C, U_NC);
                visited[U_NC.getY()][U_NC.getX()] = true;

                stack.push(U_NC);

                pause();
            }

            deAnimateWalk(C_C);
        }
    }

    private void deAnimateWalk(Cell C_C) {
        var r = (Rectangle) view.getChildren().get(C_C.getX() + C_C.getY() * model.getCells()[0].length);
        r.setFill(Color.BLACK);
    }

    private void animateWalk(Cell C_C) {
        var r = (Rectangle) view.getChildren().get(C_C.getX() + C_C.getY() * model.getCells()[0].length);
        r.setFill(Color.BLUEVIOLET);
    }

    public void pause() {
        try {
            Thread.sleep((int) view.animationDelayPropertyProperty().get() * 10L);
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

    private void removeWall(Dim wall) {
        var line = new Line(wall.x1(), wall.y1(), wall.x2(), wall.y2());
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2.5);

        Platform.runLater(() -> view.getChildren().add(line));
    }
}
