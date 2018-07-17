package com.mobdice.indianexpress.network_call;

import android.os.Parcel;
import android.os.Parcelable;

public class SoapProperties implements Parcelable {
    private final String property;
    private final String value;

    SoapProperties(String property, String value) {
        this.property = property;
        this.value = value;
    }


    protected SoapProperties(Parcel in) {
        property = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(property);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SoapProperties> CREATOR = new Creator<SoapProperties>() {
        @Override
        public SoapProperties createFromParcel(Parcel in) {
            return new SoapProperties(in);
        }

        @Override
        public SoapProperties[] newArray(int size) {
            return new SoapProperties[size];
        }
    };

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }
}