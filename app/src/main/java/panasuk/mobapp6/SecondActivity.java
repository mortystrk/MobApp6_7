package panasuk.mobapp6;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.content.Intent;
import android.widget.Switch;

import dialog.Dialog;

public class SecondActivity extends AppCompatActivity {

    String type, name, surname, age, intentType;
    Intent previousIntent;
    RadioButton rStudent, rListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        previousIntent = getIntent();

        intentType = previousIntent.getStringExtra("type");
        name = previousIntent.getStringExtra("name");
        surname = previousIntent.getStringExtra("surname");
        age = previousIntent.getStringExtra("age");
        rStudent = (RadioButton) findViewById(R.id.radioStudent);
        rListener = (RadioButton) findViewById(R.id.radioListener);

        CheckType();
    }

    public void CheckType(){
        RadioButton rb;

        switch (intentType){
            case "Listener":
                rb = (RadioButton) findViewById(R.id.radioListener);
                rb.setChecked(true);
                type = "Listener";
                break;
            case "Student":
                rb = (RadioButton) findViewById(R.id.radioStudent);
                rb.setChecked(true);
                type = "Student";
                break;
            default:
                type = null;
                break;
        }
    }

    public void onRadioButtonClicked(View view){

        boolean cheked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radioListener:
                if(cheked){
                    type = "Listener";
                }
                break;
            case R.id.radioStudent:
                if(cheked){
                    type = "Student";
                }
                break;
        }
    }

    public void onBack(View view){
        previousIntent.putExtra("type", type);
        Intent intent = previousIntent;
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void onNext(View view){

        if(!rStudent.isChecked() && !rListener.isChecked()){
            AlertDialog dialog = Dialog.CreateDialog(R.string.not_type, R.string.ok, this);
            dialog.show();
            return;
        }

        Intent currentIntent = new Intent(this, ThirdActivity.class);

        currentIntent.putExtra("name", name);
        currentIntent.putExtra("surname", surname);
        currentIntent.putExtra("age", age);
        currentIntent.putExtra("type", type);

        startActivity(currentIntent);
    }
}
