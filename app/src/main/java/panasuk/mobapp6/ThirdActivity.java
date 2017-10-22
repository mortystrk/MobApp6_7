package panasuk.mobapp6;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import db.DBHelp;
import dialog.Dialog;

public class ThirdActivity extends AppCompatActivity {


    TextView name, surname, age, type, nameDB, surnameDB, ageDB, typeDB;
    EditText search;
    String namePerson, surnamePerson, typeOfPerson;
    int ageOfPerson;
    Intent intent;
    DBHelp dbHelp;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        name = (TextView) findViewById(R.id.nameView);
        surname = (TextView) findViewById(R.id.surnameView);
        age = (TextView) findViewById(R.id.ageView);
        type = (TextView) findViewById(R.id.typeView);
        nameDB = (TextView) findViewById(R.id.nameDB);
        surnameDB = (TextView) findViewById(R.id.surnameDB);
        ageDB = (TextView) findViewById(R.id.ageDB);
        typeDB = (TextView) findViewById(R.id.typeDB);
        search = (EditText) findViewById(R.id.search);

        intent = getIntent();
        name.append(intent.getStringExtra("name"));
        surname.append(intent.getStringExtra("surname"));
        age.append(intent.getStringExtra("age"));
        type.append(intent.getStringExtra("type"));

        dbHelp = new DBHelp(this);
        database = dbHelp.getWritableDatabase();
    }

    public void onBack(View view){
        intent.setClass(this, SecondActivity.class);
        startActivity(intent);
    }

    public void onSave(View view){

        namePerson = intent.getStringExtra("name");
        surnamePerson = intent.getStringExtra("surname");
        typeOfPerson = intent.getStringExtra("type");
        ageOfPerson = Integer.parseInt(intent.getStringExtra("age"));

        ContentValues values = new ContentValues();
        values.put("name", namePerson);
        values.put("surname", surnamePerson);
        values.put("age", ageOfPerson);
        values.put("type", typeOfPerson);
        long rowID = database.insert("Members", null, values);
        Log.d("Lab_6-7", "rowID: " + rowID);

        AlertDialog dialog = Dialog.CreateDialog(R.string.insert_values, R.string.ok, this);
        dialog.show();
    }

    public boolean CheckEntries(){
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        Cursor cursor = database.query("Members", null, null, null, null, null, null);
        if(cursor.getCount() == 0){
            return false;
        }
        return true;
    }

    private AlertDialog CreateDialog(int message, int textOnPositiveButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton(textOnPositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }

    public void onGetData(View view){

        if(!CheckEntries()){
            nameDB.setText("Записей нет");
            return;
        }

        nameDB.setText("");
        surnameDB.setText("");
        ageDB.setText("");
        typeDB.setText("");

        SQLiteDatabase database = dbHelp.getWritableDatabase();
        Cursor cursor = null;

        String[] columns = new String[] {"name", "surname", "age", "type"};
        String selection = "name == ?";
        String[] selectionArgs = new String[] {search.getText().toString()};

        cursor = database.query("Members", columns, selection, selectionArgs, null, null, null);

        if(cursor.getCount() == 0){
            AlertDialog dialog = CreateDialog(R.string.name_not_found, R.string.ok);
            dialog.show();
            return;
        }

        cursor.moveToFirst();
        nameDB.setText("name: " + cursor.getString(0));
        surnameDB.append("surname: " + cursor.getString(1));
        ageDB.append("age: " + cursor.getString(2));
        typeDB.append("type: " + cursor.getString(3));
        cursor.close();
    }
}
