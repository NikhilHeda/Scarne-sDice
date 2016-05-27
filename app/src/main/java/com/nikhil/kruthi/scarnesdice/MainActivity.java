package com.nikhil.kruthi.scarnesdice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    TextView yourScore, compScore, turnScore, status;
    Button roll, hold, reset;
    ImageView dice;

    int images[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    Random rand = new Random();

    private int userOverall = 0;
    private int userTurn = 0;
    private int computerOverall = 0;
    private int computerTurn = 0;

    private final Handler handler = new Handler();
    private Runnable ct = new Runnable() {
        @Override
        public void run() {
            computerTurn();
        }
    };

    private int compCount = 0;

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        yourScore = (TextView) findViewById(R.id.tvYourScore);
        compScore = (TextView) findViewById(R.id.tvComputerScore);
        turnScore = (TextView) findViewById(R.id.tvTurnScore);
        status = (TextView) findViewById(R.id.tvStatus);
        dice = (ImageView) findViewById(R.id.ivDice);
        roll = (Button) findViewById(R.id.bRoll);
        hold = (Button) findViewById(R.id.bHold);
        reset = (Button) findViewById(R.id.bReset);

        roll.setOnClickListener(this);
        hold.setOnClickListener(this);
        reset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bRoll:
                roll();
                break;
            case R.id.bHold:
                hold();
                break;
            case R.id.bReset:
                reset();
                break;
        }
        checkWin();

    }

    private void roll() {
        int r = rand.nextInt(images.length) + 1;
        dice.setImageResource(images[r - 1]);
        if (r != 1) {
            userTurn += r;
            turnScore.setText("Player Turn Score : " + Integer.toString(userTurn));
        } else {
            userTurn = 0;
            turnScore.setText("Player Turn Score : 0");
            compCount = 0;
            flag = true;
            status.setText("Computer's Turn");
            roll.setEnabled(false);
            hold.setEnabled(false);
            handler.postDelayed(ct, 1000);
        }
    }

    private void hold() {
        userOverall += userTurn;
        userTurn = 0;
        yourScore.setText("Your Score : " + Integer.toString(userOverall));
        compCount = 0;
        flag = true;
        status.setText("Computer's Turn");
        roll.setEnabled(false);
        hold.setEnabled(false);
        handler.postDelayed(ct, 1000);
    }

    private void reset() {
        userTurn = 0;
        userOverall = 0;
        computerTurn = 0;
        computerOverall = 0;
        yourScore.setText("Your Score : 0");
        compScore.setText("Computer Score : 0");
        turnScore.setText("Turn Score : 0");
        status.setText("Player's Turn");
        handler.removeCallbacks(ct);
        roll.setEnabled(true);
        hold.setEnabled(true);
    }

    private void computerTurn() {
        int r = rand.nextInt(images.length) + 1;
        dice.setImageResource(images[r - 1]);
        if (r != 1) {
            computerTurn += r;
            turnScore.setText("Computer Turn Score : " + Integer.toString(computerTurn));
        } else {
            computerTurn = 0;
            turnScore.setText("Computer Turn Score : 0");
            flag = false;
        }

        if (compCount++ < 8 && flag) {
            handler.postDelayed(ct, 1500);
        } else {
            status.setText("Player's Turn");
            computerOverall += computerTurn;
            computerTurn = 0;
            compScore.setText("Computer Score : " + Integer.toString(computerOverall));
            roll.setEnabled(true);
            hold.setEnabled(true);
            checkWin();
        }
    }

    private void checkWin() {
        if (userOverall >= 50) {
            status.setText("Player Wins !");
            roll.setEnabled(false);
            hold.setEnabled(false);
            handler.removeCallbacks(ct);
        } else if (computerOverall >= 50) {
            status.setText("Computer Wins !");
            roll.setEnabled(false);
            hold.setEnabled(false);
            handler.removeCallbacks(ct);
        }
    }

}
