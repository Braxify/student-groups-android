package com.chnulabs.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class StudentsGroupActivity extends AppCompatActivity {

    public static final String GROUP_NUMBER = "groupnumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_group2);

        Intent intent = getIntent();
        String grpNumber = intent.getStringExtra(GROUP_NUMBER);
        StudentsGroup group = StudentsGroup.getGroup(grpNumber);

        EditText txtGrpNumber = (EditText) findViewById(R.id.grpNumberEdit);
        txtGrpNumber.setText(group.getNumber());

        EditText txtFacultyName = (EditText) findViewById(R.id.facultyEdit);
        txtFacultyName.setText(group.getFacultyName());

        TextView txtImgGrp = (TextView) findViewById(R.id.grpNumberImageTxt);
        txtImgGrp.setText(group.getNumber());

        TextView txtImgFaculty = (TextView) findViewById(R.id.facultyNameImageTxt);
        txtImgFaculty.setText(group.getFacultyName());

        if (group.getEducationLevel() == 0) {
            ((RadioButton) findViewById(R.id.edu_level_bachelor)).setChecked(true);
        } else {
            ((RadioButton) findViewById(R.id.edu_level_master)).setChecked(true);
        }

        ((CheckBox) findViewById(R.id.contract_flg)).setChecked(group.isContractExistsFlg());
        ((CheckBox) findViewById(R.id.privilege_flg)).setChecked(group.isPrivilageExistsFlg());
    }

    public void onOkBtnClick(View view) {
        String outString = "Група " + ((TextView) findViewById(R.id.grpNumberEdit)).getText() + "\n";
        outString += "Факультет " + ((TextView) findViewById(R.id.facultyEdit)).getText() + "\n";

        if (((RadioButton) findViewById(R.id.edu_level_master)).isChecked()) {
            outString += "рівень освіти = " + "магістр\n";
        } else {
            outString += "рівень освіти = " + "бакалавр\n";
        }

        if (((CheckBox) findViewById(R.id.contract_flg)).isChecked()) {
            outString += "контрактники є\n";
        } else {
            outString += "контрактників нема\n";
        }

        if (((CheckBox) findViewById(R.id.privilege_flg)).isChecked()) {
            outString += "пільговики є\n";
        } else {
            outString += "пільговиків нема\n";
        }

        Toast.makeText(this, outString, Toast.LENGTH_LONG).show();
    }

    public void onBtnStudListClick(View view) {
        Intent localIntent = getIntent();
        String group = localIntent.getStringExtra(GROUP_NUMBER);

        Intent newIntent = new Intent(this, StudentsListActivity.class);
        newIntent.putExtra(StudentsListActivity.GROUP_NUMBER, group);
        startActivity(newIntent);
    }
}
