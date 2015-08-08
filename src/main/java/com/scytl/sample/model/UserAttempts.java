package com.scytl.sample.model;

import java.util.Date;

/**
 * Created by cesardiaz on 07/08/15.
 *
 * @author cesardiaz
 */
public class UserAttempts {

    private int id;
    private String username;
    private int attempts;
    private Date lastModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
