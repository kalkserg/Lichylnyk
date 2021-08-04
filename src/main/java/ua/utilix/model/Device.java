package ua.utilix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Device {
    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private String sigfoxName = "";
    private String sigfoxId = "";
    private String protocol = "";
    private Integer stateId;
    private Boolean allMessage;
    private Boolean notified = false;
//    private String dec = "";

    public Device() {
    }

    public Device(Long chatId, Integer stateId) {
        this.chatId = chatId;
        this.stateId = stateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigfoxName() {
        return sigfoxName;
    }

    public void setSigfoxName(String sigfoxName) {
        this.sigfoxName = sigfoxName;
    }

    public String getSigfoxId() {
        return sigfoxId;
    }

    public void setSigfoxId(String sigfoxId) {
        this.sigfoxId = sigfoxId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Boolean getAllMessage() {
        return allMessage;
    }

    public void setAllMessage(boolean allMessage) {
        this.allMessage = allMessage;
    }

//    public String getDec() {
//        return dec;
//    }
//
//    public void setDec(String dec) {
//        this.dec = dec;
//    }
}
