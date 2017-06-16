package com.qd8s.werewolf2.GameHandler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matthew on 6/9/2017.
 * A player contains data for their ID and privelage level.
 */

public class Client implements Parcelable {

    private String id;
    boolean isHost;
    private boolean inGame;

    protected Client(Parcel in) {
        id = in.readString();
        isHost = in.readByte() != 0;
        inGame = in.readByte() != 0;
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isHost ? 1 : 0));
        dest.writeByte((byte) (inGame ? 1 : 0));
    }

    public String get_ID() { return id;}
    public boolean is_Host() { return isHost;}
    public boolean is_InGame() { return inGame;}


}
