package com.example.generator.common.request;

import com.example.generator.common.response.PageUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author panzhi
 * @Description
 * @since 2021-03-11
 */
public class BaseRequest implements Serializable {

    public static final String notBlankMsg = "不能为空！";

    /**
     * 提交的token
     */
    private String token;

    private String submitToken;

    @Min(value = 0, message = "页码必须大于等于0的整数")
    private Integer page;

    @Min(value = 0, message = "pageSize必须大于等于0的整数")
    private Integer pageSize;

    private Integer start;

    private Integer end;

    private String loginUserId;

    private String loginUserName;

    private String secretToken;

    private String ip;

    private String loginMobile;

    private String userAgent;

    private String referer;

    private String remoteHost;

    /**
     * 是否开启分页
     */
    private boolean pageEnable = true;

    public Integer getPage() {
        if (this.page == null){
            this.page = 1;
        }
        return page;
    }

    public Integer getStart() {
        if (this.page != null && this.page > 0) {
            start = (page - 1) * getPageSize();
            return start;
        }
        return start == null ? 0 : start;
    }

    public Integer getEnd() {
        return getPageSize();
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize == 0) {
            return PageUtils.pageSizeInit;
        }
        return pageSize;
    }

    public String getLoginUserId() {
        return loginUserId == null ? "" : loginUserId;
    }

    public String getLoginUserName() {
        return loginUserName == null ? "" : loginUserName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSubmitToken() {
        return submitToken;
    }

    public void setSubmitToken(String submitToken) {
        this.submitToken = submitToken;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoginMobile() {
        return loginMobile;
    }

    public void setLoginMobile(String loginMobile) {
        this.loginMobile = loginMobile;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public boolean getPageEnable() {
        return pageEnable;
    }

    public void setPageEnable(boolean pageEnable) {
        this.pageEnable = pageEnable;
    }
}

