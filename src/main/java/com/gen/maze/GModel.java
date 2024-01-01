package com.gen.maze;

import com.gen.maze.data.Cell;
import com.gen.maze.data.Dim;

public class GModel {
    private final Cell[][] cells;

    public Cell[][] getCells() {
        return cells;
    }

    public GModel() {
        int y = (int) Math.floor((GApp.GRID_HEIGHT - 60.0) / GApp.Scale_Y);
        int x = (int) Math.floor((GApp.GRID_WIDTH - 60.0) / GApp.Scale_X);

        cells = new Cell[y][x];
        initCellData();
    }

    private void initCellData() {
        initCellAndWalls_XY();
        assignCellNeighbours();

    }

    private void assignCellNeighbours() {
        for (int j = 0; j < cells.length; j++) {
            for (int i = 0; i < cells[0].length; i++) {
                var currentCell = cells[j][i];
                if (j - 1 >= 0) { // UP
                    currentCell.getAdjacentCells().add(cells[j - 1][i]);
                }
                if (j + 1 < cells.length) { // DOWN
                    currentCell.getAdjacentCells().add(cells[j + 1][i]);
                }
                if (i - 1 >= 0) { // LEFT
                    currentCell.getAdjacentCells().add(cells[j][i - 1]);
                }
                if (i + 1 < cells[0].length) { // RIGHT
                    currentCell.getAdjacentCells().add(cells[j][i + 1]);
                }
            }
        }
    }

    private void initCellAndWalls_XY() {
        int scaleY = GApp.Scale_Y;
        int scaleX = GApp.Scale_X;
        for (int y = 30, j = 0; y < GApp.GRID_HEIGHT - 30; y = y + scaleY, j++) {
            for (int x = 30, i = 0; x < GApp.GRID_WIDTH - 30; x = x + scaleX, i++) {
                cells[j][i] = new Cell(j, i, new Dim(y, x, y + scaleY, x), new Dim(y, x + scaleX, y + scaleY, x + scaleX), new Dim(y, x, y, x + scaleX), new Dim(y + scaleY, x, y + scaleY, x + scaleX));
            }
        }
    }
}
