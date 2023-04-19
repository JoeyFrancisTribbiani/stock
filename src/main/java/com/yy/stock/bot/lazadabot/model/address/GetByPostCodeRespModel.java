/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.lazadabot.model.address;

import java.util.List;

/**
 * Auto-generated: 2023-02-14 12:38:42
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class GetByPostCodeRespModel {
    private String locationTreeAddressId;
    private String locationTreeAddressName;
    private List<String> locationTreeAddressNameList;
    private boolean hasFloor;
    private String postCode;
    private String postCodeType;
    private String countryName;
    private double latitude;
    private double longitude;
    private boolean serviceNumber;

    public String getLocationTreeAddressId() {
        return locationTreeAddressId;
    }

    public void setLocationTreeAddressId(String locationTreeAddressId) {
        this.locationTreeAddressId = locationTreeAddressId;
    }

    public String getLocationTreeAddressName() {
        return locationTreeAddressName;
    }

    public void setLocationTreeAddressName(String locationTreeAddressName) {
        this.locationTreeAddressName = locationTreeAddressName;
    }

    public List<String> getLocationTreeAddressNameList() {
        return locationTreeAddressNameList;
    }

    public void setLocationTreeAddressNameList(List<String> locationTreeAddressNameList) {
        this.locationTreeAddressNameList = locationTreeAddressNameList;
    }

    public boolean getHasFloor() {
        return hasFloor;
    }

    public void setHasFloor(boolean hasFloor) {
        this.hasFloor = hasFloor;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostCodeType() {
        return postCodeType;
    }

    public void setPostCodeType(String postCodeType) {
        this.postCodeType = postCodeType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(boolean serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

}