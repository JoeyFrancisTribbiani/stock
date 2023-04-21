/**
  * Copyright 2023 ab173.com 
  */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {

    private String viewMoreText;
    private String actionTarget;
    private String title;
    private String type;
    private String content;
    public void setViewMoreText(String viewMoreText) {
         this.viewMoreText = viewMoreText;
     }
     public String getViewMoreText() {
         return viewMoreText;
     }

    public void setActionTarget(String actionTarget) {
         this.actionTarget = actionTarget;
     }
     public String getActionTarget() {
         return actionTarget;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

}