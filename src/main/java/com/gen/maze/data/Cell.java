package com.gen.maze.data;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final int y;
    private final int x;
    private final Dim leftWall;
    private final Dim rightWall;
    private final Dim upWall;
    private final Dim downWall;
    private final List<Cell> adjacentCells;

    public Cell(int y, int x, Dim leftWall, Dim rightWall, Dim upWall, Dim downWall) {
        this.y = y;
        this.x = x;

        this.leftWall = leftWall;
        this.rightWall = rightWall;
        this.upWall = upWall;
        this.downWall = downWall;

        adjacentCells = new ArrayList<>();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Dim getLeftWall() {
        return leftWall;
    }

    public Dim getRightWall() {
        return rightWall;
    }

    public Dim getUpWall() {
        return upWall;
    }

    public Dim getDownWall() {
        return downWall;
    }

    public List<Cell> getAdjacentCells() {
        return adjacentCells;
    }
}
