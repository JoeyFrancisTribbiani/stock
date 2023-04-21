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
public class QuantityModule {

    private String LOT;
    private String LOTS;
    private String BUY_LIMIT;
    private String QUANTITY;
    private String OFF_OR_MORE;
    private String ONLY_QUANTITY_LEFT;
    private String ADDTIONAL;
    private String QUANTITY_AVAILABLE;
    public void setLOT(String LOT) {
         this.LOT = LOT;
     }
     public String getLOT() {
         return LOT;
     }

    public void setLOTS(String LOTS) {
         this.LOTS = LOTS;
     }
     public String getLOTS() {
         return LOTS;
     }

    public void setBUY_LIMIT(String BUY_LIMIT) {
         this.BUY_LIMIT = BUY_LIMIT;
     }
     public String getBUY_LIMIT() {
         return BUY_LIMIT;
     }

    public void setQUANTITY(String QUANTITY) {
         this.QUANTITY = QUANTITY;
     }
     public String getQUANTITY() {
         return QUANTITY;
     }

    public void setOFF_OR_MORE(String OFF_OR_MORE) {
         this.OFF_OR_MORE = OFF_OR_MORE;
     }
     public String getOFF_OR_MORE() {
         return OFF_OR_MORE;
     }

    public void setONLY_QUANTITY_LEFT(String ONLY_QUANTITY_LEFT) {
         this.ONLY_QUANTITY_LEFT = ONLY_QUANTITY_LEFT;
     }
     public String getONLY_QUANTITY_LEFT() {
         return ONLY_QUANTITY_LEFT;
     }

    public void setADDTIONAL(String ADDTIONAL) {
         this.ADDTIONAL = ADDTIONAL;
     }
     public String getADDTIONAL() {
         return ADDTIONAL;
     }

    public void setQUANTITY_AVAILABLE(String QUANTITY_AVAILABLE) {
         this.QUANTITY_AVAILABLE = QUANTITY_AVAILABLE;
     }
     public String getQUANTITY_AVAILABLE() {
         return QUANTITY_AVAILABLE;
     }

}