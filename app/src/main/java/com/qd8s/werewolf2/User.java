package com.qd8s.werewolf2;

/**
 * Created by admin on 6/5/17.
 */

public class User {

    private boolean _alive;
    private String _role;
    private String _name;
    private boolean _immune;
    private boolean _actDone;


    //non-default constructor
    public User(boolean alive, String role, String name, boolean immune, boolean actDone) {
        this._alive = alive;
        this._role = role;
        this._name = name;
        this._immune = immune;
        this._actDone = actDone;
    }

    //default constructor
    public User()
    {
        _alive = true;
        _role = "";
        _name = "";
        _immune = false;
        _actDone = false;
    }

    //perform role
    void performRole(String role, User target)
    {
        //wolf role

        //villiager

        //doc

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


}
