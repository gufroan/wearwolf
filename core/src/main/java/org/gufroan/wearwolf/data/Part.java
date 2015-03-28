package org.gufroan.wearwolf.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Part implements Parcelable {

//    private int id;

    private String stringData;

    public Part() {
    }

    public Part(final String stringData) {
        this.stringData = stringData;
    }

//    public Part(final int id, final String stringData) {
//        this.id = id;
//        this.stringData = stringData;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    @Override
    public int describeContents() {
        return stringData.hashCode();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(stringData);
    }

    public static final Parcelable.Creator<Part> CREATOR = new Parcelable.Creator<Part>() {
        public Part createFromParcel(Parcel in) {
            return new Part(in);
        }

        public Part[] newArray(int size) {
            return new Part[size];
        }
    };

    private Part(Parcel in) {
        stringData = in.readString();
    }
}
