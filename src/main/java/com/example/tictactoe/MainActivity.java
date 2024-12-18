package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//import
public class MainActivity extends AppCompatActivity {
    int player1Wins = 0;
    int player2Wins = 0;
    // 0 = yellow, 1 = red
    int activePlayer = 0;  // set active player to 0, means player 1
    boolean gameIsActive = true;  // yes, the game is active
    // 2 means unplayed, for all the 9 cells
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {1,4,7},
            {2,5,8}, {0,4,8}, {2,4,6},{0,3,6}};
    // tic tac toe game, which cell is marked
// what happen when the user click the image on the board
    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        // we need to know how many pictures click on the board, 9 pictures

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        // we need to know which picture click by the user on the board
        // look at the tag value for each image view object
        // tag 0 = first image to tag 8 9th image
        // we have 9 images here for 3  rows x 3 columns cell

        // if the user click the image within the board and
        // the game status is still active
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;

            // if the user is active, player turn 1 or 2
            counter.setTranslationY(-1000f);  // run animation

            if (activePlayer == 0) {  // if player 1 turn
                counter.setImageResource(R.drawable.kuromii);
                // get this image from res folder
                activePlayer = 1;
                // set active player to player 2 after player 1 finish
            } else {  // if player 2 turn
                counter.setImageResource(R.drawable.mymelody);
                // get this image from res folder
                activePlayer = 0; // set active player 1 to player 2 finish
            }

            // here
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            // run animation again
            checkForWinner();
        }  // end if for game is active
    }  // end of method dropIn()

    @SuppressLint("SetTextI18n")
    private void checkForWinner(){
        // use for loop here to find out the winners by checking the array elements of the winning position
        // check if the user has clicked on the winning position or not
        for (int[] winningPosition : winningPositions) {

            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {

                // Someone has won!, because they have click in the winning cells or positions
                // written in the array
                gameIsActive = false;  // set game status to false, not active anymore, cannot play already

                String winner = "My Melody";  // set winner to red, player 2

                if (gameState[winningPosition[0]] == 0) {  // if player 1 win the game
                    winner = "Kuromi";  // if the winner is player 1
                }

                TextView winnerMessage = findViewById(R.id.winnerMessage);
                // get the label from res folder, xml file
                winnerMessage.setText(winner + " has won! <3");  // display the winner

                LinearLayout layout = findViewById(R.id.playAgainLayout);
                // get the layout manager from res folder, xml file
                layout.setVisibility(View.VISIBLE);  // set the layout to visible to display all the components

                if (winner.equals("Kuromi")) {
                    player1Wins++;
                } else {
                    player2Wins++;
                }

                TextView scoreTextView = findViewById(R.id.scoreTextView);
                String scoreText = String.format("%d : %d", player1Wins, player2Wins);
                scoreTextView.setText(scoreText);

            } else {
                boolean gameIsOver = true;
                // game is over, no winner or losers

                for (int counterState : gameState) {
                    // for loop to check no winner or losers
                    if (counterState == 2) {
                        gameIsOver = false;
                        break;
                    }
                } // end of for loop for draw condition, no winner or loser

                if (gameIsOver) {
                    // if game is over already with no winner or losers
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    // get the textview label from res, xml file
                    winnerMessage.setText("It's a draw!");
                    // display message
                    gameState = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2};
                    // Reset game state
                    activePlayer = 0;
                    // Reset active player
                    LinearLayout layout = findViewById(R.id.playAgainLayout);
                    // get the layout manager from res, xml file
                    layout.setVisibility(View.VISIBLE);  // set it to visible to display all components

                } // end if for game over

            }  // else if game over

        } // end of for loop for checking winner
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void playAgain(View view) {
        gameIsActive = true;
        activePlayer = 0;
        gameState = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2};

        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageResource(0);
        }
    }

    public void restartGame(View view) {
        gameIsActive = true;
        activePlayer = 0;
        gameState = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2};

        int player1Wins = 0;
        int player2Wins = 0;

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        String scoreText = String.format("%d : %d", player1Wins, player2Wins);
        scoreTextView.setText(scoreText);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageResource(0);
        }

        LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);
    }

}