package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttons: Array<Array<Button>>
    private var player1Turn = true

    private lateinit var player1Wins: TextView
    private lateinit var player2Wins: TextView

    private var roundCount = 0
    private var player1Points = 0
    private var player2Points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player1Wins = findViewById(R.id.player1Wins)
        player2Wins = findViewById(R.id.player2Wins)

        // Initialize the 2D array of buttons
        buttons = arrayOf(
            arrayOf(findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3)),
            arrayOf(findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6)),
            arrayOf(findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9))
        )

        // Set OnClickListener for each button
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener(this)
            }
        }

        // Initialize scores
        updatePointsText()
    }

    override fun onClick(v: View?) {
        if ((v as Button).text.toString() != "") {
            return
        }

        if (player1Turn) {
            v.text = "X"
        } else {
            v.text = "O"
        }

        roundCount++

        checkWin()

        player1Turn = !player1Turn
    }

    private fun checkWin() {
        // Check rows
        for (i in 0..2) {
            if (checkRow(i)) {
                playerHasWon(player1Turn)
                return
            }
        }

        // Check columns
        for (i in 0..2) {
            if (checkColumn(i)) {
                playerHasWon(player1Turn)
                return
            }
        }

        // Check diagonals
        if (checkDiagonal()) {
            playerHasWon(player1Turn)
            return
        }

        if (roundCount == 9) {
            draw()
        }
    }

    private fun checkRow(row: Int): Boolean {
        val value = buttons[row][0].text
        return value != "" && value == buttons[row][1].text && value == buttons[row][2].text
    }

    private fun checkColumn(col: Int): Boolean {
        val value = buttons[0][col].text
        return value != "" && value == buttons[1][col].text && value == buttons[2][col].text
    }

    private fun checkDiagonal(): Boolean {
        // Check left-to-right diagonal
        val value = buttons[0][0].text
        return value != "" && value == buttons[1][1].text && value == buttons[2][2].text

        // Check right-to-left diagonal
        // val value = buttons[0][2].text
        // return value != "" && value == buttons[1][1].text && value == buttons[2][0].text
    }

    private fun playerHasWon(player1Turn: Boolean) {
        var winnerText = ""
        if (player1Turn) {
            winnerText = "Player 1 Wins!"
            player1Points++
        } else {
            winnerText = "Player 2 Wins!"
            player2Points++
        }

        Toast.makeText(this, winnerText, Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun updatePointsText() {
        player1Wins.text = "Player 1: $player1Points"
        player2Wins.text = "Player 2: $player2Points"
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }

        roundCount = 0
        player1Turn = true
    }
}