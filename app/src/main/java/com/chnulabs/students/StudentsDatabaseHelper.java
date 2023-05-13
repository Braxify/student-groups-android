package com.chnulabs.students;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "students";
    private static final int DB_VERSION = 2;

    public StudentsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlText = "CREATE TABLE Groups (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "number TEXT(10) NOT NULL,\n" +
                "facultyName TEXT(100),\n" +
                "educationLevel INTEGER,\n" +
                "contractExistsFlg BOOLEAN,\n" +
                "privilageExistsFlg BOOLEAN\n" +
                ");";
        sqLiteDatabase.execSQL(sqlText);

        updateSchema(sqLiteDatabase, 0);

        populateDB(sqLiteDatabase);
    }

    private void populateDB(SQLiteDatabase db) {
        populateGroups(db);
        populateStudents(db);
    }

    private void populateGroups(SQLiteDatabase db) {
        for (StudentsGroup group : StudentsGroup.getGroups()) {
            insertRowToGroup(db, group);
        }
    }

    private void populateStudents(SQLiteDatabase db) {
        for (Student student : Student.getStudents()) {
            insertRowToStudent(db, student);
        }
    }

    private void insertRowToGroup(SQLiteDatabase db, StudentsGroup group) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", group.getNumber());
        contentValues.put("facultyName", group.getFacultyName());
        contentValues.put("educationLevel", group.getEducationLevel());
        contentValues.put("contractExistsFlg", group.isContractExistsFlg());
        contentValues.put("privilageExistsFlg", group.isPrivilageExistsFlg());
        db.insert("Groups", null, contentValues);
    }

    private void insertRowToStudent(SQLiteDatabase db, Student student) {
        db.execSQL("INSERT INTO Students (name, group_id)\n" +
                "SELECT '" + student.getName() + "', id\n" +
                "FROM Groups\n" +
                "WHERE number='" + student.getGroupNumber() + "'");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        updateSchema(sqLiteDatabase, oldV);
    }

    private void updateSchema(SQLiteDatabase db, int oldV) {
        if (oldV < 2) {
            db.execSQL("CREATE TABLE Students (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name TEXT(100) NOT NULL,\n" +
                    "group_id INTEGER REFERENCES Groups (id) ON DELETE RESTRICT\n" +
                    "ON UPDATE RESTRICT\n" +
                    ");");
            populateStudents(db);
        }
    }
}
