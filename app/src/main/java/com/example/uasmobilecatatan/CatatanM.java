package com.example.uasmobilecatatan;

import android.os.Parcel;
import android.os.Parcelable;

public class CatatanM implements Parcelable {
    private String catatan;
    private String CatatanID;

    public CatatanM(){

    }

    protected CatatanM(Parcel in) {
        catatan = in.readString();
        CatatanID = in.readString();
    }

    public static final Creator<CatatanM> CREATOR = new Creator<CatatanM>() {
        @Override
        public CatatanM createFromParcel(Parcel in) {
            return new CatatanM(in);
        }

        @Override
        public CatatanM[] newArray(int size) {
            return new CatatanM[size];
        }
    };

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getCatatanID() {
        return CatatanID;
    }

    public void setCatatanID(String catatanID) {
        this.CatatanID = catatanID;
    }

    public CatatanM(String catatanID, String catatan) {
        this.catatan = catatan;
        this.CatatanID = catatanID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(catatan);
        parcel.writeString(CatatanID);
    }
}
