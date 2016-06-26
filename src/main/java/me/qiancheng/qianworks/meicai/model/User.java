package me.qiancheng.qianworks.meicai.model;

import java.io.Serializable;

/**
 * Created by iamya on 6/26/2016.
 */
public class User implements Serializable {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
