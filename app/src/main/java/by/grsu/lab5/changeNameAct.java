package by.grsu.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class changeNameAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        String txtName = getIntent().getStringExtra("name");

        if (txtName != null) {
            ((EditText)findViewById(R.id.name)).setText(txtName);
        }
    }
    public void onSave(View view){
        Intent intent = new Intent();
        intent.putExtra("nick", ((EditText)findViewById(R.id.name)).getText().toString());

        setResult(RESULT_OK, intent);
        this.finish();
    }
    public void onClose(View view){
        this.finish();
    }
    @Override
    public void finish(){
        super.finish();
    }

}