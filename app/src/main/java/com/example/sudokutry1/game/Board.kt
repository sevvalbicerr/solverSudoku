package com.example.sudokutry1.game

import com.example.sudokutry1.game.Cell

class Board(val size: Int, val cells: List<Cell>) {
    fun getCell(row: Int, col: Int) = cells[row * size + col]
}