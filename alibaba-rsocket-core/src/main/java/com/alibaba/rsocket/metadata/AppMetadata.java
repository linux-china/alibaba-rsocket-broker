package com.alibaba.rsocket.metadata;

import com.alibaba.rsocket.encoding.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * application metadata, json format
 *
 * @author leijuan
 */
public class AppMetadata implements MetadataAware {
    private Integer id;
    /**
     * application uuid, almost uuid
     */
    private String uuid;
    /**
     * app name
     */
    private String name;
    /**
     * description
     */
    private String description;
    /**
     * device information
     */
    private String device;
    /**
     * ip
     */
    private String ip;
    /**
     * rsocket listen port
     */
    private Integer port;
    /**
     * rsocket schema
     */
    private String schema = "tcp";
    /**
     * secure or not
     */
    private boolean secure = false;
    /**
     * connection uri, websocket uri maybe different, ws://127.0.0.1:42252/rsocket
     */
    private String uri;
    /**
     * management port for Spring Boot actuator
     */
    private Integer managementPort;

    /**
     * sdk version
     */
    private String sdk = "RSocket-Java-0.1.0-SNAPSHOT";

    /**
     * developers, format as email list: xxx <xxx@foobar.com>, yyy <yyy@foobar.com>
     */
    private String developers;

    /**
     * metadata
     */
    private Map<String, String> metadata;

    /**
     * humans.md from classpath
     */
    private String humansMd;
    /**
     * connected timestamp
     */
    private Date connectedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumansMd() {
        return humansMd;
    }

    public void setHumansMd(String humansMd) {
        this.humansMd = humansMd;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public Integer getManagementPort() {
        return managementPort;
    }

    public void setManagementPort(Integer managementPort) {
        this.managementPort = managementPort;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String name, String value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(name, value);
    }

    public String getMetadata(String name) {
        if (this.metadata == null) {
            return null;
        }
        return metadata.get(name);
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getUri() {
        if (uri == null) {
            return schema + "::" + ip + ":" + port;
        }
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(Date connectedAt) {
        this.connectedAt = connectedAt;
    }

    @Override
    public RSocketMimeType rsocketMimeType() {
        return RSocketMimeType.Application;
    }

    @Override
    @JsonIgnore
    public String getMimeType() {
        return RSocketMimeType.Application.getType();
    }

    @Override
    @JsonIgnore
    public ByteBuf getContent() {
        return Unpooled.wrappedBuffer(JsonUtils.toJsonBytes(this));
    }

    @Override
    public void load(ByteBuf byteBuf) throws Exception {
        JsonUtils.updateJsonValue(byteBuf, this);
    }

    @Override
    public String toText() throws Exception {
        return JsonUtils.toJsonText(this);
    }

    @Override
    public void load(String text) throws Exception {
        JsonUtils.updateJsonValue(text, this);
    }

    public static AppMetadata from(ByteBuf content) {
        AppMetadata appMetadata = new AppMetadata();
        try {
            appMetadata.load(content);
        } catch (Exception ignore) {

        }
        return appMetadata;
    }
}
