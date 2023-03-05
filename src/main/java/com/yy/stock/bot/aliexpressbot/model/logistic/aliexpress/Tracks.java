/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.aliexpressbot.model.logistic.aliexpress;

import java.util.Date;

/**
 * Auto-generated: 2023-03-05 12:19:20
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Tracks {

    private TrackNodeDTO trackNodeDTO;
    private Date eventDateString;
    private long eventDate;
    private String eventDesc;
    private String eventCode;
    private Date timeZone;
    private String location;

    public TrackNodeDTO getTrackNodeDTO() {
        return trackNodeDTO;
    }

    public void setTrackNodeDTO(TrackNodeDTO trackNodeDTO) {
        this.trackNodeDTO = trackNodeDTO;
    }

    public Date getEventDateString() {
        return eventDateString;
    }

    public void setEventDateString(Date eventDateString) {
        this.eventDateString = eventDateString;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Date getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Date timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}