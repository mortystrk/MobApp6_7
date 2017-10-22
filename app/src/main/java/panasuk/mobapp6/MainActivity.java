package panasuk.mobapp6;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import dialog.Dialog;

public class MainActivity extends AppCompatActivity {

    EditText name, surname, age;
    Intent previousIntent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.editText);
        surname = (EditText) findViewById(R.id.editText2);
        age = (EditText) findViewById(R.id.editText3);

        previousIntent = getIntent();

        name.setText(previousIntent.getStringExtra("name"));
        surname.setText(previousIntent.getStringExtra("surname"));
        age.setText(previousIntent.getStringExtra("age"));
        SetType();
    }

    public void SetType(){
            type = previousIntent.getStringExtra("type");
            if(type == null){
                type = "default";
            }
    }

    public void onNext(View view){

        if(!CheckValues()){
            AlertDialog dialog = Dialog.CreateDialog(R.string.incorrect_values, R.string.ok, this);
            dialog.show();
            return;
        }
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("surname", surname.getText().toString());
        intent.putExtra("age", age.getText().toString());
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public boolean CheckValues(){
        if(name.getText().toString().trim().isEmpty() ||
                surname.getText().toString().trim().isEmpty() ||
                age.getText().toString().trim().isEmpty()){
            return false;
        }
        return true;
    }
}
