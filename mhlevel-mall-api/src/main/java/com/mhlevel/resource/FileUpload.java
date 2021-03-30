package com.mhlevel.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author quanbin
 * @date 2021-03-30
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-dev.properties")
public class FileUpload {

    private String imageUserFaceLocation;

    private String imageServelUrl;

    public String getImageUserFaceLocation() {
        return imageUserFaceLocation;
    }

    public void setImageUserFaceLocation(String imageUserFaceLocation) {
        this.imageUserFaceLocation = imageUserFaceLocation;
    }

    public String getImageServelUrl() {
        return imageServelUrl;
    }

    public void setImageServelUrl(String imageServelUrl) {
        this.imageServelUrl = imageServelUrl;
    }
}
