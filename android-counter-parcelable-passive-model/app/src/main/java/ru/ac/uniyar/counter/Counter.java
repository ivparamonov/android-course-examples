package ru.ac.uniyar.counter;

import android.os.Parcel;
import android.os.Parcelable;

public class Counter implements Parcelable {
    private int value;
    public Counter() {
        value = 0;
    }
    public int getValue() {
        return value;
    }
    public void increase() {
        value++;
    }
    public void reset() {
        value = 0;
    }

    // Parcelable stuff below

    protected Counter(Parcel in) {
        value = in.readInt();
    }

    public static final Creator<Counter> CREATOR = new Creator<Counter>() {
        @Override
        public Counter createFromParcel(Parcel in) {
            return new Counter(in);
        }

        @Override
        public Counter[] newArray(int size) {
            return new Counter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }
}
