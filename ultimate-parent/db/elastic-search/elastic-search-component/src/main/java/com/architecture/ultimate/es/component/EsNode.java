package com.core.es.component;

import java.io.Serializable;

/**
 * @author luyi
 * ES的节点
 */
public class EsNode implements Serializable {
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

    @Override
    public String toString() {
        return "EsNode{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", scheme='" + scheme + '\'' +
                '}';
    }
}
