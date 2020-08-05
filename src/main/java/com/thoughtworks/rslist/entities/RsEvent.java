package com.thoughtworks.rslist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class RsEvent {
    @NotNull
    private String eventName;

    @NotNull
    private String keyword;

    @Valid
    @NotNull
    private User user;

    public RsEvent() {
    }

    public RsEvent(String eventName, String keyword, User user) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
}
