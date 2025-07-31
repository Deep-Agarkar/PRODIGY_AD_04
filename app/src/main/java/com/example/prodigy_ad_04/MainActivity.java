package com.example.prodigy_ad_04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prodigy_ad_04.R;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private String currentPlayer = "X";
    private boolean gameActive = true;
    private TextView statusText;

    private final int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusText = findViewById(R.id.statusText);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 9; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);

            final int index = i;
            buttons[i].setOnClickListener(v -> onCellClicked(index));
        }

        findViewById(R.id.resetButton).setOnClickListener(v -> resetGame());
    }

    private void onCellClicked(int index) {
        if (!buttons[index].getText().toString().equals("") || !gameActive) return;

        buttons[index].setText(currentPlayer);
        if (checkWinner()) {
            statusText.setText("Player " + currentPlayer + " Wins!");
            gameActive = false;
        } else if (isDraw()) {
            statusText.setText("It's a Draw!");
            gameActive = false;
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            statusText.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        for (int[] pos : winPositions) {
            String a = buttons[pos[0]].getText().toString();
            String b = buttons[pos[1]].getText().toString();
            String c = buttons[pos[2]].getText().toString();
            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                highlightWinner(pos);
                return true;
            }
        }
        return false;
    }

    private void highlightWinner(int[] pos) {
        for (int i : pos) {
            buttons[i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
    }

    private boolean isDraw() {
        for (Button b : buttons) {
            if (b.getText().toString().equals("")) return false;
        }
        return true;
    }

    private void resetGame() {
        currentPlayer = "X";
        gameActive = true;
        statusText.setText("Player X's Turn");
        for (Button b : buttons) {
            b.setText("");
            b.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }
}
