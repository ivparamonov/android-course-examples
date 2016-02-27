package ru.ac.uniyar.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import static java.util.Calendar.*;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoEditorActivity extends Activity {
    @Bind(R.id.titleText) EditText titleText;
    @Bind(R.id.descriptionText) EditText descriptionText;
    @Bind(R.id.dueDatePicker) DatePicker dueDatePicker;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_editor);
        ButterKnife.bind(this);
        Todo todo = getIntent().getParcelableExtra("todo");
        if (todo != null) {
            titleText.setText(todo.getTitle());
            descriptionText.setText(todo.getDescription());
            Calendar calendar = todo.getDueDateAsCalendar();
            dueDatePicker.init(calendar.get(YEAR), calendar.get(MONTH),
                    calendar.get(DAY_OF_MONTH), null);
        }
    }

    public void onOkButtonClick(View v) {
        Todo todo = getIntent().getParcelableExtra("todo");
        if (todo == null) todo = new Todo();
        todo.setTitle(titleText.getText().toString());
        todo.setDescription(descriptionText.getText().toString());
        todo.setDueDate(dueDatePicker.getYear(),
                dueDatePicker.getMonth(), dueDatePicker.getDayOfMonth());
        Intent resultIntent = new Intent();
        resultIntent.putExtra("todo", todo);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onCancelButtonClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
