package by.grsu.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private int mode = 2;
    private int attempts = 5;
    private int lengthOfNumber = 2;
    private int comp_num = 0;
    final int REQUEST_CODE = 1;
    public static String NICK_MES = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i ("MainActivity", "Угадай число. onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //comp_num = guessNum.rnd_comp_num(lengthOfNumber);
    }

    public void setComplexity(View view) {
        onCreateDialog().show();
    }

    public Dialog onCreateDialog() {
        Log.i ("MainActivity", "Угадай число. onCreateDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switch (mode) {
                    case 3:
                        attempts = 7;
                        ((TextView)findViewById(R.id.showMsg)).setText(R.string._3);
                        ((TextView)findViewById(R.id.showAttemptsLeft)).setText("         " + attempts);
                        break;
                    case 4:
                        attempts = 10;
                        ((TextView)findViewById(R.id.showMsg)).setText(R.string._4);
                        ((TextView)findViewById(R.id.showAttemptsLeft)).setText("         " + attempts);
                        break;
                    default:
                        attempts = 5;
                        ((TextView)findViewById(R.id.showMsg)).setText(R.string._2);
                        ((TextView)findViewById(R.id.showAttemptsLeft)).setText("         " + attempts);
                        break;
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setSingleChoiceItems(R.array.diaps_array, mode-2, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 1:
                        mode = 3;
                        comp_num = guessNum.rnd_comp_num(3);
                        break;
                    case 2:
                        mode = 4;
                        comp_num = guessNum.rnd_comp_num(4);
                        break;
                    default:
                        mode = 2;
                        comp_num = guessNum.rnd_comp_num(2);
                        break;
                }

                // The 'which' argument contains the index position of the selected item
            }
        });

        
        return builder.create();
    }

    public void guess(View view) {
        int userAnswer = Integer.parseInt(((EditText) findViewById(R.id.name)).getText().toString());
        if (userAnswer == comp_num) {
            Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num + ". Победа.");
            Toast t = Toast.makeText(this,"Поздравляю!",Toast.LENGTH_LONG);
            t.show();

            ((Button)findViewById(R.id.guessButton)).setClickable(false);

        } else {
            ((EditText) findViewById(R.id.name)).setText("");
            attempts--;
            if (attempts != 0) {
                ((TextView) findViewById(R.id.showAttemptsLeft)).setText(Integer.toString(attempts));
                Log.d ("MainActivity", "Выбрана длина числа " + lengthOfNumber + ". Загаданное число " + comp_num + ". Неудача.");
                if (comp_num < userAnswer) {
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

    public void save(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        String shareBody = "Nickname: "
                + ((TextView)findViewById(R.id.nameText)).getText().toString() +
                "\nNumber: " + Integer.toString(comp_num) + "\nAttempts left: " + attempts;
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Game result");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Save to"));
    }

    public void restart(View view) {
        Log.i ("MainActivity", "Рестарт.");
        ((Button)findViewById(R.id.guessButton)).setClickable(true);
        onCreateDialog().show();
    }

    public void goToAct(View view) {
        Intent intent = new Intent(this, changeNameAct.class);

        intent.putExtra(NICK_MES, ((TextView)findViewById(R.id.nameText)).getText().toString());

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("MainActivity", "onActivityResult()");

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_CODE: String name = data.getStringExtra("nick");
                    ((TextView)findViewById(R.id.nameText)).setText(name);
                    break;
                default:
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
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