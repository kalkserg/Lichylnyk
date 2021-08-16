package ua.utilix.model;

import org.springframework.stereotype.Component;
import sigfox.Sigfox;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static java.lang.String.format;

@Component
public class SigfoxData implements Sigfox {
    private sigfox.Sigfox strategy;
    private double value;
    private TypeMessage type;
    private int sendCounter;
    private String message;
    private String id;
    private int units;
    private String chan;

    private String interval;
    private int hardwareRev;
    private int softwareRev;
    private String div = "";
    private int temperature;
    private float batteryPower;
    private String remainBatteryLife = "";
    private String radioPower = "";
    private String minFlow = "";
    private String maxFlow = "";
    private String minWaterTemp = "";
    private String minAmbTemp = "";
    private String maxAmbTemp = "";
    private static int Hr[];
    private TypeError errorMagnet;
    private TypeError errorBurst;
    private TypeError errorDry;
    private TypeError errorReverse;
    private TypeError errorLeak;
    //Bove
    private TypeError errorTamper;
    private TypeError errorFreezing;
    private TypeError errorBatteryAlarm;
    private TypeError errorOverRange;
    private TypeError errorTemperatureAlarm;
    private TypeError errorEEPROM;
    private TypeError errorShortcircuit;
    private TypeError errorSensorbreak;
    private TypeError errorTemperatureless;
    private TypeError errorTemperaturemore;

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = !interval.equals("0")?" "+ interval + " минут.":".";
    }

    public String getMinFlow() {
        return minFlow;
    }

    public void setMinFlow(String minFlow) {
        this.minFlow = " Мін.потік "+ minFlow + " " + getFUnits() + ".";
    }

    public String getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(String maxFlow) {
        this.maxFlow = " Макс.потік " + maxFlow + " " + getFUnits() + ".";
    }

    public String getMinWaterTemp() {
        return minWaterTemp;
    }

    public void setMinWaterTemp(String minWaterTemp) {
        this.minWaterTemp = " Мін.температура води " + minWaterTemp + getTUnits() + ".";
    }

    public String getMinAmbTemp() {
        return minAmbTemp;
    }

    public void setMinAmbTemp(String minAmbTemp) {
        this.minAmbTemp = " Мін.навкол.температура " + minAmbTemp + getTUnits() + ".";
    }

    public String getMaxAmbTemp() {
        return maxAmbTemp;
    }

    public void setMaxAmbTemp(String maxAmbTemp) {
        this.maxAmbTemp = " Макс.навкол.температура " + maxAmbTemp + getTUnits() + ".";
    }

    public int[] getHr() {
        return Hr;
    }

    public int getHr(int i) {
        return Hr[i];
    }

    public void setHr(int[] hr) {
        Hr = hr;
    }

    public String getTUnits() {
        if (units == 0) return '\u00B0'+"C";
        else return '\u00B0'+"F";
    }
    public String getFUnits() {
        if (units == 0) return "літр на годину";
        else return "галон за хв.";
    }

    public String getVUnits() {
        String tmp = null;
        if(units == 0 ) tmp = " м.куб. ";
        else if(units == 1 ) tmp = " фут.куб. ";
        else if(units == 2 ) tmp = " галон. ";
        else if(units == 10 ) tmp = " кВт/ч. ";
        return tmp;
    }

    public String getChan() {
        if(chan!=null) return "канал" + chan + " ";
        return "";
    }

    public void setChan(String chan) {
        this.chan = chan;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getRemainBatteryLife() {
        return remainBatteryLife;
    }

    public void setRemainBatteryLife(int remainBatteryLife) {
        this.remainBatteryLife = "Залишилось батарейки " + String.valueOf(remainBatteryLife) + " міс. ";
    }

    public TypeError getErrorDry() {
        return errorDry;
    }

    public void setErrorDry(TypeError errorDry) {
        this.errorDry = errorDry;
    }

    public TypeError getErrorReverse() {
        return errorReverse;
    }

    public void setErrorReverse(TypeError errorReverse) {
        this.errorReverse = errorReverse;
    }

    public TypeError getErrorLeak() {
        return errorLeak;
    }

    public void setErrorLeak(TypeError errorLeak) {
        this.errorLeak = errorLeak;
    }

    public TypeError getErrorBurst() {
        return errorBurst;
    }

    public void setErrorBurst(TypeError errorBurst) {
        this.errorBurst = errorBurst;
    }

    public TypeError getErrorMagnet() {
        return errorMagnet;
    }

    public void setErrorMagnet(TypeError errorMagnet) {
        this.errorMagnet = errorMagnet;
    }

//------------

    public TypeError getErrorTamper() {
        return errorTamper;
    }

    public void setErrorTamper(TypeError errorTamper) {
        this.errorTamper = errorTamper;
    }

    public TypeError getErrorFreezing() {
        return errorFreezing;
    }

    public void setErrorFreezing(TypeError errorFreezing) {
        this.errorFreezing = errorFreezing;
    }

    public TypeError getErrorBatteryAlarm() {
        return errorBatteryAlarm;
    }

    public void setErrorBatteryAlarm(TypeError errorBatteryAlarm) {
        this.errorBatteryAlarm = errorBatteryAlarm;
    }


    public TypeError getErrorOverRange() {
        return errorOverRange;
    }

    public void setErrorOverRange(TypeError errorOverRange) {
        this.errorOverRange = errorOverRange;
    }


    public TypeError getErrorTemperatureAlarm() {
        return errorTemperatureAlarm;
    }

    public void setErrorTemperatureAlarm(TypeError errorTemperatureAlarm) {
        this.errorTemperatureAlarm = errorTemperatureAlarm;
    }

    public TypeError getErrorEEPROM() {
        return errorEEPROM;
    }

    public void setErrorEEPROM(TypeError errorEEPROM) {
        this.errorEEPROM = errorEEPROM;
    }

    public TypeError getErrorShortcircuit() {
        return errorShortcircuit;
    }

    public void setErrorShortcircuit(TypeError errorShortcircuit) {
        this.errorShortcircuit = errorShortcircuit;
    }

    public TypeError getErrorSensorbreak() {
        return errorSensorbreak;
    }

    public void setErrorSensorbreak(TypeError errorSensorbreak) {
        this.errorSensorbreak = errorSensorbreak;
    }

    public TypeError getErrorTemperatureless() {
        return errorTemperatureless;
    }

    public void setErrorTemperatureless(TypeError errorTemperatureless) {
        this.errorTemperatureless = errorTemperatureless;
    }

    public TypeError getErrorTemperaturemore() {
        return errorTemperaturemore;
    }

    public void setErrorTemperaturemore(TypeError errorTemperaturemore) {
        this.errorTemperaturemore = errorTemperaturemore;
    }

    //-------

    public int getHardwareRev() {
        return hardwareRev;
    }

    public void setHardwareRev(int hardwareRev) {
        this.hardwareRev = hardwareRev;
    }

    public int getSoftwareRev() {
        return softwareRev;
    }

    public void setSoftwareRev(int softwareRev) {
        this.softwareRev = softwareRev;
    }

    public double getDiv() {
        return Double.parseDouble(div);
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(byte batteryPower) {
        //  U = 2 + (bytes[7]>>7) + (bytes[7]&0x7F)/100.
        System.out.println("----- " + batteryPower);
        float k = (float) (2 + ((batteryPower & 0xFF) >> 7) + (batteryPower & 0x7F) / 100.);
        this.batteryPower = (float) Math.round(k * 100) / 100;
    }

    public String getRadioPower() {
        return radioPower;
    }

    public void setRadioPower(int radioPower) {
        this.radioPower = String.valueOf(radioPower) + "dB.";
    }

    public void setRadioPowerInfo(int radioPower) {
        // '00' -9dB; '01' -6dB; '10' -3dB; '11' Макс. мощность
        if (radioPower == 1) this.radioPower = "-6dB.";
        else if (radioPower == 2) this.radioPower = "-3dB.";
        else if (radioPower == 0) this.radioPower = "-9dB.";
        else if (radioPower == 3) this.radioPower = "Макс.потужність.";
    }

    public sigfox.Sigfox getStrategy() {
        return strategy;
    }

    public void setStrategy(sigfox.Sigfox strategy) {
        this.strategy = strategy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeMessage getType() {
        return type;
    }

    public void setType(TypeMessage type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getSendCounter() {
        return sendCounter;
    }

    public void setSendCounter(int sendCounter) {
        this.sendCounter = sendCounter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String valueForm = "%.3f";
        String batteryForm = "%.2f";
        if (type == TypeMessage.DAILY | type == TypeMessage.HOURLY) {
            String str, hh;
            hh = "";
            str = "Лічильник " + id + ". " + type + ". Показник " + getChan() + format(valueForm,value) + getVUnits() + errorBurst + errorDry + errorLeak + errorReverse + minFlow + maxFlow + minWaterTemp + minAmbTemp + maxAmbTemp;
            if(Hr!=null) {
                hh = "Hr ";
                for(int i=0; i<24; i++) hh = hh + " " + getHr(i);
            }
            return str + hh;
        }
        else if (type == TypeMessage.WEEKLY)
            return "Лічильник " + id + ". " + type + ". Показник " + getChan() + format(valueForm,value) + getVUnits() + " Напруга батарейки " + format(batteryForm,batteryPower) + " В.";
        else if (type == TypeMessage.INFO)
            return "Лічильник " + id + ". " + type + ". Показник " + getChan() + format(valueForm,value) + getVUnits() + " Всього передано " + sendCounter + " повідомлень." + " Рівень сигналу радіо модему " + radioPower + remainBatteryLife + minFlow + maxFlow + minWaterTemp + minAmbTemp + maxAmbTemp;
        else if (type == TypeMessage.EXTENDED)
            return "Лічильник " + id + ". " + type + ". HARDWARE_REV " + hardwareRev + " SOFTWARE_REV " + softwareRev + " Температура модема " + temperature + "С. Напруга батарейкі " + batteryPower + " В. " + "Рівень сигналу радіо модему " + radioPower + ". Коефіцієнт ділення числа імпульсів " + div;
        else if (type == TypeMessage.COMMAND)
            return "Лічильник " + id + ". " + type + (errorMagnet.equals("")?(". Температура модема " + temperature + " С. Напруга батарейкі " + format(batteryForm,batteryPower) + " В. "):(". "+errorMagnet));
        else if (type == TypeMessage.RESET)
            return "Лічильник " + id + ". " + type + ". HARDWARE_REV " + hardwareRev + " SOFTWARE_REV " + softwareRev + " Температура модема " + temperature + " С. Напруга батарейкі " + batteryPower + " В. " + "Рівень сигналу радіо модему " + radioPower + ". Коефіцієнт ділення числа імпульсів " + div;
        if (type == TypeMessage.EVENT)
            return "Лічильник " + id + ". " + type + ". "  + remainBatteryLife + errorBurst + errorDry + errorLeak + errorReverse + minFlow + maxFlow + minWaterTemp + minAmbTemp + maxAmbTemp;
        if (type == TypeMessage.INTERVAL)
            return "Лічильник " + id + ". " + type + interval + " Показник " + getChan() + format(valueForm,value) + getVUnits()  + errorBurst + errorDry + errorLeak + errorReverse + errorTamper + errorFreezing + errorBatteryAlarm + errorOverRange + errorTemperatureAlarm + errorEEPROM + errorSensorbreak + errorShortcircuit + errorTemperatureless + errorTemperaturemore;
        return "Лічильник " + id + ". Невідомий тип повідомлення. Данні: " + message;
    }
}
