package com.chnulabs.students;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentsListActivity extends AppCompatActivity {

    public static final String GROUP_NUMBER = "groupnumber";
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER, 0);

        ListView listView = (ListView) findViewById(R.id.studentsList);

        SimpleCursorAdapter adapter = getDataFromDB(grpNumber);
        if (adapter != null) {
            listView.setAdapter(adapter);
        }
    }

    private SimpleCursorAdapter getDataFromDB(int groupId) {
        SimpleCursorAdapter listAdapter = null;

        SQLiteOpenHelper sqliteHelper = new StudentsDatabaseHelper(this);
        try {
            db = sqliteHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT s.id _id, name, number\n" +
                    "FROM Students s INNER JOIN Groups g on s.group_id= g.id\n" +
                    "WHERE g.id = ?", new String[]{Integer.toString(groupId)});
            listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor, new String[]{"name"},
                    new int[]{android.R.id.text1}, 0
            );
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return listAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    public void onSendBtnClick(View view) {
    //        TextView textView = (TextView) findViewById(R.id.text);
    //
    //        Intent intent = new Intent(Intent.ACTION_SEND);
    //        intent.setType("text/plain");
    //        intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
    //        intent.putExtra(Intent.EXTRA_SUBJECT, "Список студентів");
    //        startActivity(intent);
    }
}
