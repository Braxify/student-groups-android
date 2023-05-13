package com.chnulabs.students;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroupsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StudentsGroup group = (StudentsGroup) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(GroupsListActivity.this,
                        StudentsGroupActivity.class);
                intent.putExtra(StudentsGroupActivity.GROUP_NUMBER, group.getId());
                startActivity(intent);
            }
        };

        ListView listView = findViewById(R.id.groups_list);
        listView.setOnItemClickListener(listener);

        ArrayAdapter<StudentsGroup> adapter = new ArrayAdapter<StudentsGroup>(
                this,
                android.R.layout.simple_list_item_1,
                getDataFromDB()
        );
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getDataFromDB();

        ListView listView = findViewById(R.id.groups_list);
        ArrayAdapter<StudentsGroup> adapter = new ArrayAdapter<StudentsGroup>(
                this,
                android.R.layout.simple_list_item_1,
                // StudentsGroup.getGroups()
                getDataFromDB()
        );
        listView.setAdapter(adapter);
    }

    private ArrayList<StudentsGroup> getDataFromDB() {
        ArrayList<StudentsGroup> groups = new ArrayList<StudentsGroup>();

        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("groups",
                    new String[]{"number", "facultyName", "educationLevel",
                            "contractExistsFlg", "privilageExistsFlg", "id"},
                    null, null, null,
                    null, "number");
            while (cursor.moveToNext()) {
                groups.add(
                        new StudentsGroup(
                                cursor.getInt(5),
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                (cursor.getInt(3) > 0),
                                (cursor.getInt(4) > 0)
                        )
                );
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return groups;
    }

    public void onGrpAddClick(View view) {
        startActivity(new Intent(this, AddStudentsGroupActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.groups_menu, menu);

        String text = "";
        for (StudentsGroup group : StudentsGroup.getGroups()) {
            text += group.getNumber() + "\n";
        }

        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_group) {
            startActivity(new Intent(this, AddStudentsGroupActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
