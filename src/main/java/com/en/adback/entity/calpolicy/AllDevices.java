package com.en.adback.entity.calpolicy;

import lombok.Data;

@Data
public class AllDevices {
   private String  deviceId;
   private String  cityId ;
   private String ip;
   private String appName;
   private String url;

    public AllDevices() {
    }

    public AllDevices(String deviceId, String cityId) {
        this.deviceId = deviceId;
        this.cityId = cityId;
    }

    public AllDevices(String deviceId, String cityId, String ip, String appName, String url) {
        this.deviceId = deviceId;
        this.cityId = cityId;
        this.ip = ip;
        this.appName = appName;
        this.url = url;
    }

    public String getDeviceId() {
        return deviceId;
    }


}
