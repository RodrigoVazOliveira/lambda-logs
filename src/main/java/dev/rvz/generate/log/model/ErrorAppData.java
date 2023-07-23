package dev.rvz.generate.log.model;

import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.core.JsonProcessingException;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorAppData {
    private String platform;
    private String appVersion;
    private DeviceModel deviceModel;
    private String messageError;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTimeCreated = LocalDateTime.now();

    public ErrorAppData() {
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public DeviceModel getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(DeviceModel deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}