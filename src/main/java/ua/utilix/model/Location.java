package ua.utilix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private Long id;
    private String sigfoxId = "";
    private String addr = "";
    private String location = "";

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigfoxId() {
        return sigfoxId;
    }

    public void setSigfoxId(String sigfoxId) {
        this.sigfoxId = sigfoxId;
    }

    public String getAddr() {
        return addr.equals("")?"":addr + ", ";
    }

    public void setAddr(String protocol) {
        this.addr = addr;
    }


    public String getLocation() {
        return location.equals("")?"":location + ", ";
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
