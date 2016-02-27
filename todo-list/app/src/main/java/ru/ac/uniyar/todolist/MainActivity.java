package ru.ac.uniyar.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.todoList) ListView todoListView;
    SimpleCursorAdapter adapter;
    Cursor todoListCursor;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        db = ((TodoListApplication) getApplication()).getDatabase();
        initListAdapter();
    }

    private void initListAdapter() {
        todoListCursor = db.query("todos", null, null, null, null, null, "dueDate");
        String[] from = new String[] { "title", "description" };
        int[] to = new int[] { R.id.titleText, R.id.descriptionText };
        adapter = new SimpleCursorAdapter(this,
                R.layout.todo_item, todoListCursor, from, to,
                CursorAdapter.FLAG_AUTO_REQUERY);
        todoListView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void onFABClick(View view) {
        Intent intent = new Intent(this, ToDoEditorActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnItemClick(R.id.todoList)
    public void onListItemClicked(int position) {
        String itemId = String.valueOf(adapter.getItemId(position));
        Cursor cursor = db.query("todos", null,
                "_id = ?", new String[] { itemId }, null, null, null);
        cursor.moveToNext();
        Todo todo = new Todo(
                Integer.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("description")),
                cursor.getString(cursor.getColumnIndex("dueDate")));
        cursor.close();
        Intent intent = new Intent(this, ToDoEditorActivity.class);
        intent.putExtra("todo", todo);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode != RESULT_OK) return;
            Todo todo = data.getParcelableExtra("todo");
            ContentValues cv = new ContentValues();
            cv.put("title", todo.getTitle());
            cv.put("description", todo.getDescription());
            cv.put("dueDate", todo.getDueDate());
            if (todo.isNew()) {
                db.insert("todos", null, cv);
            } else {
                db.update("todos", cv, "_id = ?",
                        new String[] { String.valueOf(todo.getId()) });
            }
            todoListCursor.requery();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
