package com.qd8s.werewolf2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 6/5/17.
 */

public class User implements Parcelable {

    private boolean _alive;
    private String _role;
    private String _name;
    private String _id;
    private boolean _immune;
    private boolean _actDone;
    private User _target;
    private boolean _isAlpha;
    private boolean _voteReady;
    private boolean _isHost;
    private String activity;

    //non-default constructor
    public User(String id, boolean alive, String role, String name, boolean immune, boolean actDone, User target, boolean isAlpha) {
        this._alive = alive;
        this._role = role;
        this._name = name;
        this._immune = immune;
        this._actDone = actDone;
        this._target = target;
        this._isAlpha = isAlpha;
        this._voteReady = false;
        this._isHost = false;
        this._id = id;
    }

    //default constructor
    public User()
    {
        _alive = true;
        _role = "";
        _name = "";
        _immune = false;
        _actDone = false;
        _target = null;
        _isAlpha = false;
        this._voteReady = false;
        _isHost = false;
        _id = "";
    }

    public User(String id, String name) {
        _alive = true;
        _role = "";
        _name = name;
        _immune = false;
        _actDone = false;
        _target = null;
        _isAlpha = false;
        this._voteReady = false;
        _isHost = false;
        _id = id;
    }

    protected User(Parcel in) {
        _alive = in.readByte() != 0;
        _role = in.readString();
        _name = in.readString();
        _id = in.readString();
        _immune = in.readByte() != 0;
        _actDone = in.readByte() != 0;
        _target = in.readParcelable(User.class.getClassLoader());
        _isAlpha = in.readByte() != 0;
        _voteReady = in.readByte() != 0;
        _isHost = in.readByte() != 0;

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //perform role
    void performRole()
    {
        //wolf role
        if (_role == "Wolf"  && _target.isImmune())
        {
            _target.setAlive(false);
        }
        //villiager

        //doc
        if (_role == "Doc")
        {
            _target.setImmune(true);
        }

        return;
    }


    //getters and setters
    public boolean isAlive() {
        return _alive;
    }

    public void setAlive(boolean alive) {
        this._alive = alive;
    }

    public String getRole() {
        return _role;
    }

    public void setRole(String role) {
        this._role = role;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getID() { return this._id; }

    public boolean isImmune() {
        return _immune;
    }

    public void setImmune(boolean immune) {
        this._immune = immune;
    }

    public boolean isActDone() {
        return _actDone;
    }

    public void setActDone(boolean actDone) {
        this._actDone = actDone;
    }

    public User getTarget() { return _target; }

    public void setTarget(User target) { this._target = target; }

    public boolean getAlpha() { return _isAlpha; }

    public void setAlpha(boolean isAlpha) { this._isAlpha = isAlpha; }

    public boolean is_voteReady() {
        return _voteReady;
    }

    public void set_voteReady(boolean _voteReady) {
        this._voteReady = _voteReady;
    }

    public void set_host(boolean value) { _isHost = value;}

    public boolean isHost() { return _isHost; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (_alive ? 1 : 0));
        dest.writeString(_role);
        dest.writeString(_name);
        dest.writeString(_id);
        dest.writeByte((byte) (_immune ? 1 : 0));
        dest.writeByte((byte) (_actDone ? 1 : 0));
        dest.writeParcelable(_target, flags);
        dest.writeByte((byte) (_isAlpha ? 1 : 0));
        dest.writeByte((byte) (_voteReady ? 1 : 0));
        dest.writeByte((byte) (_isHost ? 1 : 0));
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
