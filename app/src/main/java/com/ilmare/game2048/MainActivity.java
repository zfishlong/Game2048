package com.ilmare.game2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ilmare.game2048.View.GameView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.score)
    TextView scoreView;
    @InjectView(R.id.cardGrid)
    GameView gameView;

    public static MainActivity mainActivity = null;
    @InjectView(R.id.restart)
    Button restart;

    private int Score = 0;

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        restart.setOnClickListener(this);
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }


    public void clearScore() {
        this.Score = 0;
        scoreView.setText("得分：" + Score);

    }


    public void addScore(int score) {
        this.Score += score;
        scoreView.setText("得分：" + Score);
    }

    @Override
    public void onClick(View v) {
        gameView.startGame();
    }
}
