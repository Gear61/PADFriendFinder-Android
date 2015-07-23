package com.randomappsinc.padfriendfinder.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexanderchiou on 7/23/15.
 */
public class RestCallResponse implements Parcelable
{
    private int statusCode;
    private String response;

    public RestCallResponse()
    {
        this.response = "";
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

    protected RestCallResponse(Parcel in) {
        statusCode = in.readInt();
        response = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeString(response);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RestCallResponse> CREATOR = new Parcelable.Creator<RestCallResponse>() {
        @Override
        public RestCallResponse createFromParcel(Parcel in) {
            return new RestCallResponse(in);
        }

        @Override
        public RestCallResponse[] newArray(int size) {
            return new RestCallResponse[size];
        }
    };
}