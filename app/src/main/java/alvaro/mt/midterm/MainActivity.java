package alvaro.mt.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView disprock, dispScissor, disppaper, disphand;
    Button btnplay, btnrecord;
    TextView txtwins, txtlose;
    int roll;
    int win = 0;
    int lose = 0;
    Random random;
    Boolean rock = false, scissor = false, paper = false;
    Database Conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtwins = (TextView) findViewById(R.id.txtwins);
        txtlose = (TextView) findViewById(R.id.txtloses);

        Conn = new Database(this);
        win = Conn.getWins();
        lose = Conn.getLoss();

        txtlose.setText(String.valueOf(lose));
        txtwins.setText(String.valueOf(win));

        disphand = (ImageView) findViewById(R.id.disphand);
        disprock = (ImageView) findViewById(R.id.Disprock);
        dispScissor = (ImageView) findViewById(R.id.Dispscissor);
        disppaper = (ImageView) findViewById(R.id.disppaper);
        btnplay = (Button) findViewById(R.id.btnplay);
        btnrecord =(Button) findViewById(R.id.btnrecords);

        random = new Random();

        disprock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disprock.setBackgroundColor(Color.BLUE);
                dispScissor.setBackgroundColor(Color.TRANSPARENT);
                disppaper.setBackgroundColor(Color.TRANSPARENT);
                rock = true;
                scissor = false;
                paper = false;
            }
        });

        dispScissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispScissor.setBackgroundColor(Color.BLUE);
                disprock.setBackgroundColor(Color.TRANSPARENT);
                disppaper.setBackgroundColor(Color.TRANSPARENT);
                scissor = true;
                rock = false;
                paper = false;
            }
        });

        disppaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disppaper.setBackgroundColor(Color.BLUE);
                dispScissor.setBackgroundColor(Color.TRANSPARENT);
                disprock.setBackgroundColor(Color.TRANSPARENT);
                paper = true;
                rock = false;
                scissor = false;
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = 0;
                if(rock||paper||scissor) {
                    roll = random.nextInt(3);
                    //int res = 0;
                    String cpu = "";
                    switch (roll) {
                        case 0:
                            cpu = "Paper";
                            res = R.drawable.paper1;
                            break;
                        case 1:
                            cpu = "Rock";
                            res = R.drawable.rock2;
                            break;
                        case 2:
                            cpu = "Scissor";
                            res = R.drawable.scissor3;
                            break;
                    }
                    disphand.setBackgroundColor(Color.TRANSPARENT);
                    disphand.setImageResource(res);
                    int result = whoseTheWinner(roll);
                    String ply = rock ? "Rock" : paper ? "Paper" : scissor ? "Scissor" : "";
                    if (result == 0) {
                        lose++;
                        Conn.insertHistory(ply, cpu, "CPU");
                        Toast.makeText(MainActivity.this, "You lost!", Toast.LENGTH_SHORT).show();
                    } else if (result == 1) {
                        win++;
                        Conn.insertHistory(ply, cpu, "Player");
                        Toast.makeText(MainActivity.this, "You won!", Toast.LENGTH_SHORT).show();
                    } else {
                        Conn.insertHistory(ply, cpu, "Draw");
                        Toast.makeText(MainActivity.this, "Draw!", Toast.LENGTH_SHORT).show();
                    }
                    txtwins.setText(String.valueOf(win));
                    txtlose.setText(String.valueOf(lose));
                }
                else {
                    Toast.makeText(MainActivity.this, "Pick one of the three images.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        btnrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".RecordActivity");
                startActivity(intent);
            }
        });
    }

    public int whoseTheWinner(int cpu) {
        // return 1 player won 0 cpu -1 draw
        Log.d("TEST", String.valueOf(cpu));
        Log.d("TEST", String.valueOf(rock));
        Log.d("TEST", String.valueOf(paper));
        Log.d("TEST", String.valueOf(scissor));
        switch (cpu) {
            case 0: // papel
                if (scissor) {
                    return 1;
                } else if (rock) {
                    return 0;
                }
                return -1;
            case 1: // rock
                if (paper) {
                    return 1;
                } else if (scissor) {
                    return 0;
                }
                return -1;
            case 2: // scissor
                if (rock) {
                    return 1;
                } else if (paper) {
                    return 0;
                }
                return -1;
        }
        return -1;
    }
}