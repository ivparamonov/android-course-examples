package ru.ac.uniyar.todolist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.todoList) ListView todoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ((TodoListApplication) getApplication()).getDatabase().execSQL(
                "insert into todos values (null, 'Todo', 'Description', '2016-03-01')");
        initListAdapter();
    }

    private void initListAdapter() {
        Cursor cursor = ((TodoListApplication) getApplication()).getDatabase()
                .query("todos", null, null, null, null, null, "dueDate");
        String[] from = new String[] { "title", "description" };
        int[] to = new int[] { R.id.titleText, R.id.descriptionText };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.todo_item, cursor, from, to,
                CursorAdapter.FLAG_AUTO_REQUERY);
        todoListView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void onFABClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
    }
}
