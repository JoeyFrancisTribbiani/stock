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
public class BreadcrumbComponent {

    private String categoryUrl;
    private List<PathList> pathList;
    private String categoryName;
    public void setCategoryUrl(String categoryUrl) {
         this.categoryUrl = categoryUrl;
     }
     public String getCategoryUrl() {
         return categoryUrl;
     }

    public void setPathList(List<PathList> pathList) {
         this.pathList = pathList;
     }
     public List<PathList> getPathList() {
         return pathList;
     }

    public void setCategoryName(String categoryName) {
         this.categoryName = categoryName;
     }
     public String getCategoryName() {
         return categoryName;
     }

}