package com.example.projekt3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PostepInfo implements Parcelable {
    public int mPobranychBajtow;
    public int mRozmiar;
    public int mStatus;

    public PostepInfo() {
    }
    protected PostepInfo(Parcel in) {
        mPobranychBajtow = in.readInt();
        mRozmiar = in.readInt();
        mStatus = in.readInt();
    }

    public static final Creator<PostepInfo> CREATOR = new Creator<PostepInfo>() {
        @Override
        public PostepInfo createFromParcel(Parcel in) {
            return new PostepInfo(in);
        }

        @Override
        public PostepInfo[] newArray(int size) {
            return new PostepInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(mPobranychBajtow);
        parcel.writeInt(mRozmiar);
        parcel.writeInt(mStatus);
    }

    public int getProcent() {
        return (int)((double)mPobranychBajtow/(double)mRozmiar*100);
    }

    public void setmPobranychBajtow(int mPobranychBajtow) {
        this.mPobranychBajtow = mPobranychBajtow;
    }

    public void setmRozmiar(int mRozmiar) {
        this.mRozmiar = mRozmiar;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public int getmPobranychBajtow() {
        return mPobranychBajtow;
    }

    public int getmRozmiar() {
        return mRozmiar;
    }

    public int getmStatus() {
        return mStatus;
    }
}
