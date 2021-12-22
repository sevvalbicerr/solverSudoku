package com.example.sudokutry1

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sudokutry1.databinding.ActivitySolverBinding

//Backtrakcing Algorithm
class solver : AppCompatActivity() {
    private lateinit var  binding: ActivitySolverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySolverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.solve.setOnClickListener {
            //Bulmacayı çöz butonu
        }
    }
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        val board = arrayOf(
            intArrayOf(0, 0, 0, 0, 6, 4, 0, 0, 0),
            intArrayOf(7, 0, 0, 0, 0, 0, 3, 9, 0),
            intArrayOf(8, 0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 5, 0, 2, 0, 6, 0),
            intArrayOf(0, 8, 0, 4, 0, 0, 0, 0, 0),
            intArrayOf(3, 5, 0, 6, 0, 0, 0, 7, 0),
            intArrayOf(0, 0, 2, 0, 0, 0, 1, 0, 3),
            intArrayOf(0, 0, 1, 0, 5, 9, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 7, 0, 0)
        )
        val N = board.size
      //  generateBoard()
        if (solveSudoku(board, N)) {
            // print solution
            print(board, N)
        } else {
            println("No solution")
        }
        return super.onCreateView(name, context, attrs)
    }

    fun isSafe(
        board: Array<IntArray>,
        row: Int, col: Int,
        num: Int
    ): Boolean {
        // Row has the unique (row-clash)
        for (d in board.indices) {

            // Check if the number we are trying to
            // place is already present in
            // that row, return false;
            if (board[row][d] == num) {
                return false
            }
        }

        // Column has the unique numbers (column-clash)
        for (r in board.indices) {

            // Check if the number
            // we are trying to
            // place is already present in
            // that column, return false;
            if (board[r][col] == num) {
                return false
            }
        }

        // Corresponding square has
        // unique number (box-clash)
        val sqrt = Math.sqrt(board.size.toDouble()).toInt()
        val boxRowStart = row - row % sqrt
        val boxColStart = col - col % sqrt
        for (r in boxRowStart until boxRowStart + sqrt) {
            for (d in boxColStart until boxColStart + sqrt) {
                if (board[r][d] == num) {
                    return false
                }
            }
        }

        // if there is no clash, it's safe
        return true
    }

    fun solveSudoku(
        board: Array<IntArray>, n: Int
    ): Boolean {
        var row = -1
        var col = -1
        var isEmpty = true
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (board[i][j] == 0) {
                    row = i
                    col = j

                    // We still have some remaining
                    // missing values in Sudoku
                    isEmpty = false
                    break
                }
            }
            if (!isEmpty) {
                break
            }
        }

        // No empty space left
        if (isEmpty) {
            return true
        }

        // Else for each-row backtrack
        for (num in 1..n) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num
                if (solveSudoku(board, n)) {
                    // print(board, n);
                    return true
                } else {
                    // replace it
                    board[row][col] = 0
                }
            }
        }
        return false
    }

    fun print(
        board: Array<IntArray>, N: Int
    ) {

        // We got the answer, just print it
        for (r in 0 until N) {
            for (d in 0 until N) {
                print(board[r][d])
                print(" ")
            }
            print("\n")
            if ((r + 1) % Math.sqrt(N.toDouble()).toInt() == 0) {
                print("")
            }
        }
    }


}