package com.intercorp.experis.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intercorp.experis.R;
import com.intercorp.experis.model.entities.User;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etUserName;
    TextInputEditText etAge;
    Button btNext;
    DatabaseReference mDatabase;
    TextView tvDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        etUserName = findViewById(R.id.etUserName);
        etAge = findViewById(R.id.etAge);
        btNext = findViewById(R.id.btNext);
        tvDate = findViewById(R.id.tvDate);
        User user = getIntent().getParcelableExtra("userApp");

        etUserName.setText(user.getName());
        btNext.setOnClickListener(v -> {
            user.setAge(etAge.getText().toString());
            user.setDateTime(tvDate.getTag() == null ? "" : (String) tvDate.getTag());
            saveRealTimeDatabase(user);
        });

        tvDate.setOnClickListener(v -> {
            datePicker();
        });

    }


    private void saveRealTimeDatabase(User user) {
        mDatabase.child("users").child(user.getId()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {

                    }
                });
    }

    private void datePicker() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Seleccione su fecha de nacimiento");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    tvDate.setText(materialDatePicker.getHeaderText());
                    tvDate.setTag(materialDatePicker.getHeaderText());
                }
        );


    }
}
