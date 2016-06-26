package me.qiancheng.qianworks.meicai.model;

/**
 * Created by iamya on 6/24/2016.
 */
public class Token extends Base{
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Token{");
        sb.append("access_token='").append(access_token).append('\'');
        sb.append(", token_type='").append(token_type).append('\'');
        sb.append(", refresh_token='").append(refresh_token).append('\'');
        sb.append(", expires_in=").append(expires_in);
        sb.append('}');
        return sb.toString();
    }
}
