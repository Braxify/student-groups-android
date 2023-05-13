package com.chnulabs.students;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class AddStudentsGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students_group);
    }

    public void onGrpAddClick(View view) {
        EditText number = (EditText) findViewById(R.id.addGroupNumber);
        EditText faculty = (EditText) findViewById(R.id.addFaculty);

        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("number", number.getText().toString());
            contentValues.put("facultyName", faculty.getText().toString());
            contentValues.put("educationLevel", 0);
            contentValues.put("contractExistsFlg", 0);
            contentValues.put("privilageExistsFlg", 0);
            db.insert("Groups", null, contentValues);
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
