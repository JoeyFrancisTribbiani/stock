/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageComponent {

    private List<String> image250PathList;
    private List<String> imagePathList;
    private List<String> image640PathList;
    private boolean imageExist;
    private String imageServer;
    private List<String> summImagePathList;

    public List<String> getImage250PathList() {
        return image250PathList;
    }

    public void setImage250PathList(List<String> image250PathList) {
        this.image250PathList = image250PathList;
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }

    public List<String> getImage640PathList() {
        return image640PathList;
    }

    public void setImage640PathList(List<String> image640PathList) {
        this.image640PathList = image640PathList;
    }

    public boolean getImageExist() {
        return imageExist;
    }

    public void setImageExist(boolean imageExist) {
        this.imageExist = imageExist;
    }

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public List<String> getSummImagePathList() {
        return summImagePathList;
    }

    public void setSummImagePathList(List<String> summImagePathList) {
        this.summImagePathList = summImagePathList;
    }

}