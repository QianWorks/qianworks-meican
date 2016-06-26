package me.qiancheng.qianworks.meicai.model;

import java.io.Serializable;

/**
 * Created by iamya on 6/24/2016.
 */
public class Base implements Serializable {
    private String error_description;
    private String error;

    public Base() {
       super();
    }

    public Base(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Base{" +
                "error='" + error + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
