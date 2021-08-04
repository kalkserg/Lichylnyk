package ua.utilix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Water5 {
    @Id
    @GeneratedValue
    private Long id;
    private String sigfoxId = "";
    private Integer initialImpuls1;
    private Integer impulsPerCub1;
    private Float initialMcub1;
//    private Integer initialRawValue1;
    private Integer initialImpuls2;
    private Integer impulsPerCub2;
    private Float initialMcub2;
//    private Integer initialRawValue2;

    public Water5() {
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

    public Integer getInitialImpuls1() {
        return initialImpuls1;
    }

    public void setInitialImpuls1(Integer initialImpuls1) {
        this.initialImpuls1 = initialImpuls1;
    }

    public Integer getInitialImpuls2() {
        return initialImpuls2;
    }

    public void setInitialImpuls2(Integer initialImpuls2) {
        this.initialImpuls2 = initialImpuls2;
    }

    public Integer getImpulsPerCub1() {
        return impulsPerCub1;
    }

    public void setImpulsPerCub1(Integer impulsPerCub1) {
        this.impulsPerCub1 = impulsPerCub1;
    }

    public Integer getImpulsPerCub2() {
        return impulsPerCub2;
    }

    public void setImpulsPerCub2(Integer impulsPerCub2) {
        this.impulsPerCub2 = impulsPerCub2;
    }

    public Float getInitialMCub1() { return initialMcub1; }

    public void setInitialMcub1(Float initialMcub1) {
        this.initialMcub1 = initialMcub1;
    }

    public Float getInitialMCub2() { return initialMcub2; }

    public void setInitialMcub2(Float initialMcub2) {
        this.initialMcub2 = initialMcub2;
    }

//    public Integer getInitialRawValue1() { return initialRawValue1; }
//
//    public void setInitialRawValue1(Integer initialRawValue1) {
//        this.initialRawValue1 = initialRawValue1;
//    }
//
//    public Integer getInitialRawValue2() { return initialRawValue2; }
//
//    public void setInitialRawValue2(Integer initialRawValue2) {
//        this.initialRawValue2 = initialRawValue2;
//    }

//    public List<String> getParameters() {
//            //channel1
//            //0 - inital impuls
//            //1 - impuls per m.cub
//            //2 - inital m.cub
//            //3 - raw value channel 1
//            //channel2
//            //4 - inital impuls
//            //5 - impuls per m.cub
//            //6 - inital m.cub
//            //7 - raw value channel 2
//        return new ArrayList<String>(Arrays.asList(this.getInitialImpuls1().toString(),this.getImpulsPerCub1().toString(),this.getInitialMCub1().toString(),this.getInitialRawValue1().toString(),
//                this.getInitialImpuls2().toString(),this.getImpulsPerCub2().toString(),this.getInitialMCub2().toString(),this.getInitialRawValue2().toString()));
//    }

}
