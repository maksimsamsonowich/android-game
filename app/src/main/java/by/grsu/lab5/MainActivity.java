package by.grsu.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
    private int attempts = 5;
    private int lengthOfNumber = 2;
    private int comp_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i ("MainActivity", "Угадай число. onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setComplexity(View view) {
        onCreateDialog().show();
    }

    public Dialog onCreateDialog() {
        Log.i ("MainActivity", "Угадай число. onCreateDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialogTitle)
                .setMessage(R.string.dialogMsg)
                .setPositiveButton("2-значное", (dialog, id) -> {
                    attempts = 5;
                    lengthOfNumber = 2;
                    ((TextView)findViewById(R.id.showMsg)).setText(R.string._2);
                    ((TextView)findViewById(R.id.showAttemptsLeft)).setText(attempts);
                    comp_num = guessNum.rnd_comp_num(lengthOfNumber);
                    Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num);
                    dialog.cancel();
                })
                .setPositiveButton("3х-значное", (dialog, which) -> {
                    attempts = 7;
                    lengthOfNumber = 3;
                    ((TextView)findViewById(R.id.showMsg)).setText(R.string._3);
                    ((TextView)findViewById(R.id.showAttemptsLeft)).setText(attempts);
                    comp_num = guessNum.rnd_comp_num(lengthOfNumber);
                    Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num);
                    dialog.cancel();
                })
                .setPositiveButton("4х-значное", (dialog, which) -> {
                    attempts = 10;
                    lengthOfNumber = 4;
                    ((TextView)findViewById(R.id.showMsg)).setText(R.string._4);
                    ((TextView)findViewById(R.id.showAttemptsLeft)).setText(attempts);
                    Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num);
                    comp_num = guessNum.rnd_comp_num(lengthOfNumber);
                    dialog.cancel();
                });
        
        
        return builder.create();
    }

    public void guess(View view) {
        if (Integer.parseInt(((EditText) findViewById(R.id.txt_hint)).getText().toString()) == comp_num) {
            Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num + ". Победа.");
            Toast t = Toast.makeText(this,"Поздравляю!",Toast.LENGTH_LONG);
            t.show();

            ((Button)findViewById(R.id.guessButton)).setClickable(false);

        } else {
            ((EditText) findViewById(R.id.txt_hint)).setText("");
            attempts--;
            if (attempts != 0) {
                ((TextView) findViewById(R.id.showAttemptsLeft)).setText(attempts);
                Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num + ". Неудача.");
                if (comp_num < Integer.parseInt(((EditText) findViewById(R.id.txt_hint)).getText().toString())) {
                    ((TextView)findViewById(R.id.showMsg)).setText(R.string.numIsLower);
                } else {
                    ((TextView)findViewById(R.id.showMsg)).setText(R.string.numIsHigher);
                }

            } else {
                Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num + ". Проигрыш.");
                Toast t = Toast.makeText(this, "Looser!", Toast.LENGTH_LONG);
                t.show();

                ((Button)findViewById(R.id.guessButton)).setClickable(false);

            }
        }
    }

    public void restart(View view) {
        Log.i ("MainActivity", "Рестарт.");
        ((Button)findViewById(R.id.guessButton)).setClickable(true);
        onCreateDialog().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i ("MainActivity", "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i ("MainActivity", "onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i ("MainActivity", "onResume()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        attempts = savedInstanceState.getInt("att");
        comp_num = savedInstanceState.getInt("comp");
        lengthOfNumber = savedInstanceState.getInt("length");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i ("MainActivity", "onSaveInstanceState(@NonNull Bundle outState)");
        outState.putInt("att", attempts);
        outState.putInt("comp", comp_num);
        outState.putInt("length", lengthOfNumber);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i ("MainActivity", "onRestart()");
    }
}

class guessNum {

    static public int rnd_comp_num(int lenght) {
        int min = 10;
        int max = 99;

        if (lenght == 2) {
            min = 10;
            max = 99;
        } else if (lenght == 3) {

            min = 100;
            max = 999;
        } else if (lenght == 4) {

            min = 1000;
            max = 9999;
        } else {
            return 0;
        }

        int diff = max - min;
        Random random = new Random();
        return random.nextInt(diff + 1) + min;
    }
}