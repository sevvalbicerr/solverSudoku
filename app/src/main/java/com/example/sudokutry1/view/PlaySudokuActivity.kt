package com.example.sudokutry1.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sudokutry1.R
import com.example.sudokutry1.databinding.ActivityPlaySudokuBinding
import com.example.sudokutry1.game.Cell
import com.example.sudokutry1.view.custom.SudokuBoardView
import com.example.sudokutry1.viewmodel.PlaySudokuViewModel


class PlaySudokuActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private  val viewModel: PlaySudokuViewModel by lazy {
        ViewModelProvider(this)[PlaySudokuViewModel::class.java]
    }
    private lateinit var numberButtons: List<Button>

    private lateinit var binding : ActivityPlaySudokuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_sudoku)
        binding = ActivityPlaySudokuBinding.inflate(layoutInflater)
        binding.sudokuBoardView.registerListener(this)


        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })
        viewModel.sudokuGame.isTakingNotesLiveData.observe(this, Observer { updateNoteTakingUI(it) })
        viewModel.sudokuGame.highlightedKeysLiveData.observe(this, Observer { updateHighlightedKeys(it) })

        numberButtons = listOf(binding.oneButton, binding.twoButton, binding.threeButton, binding.fourButton
            , binding.fiveButton, binding.sixButton,
            binding.sevenButton, binding.eightButton, binding.nineButton)

        numberButtons.forEachIndexed { index, button ->
            //sayfa açıldığı anda 2 kere çalışıyor ve sonra hiç çalışmıyor
            println("çalışmıyor Playsudokuactivity satır 43")
            button.setOnClickListener {

                viewModel.sudokuGame.handleInput(index + 1)
                println("çalışmıyor Playsudokuactivity satır 46")
            }
        }

        binding.notesButton.setOnClickListener { viewModel.sudokuGame.changeNoteTakingState() }
        binding.deleteButton.setOnClickListener { viewModel.sudokuGame.delete() }
        //done() fonksiyonu butona tıklanıldığında mevcut durumdaki tabloyu
        binding.finishButton.setOnClickListener{ viewModel.sudokuGame.done() }
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        binding.sudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    private fun updateNoteTakingUI(isNoteTaking: Boolean?) = isNoteTaking?.let {
        val color = if (it) ContextCompat.getColor(this, R.color.design_default_color_primary) else Color.LTGRAY
        binding.notesButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    private fun updateHighlightedKeys(set: Set<Int>?) = set?.let {
        numberButtons.forEachIndexed { index, button ->
            val color = if (set.contains(index + 1)) ContextCompat.getColor(this, R.color.design_default_color_primary) else Color.LTGRAY
            button.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}
