package ru.ac.uniyar.todolist;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ToDoContentProvider extends ContentProvider {
    private DBHelper dbHelper;

    private static final String AUTHORITY = "ru.ac.uniyar.todolist.contentprovider";
    private static final String BASE_PATH = "todos";
    private static final int TODOS = 10;
    private static final int TODO_ID = 20;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH) {{
        addURI(AUTHORITY, BASE_PATH, TODOS);
        addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
    }};

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("todos");
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TODOS:
                break;
            case TODO_ID:
                queryBuilder.appendWhere("_id = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case TODOS:
                id = sqlDB.insert("todos", null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated;
        switch (uriType) {
            case TODOS:
                rowsUpdated = sqlDB.update("todos", values, selection, selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update("todos", values, "_id =" + id, null);
                } else {
                    rowsUpdated = sqlDB.update("todos", values, "_id =" + id +
                            " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
