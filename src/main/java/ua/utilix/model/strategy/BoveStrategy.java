package ua.utilix.model.strategy;

import ua.utilix.model.SigfoxData;

public class BoveStrategy extends DefaultStrategy {

    public BoveStrategy(SigfoxData sigfoxData) {
        super(sigfoxData);
    }

    public SigfoxData parse(String id, String input, int sequence) {

        byte[] bytes = asBytes(input);

        int valvestate;
        int interval = 0;
        boolean dry = false;
        boolean reverse = false;
        boolean leak = false;
        boolean burst = false;
        boolean tamper = false;
        boolean freezing = false;
        boolean meterbattery = false;
        boolean overrange = false;
        boolean temperature = false;
        boolean eeprom = false;
        boolean transduce = false;
        boolean shortcircuit = false;
        boolean sensorbreak = false;
        boolean temperatureless = false;
        boolean temperaturemore = false;
        Integer waterValue = null;
        Integer heatValue = null;


//        System.out.println(bytes[11]&0xFF);
        int crc = modulo256(new byte[]{bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7], bytes[8], bytes[9], bytes[10]});

        //first byte always 68
        if (bytes[0] == 0x68) {
            //WATER|HEAT
            if ((crc != (bytes[11] & 0xFF)) && ((bytes[1] & 0xFF) == 32)) {

                // HEAT METER
                heatValue = Integer.parseInt(asString(new byte[]{bytes[5], bytes[4], bytes[3], bytes[2]}));
                waterValue = Integer.parseInt(asString(new byte[]{bytes[9], bytes[8], bytes[7], bytes[6]}));
                interval = ((bytes[6] & 0xFF) | ((bytes[5] & 0xFF) << 8));
                meterbattery = (bytes[11] & 0x01) == 1;
                shortcircuit = (bytes[11] & 0x02) >> 1 == 1;
                sensorbreak = (bytes[11] & 0x04) >> 2 == 1;
                dry = (bytes[11] & 0x08) >> 3 == 1;
                eeprom = (bytes[8] & 0x20) >> 5 == 1;
                temperatureless = (bytes[8] & 0x40) >> 6 == 1;
                temperaturemore = (bytes[8] & 0x80) >> 7 == 1;

                sigfoxData.setValue(heatValue / 100.);
                sigfoxData.setUnits(10);                 // kWh

            } else {

                // WATER METER
                waterValue = Integer.parseInt(asString(new byte[]{bytes[4], bytes[3], bytes[2], bytes[1]}));
                interval = ((bytes[6] & 0xFF) | ((bytes[5] & 0xFF) << 8));
                valvestate = (bytes[7] & 0x03);
                leak = (bytes[7] & 0x04) >> 2 == 1;
                burst = (bytes[7] & 0x08) >> 3 == 1;
                tamper = (bytes[7] & 0x10) >> 4 == 1;
                freezing = (bytes[7] & 0x20) >> 5 == 1;
                meterbattery = (bytes[8] & 0x01) == 1;
                dry = (bytes[8] & 0x02) >> 1 == 1;
                reverse = (bytes[8] & 0x04) >> 2 == 1;
                overrange = (bytes[8] & 0x08) >> 3 == 1;
                temperature = (bytes[8] & 0x10) >> 4 == 1;
                eeprom = (bytes[8] & 0x20) >> 5 == 1;
                leak = (bytes[8] & 0x40) >> 6 == 1;
                transduce = (bytes[8] & 0x80) >> 7 == 1;

                sigfoxData.setValue(waterValue / 1000.);
                sigfoxData.setUnits(0);                 // cub.m

            }
        }
        sigfoxData.setMessage(input);
        sigfoxData.setId(id);
        sigfoxData.setType(TypeMessage.INTERVAL); //always
        sigfoxData.setInterval(String.valueOf(interval));
        if (dry) sigfoxData.setErrorDry(TypeError.DRY);
        if (reverse) sigfoxData.setErrorReverse(TypeError.REVERSE);
        if (leak) sigfoxData.setErrorLeak(TypeError.LEAK);
        if (burst) sigfoxData.setErrorBurst(TypeError.BURST);
        if (tamper) sigfoxData.setErrorBurst(TypeError.TAMPER);
        if (freezing) sigfoxData.setErrorBurst(TypeError.FREEZING);
        if (meterbattery) sigfoxData.setErrorBurst(TypeError.BATTERYALARM);
        if (overrange) sigfoxData.setErrorBurst(TypeError.OVERRANGE);
        if (temperature) sigfoxData.setErrorBurst(TypeError.TEMPERATUREALARM);
        if (eeprom) sigfoxData.setErrorBurst(TypeError.EEPROM);
        if (sensorbreak) sigfoxData.setErrorBurst(TypeError.SENSORBREAK);
        if (shortcircuit) sigfoxData.setErrorBurst(TypeError.SHORTCIRCUIT);
        if (temperatureless) sigfoxData.setErrorBurst(TypeError.TEMPERATURELESS);
        if (temperaturemore) sigfoxData.setErrorBurst(TypeError.TEMPERATUREMORE);

        return sigfoxData;
    }

    private int modulo256(byte[] bytes){
        int sum = 0;
        for (int i = 0; i<bytes.length; i++) {
            sum = sum + (bytes[i]&0xFF);
        }
        sum = sum%256;

        return sum;
    }
}
