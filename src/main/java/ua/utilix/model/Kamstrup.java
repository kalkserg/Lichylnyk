package ua.utilix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Kamstrup {
    @Id
    @GeneratedValue
    private Long id;
    private String sigfoxId = "";
    private String decodeKey = "";

    public Kamstrup() {
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

    public String getDecodeKey() { return decodeKey; }

    public void setDecodeKey(String dec) { this.decodeKey = decodeKey; }

}
