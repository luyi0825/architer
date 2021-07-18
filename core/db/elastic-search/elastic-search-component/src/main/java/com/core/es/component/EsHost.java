package com.core.es.component;

import java.io.Serializable;

public class EsHost implements Serializable {
    private String ip;
    private int port = 92;
    private String scheme = "http";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
