package com.example.sudokutry1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sudokutry1.game.SudokuGame

class PlaySudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()
}