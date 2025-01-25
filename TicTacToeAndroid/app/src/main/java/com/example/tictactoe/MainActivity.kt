package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var board = Array(3) { CharArray(3) { ' ' } }
    private var currentPlayer = 'X'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBoard()
        binding.resetButton.setOnClickListener { resetGame() }
    }

    private fun setupBoard() {
        val buttons = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    onCellClicked(buttons[i][j], i, j)
                }
            }
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (board[row][col] != ' ') return

        board[row][col] = currentPlayer
        button.text = currentPlayer.toString()

        when {
            checkWinner() -> {
                binding.statusTextView.text = "Player $currentPlayer Wins!"
                disableBoard()
            }
            isBoardFull() -> binding.statusTextView.text = "It's a Draw!"
            else -> {
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                binding.statusTextView.text = "Player $currentPlayer's Turn"
            }
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { cell -> cell != ' ' } }
    }

    private fun disableBoard() {
        val buttons = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        board = Array(3) { CharArray(3) { ' ' } }
        currentPlayer = 'X'
        binding.statusTextView.text = "Player X's Turn"

        val buttons = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = true
                buttons[i][j].text = ""
            }
        }
    }
}
