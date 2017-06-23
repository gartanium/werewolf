package com.qd8s.werewolf2.GameHandler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Matthew on 6/9/2017.
 * A player contains data for their ID and privelage level.
 */

public class Client implements Parcelable {

    private String id;

    private boolean inGame;

    private boolean isHost;
    /**
     * Sets the Client to be the host.
     * @param value
     */
    private void setHost(boolean value) { isHost = value; }


    /**
     * Default constructor.
     * Default Id is set to "John Doe".
     * Is Host is set to false.
     */
    public Client() {
        id = "John Doe";
        isHost = false;
        inGame = false;
    }

    /**
     * Non default constructor.
     * @param id Client ID.
     * @param isHost Is Client the host?
     */
    public Client(String id, boolean isHost) {
        this.id = id;
        this.isHost = isHost;
        this.inGame = false;
    }

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

    public void set_host(boolean _host) {
        this.isHost = true;
    }

    public boolean is_host() {
        return isHost;
    }
}
