/**
  * Copyright 2023 ab173.com 
  */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;
import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
public class ImageComponent {

    private List<String> image250PathList;
    private List<String> imagePathList;
    private List<String> image640PathList;
    private boolean imageExist;
    private String imageServer;
    private List<String> summImagePathList;
    public void setImage250PathList(List<String> image250PathList) {
         this.image250PathList = image250PathList;
     }
     public List<String> getImage250PathList() {
         return image250PathList;
     }

    public void setImagePathList(List<String> imagePathList) {
         this.imagePathList = imagePathList;
     }
     public List<String> getImagePathList() {
         return imagePathList;
     }

    public void setImage640PathList(List<String> image640PathList) {
         this.image640PathList = image640PathList;
     }
     public List<String> getImage640PathList() {
         return image640PathList;
     }

    public void setImageExist(boolean imageExist) {
         this.imageExist = imageExist;
     }
     public boolean getImageExist() {
         return imageExist;
     }

    public void setImageServer(String imageServer) {
         this.imageServer = imageServer;
     }
     public String getImageServer() {
         return imageServer;
     }

    public void setSummImagePathList(List<String> summImagePathList) {
         this.summImagePathList = summImagePathList;
     }
     public List<String> getSummImagePathList() {
         return summImagePathList;
     }

}