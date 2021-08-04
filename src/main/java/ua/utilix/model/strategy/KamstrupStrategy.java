package ua.utilix.model.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import ua.utilix.model.Kamstrup;
import ua.utilix.model.SigfoxData;
import ua.utilix.service.KamstrupService;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;


//@Controller
public class KamstrupStrategy extends DefaultStrategy {

//    @Autowired
//    private KamstrupService kamstrupService;

//    kamstrupService = ((KamstrupService) kamstrupService).getUsername();

//    @Autowired
//    public void setmmmmService(KamstrupService kamstrupService) {
//        this.kamstrupService = kamstrupService;
//    }

    public KamstrupStrategy(SigfoxData sigfoxData) {
        super(sigfoxData);
    }

    public static byte[] decrypt(String input, String dec, String iv) {
        byte[] plainText = null;
        byte[] decodedKey = asBytes(dec);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(asBytes(iv)));
            plainText = cipher.doFinal(asBytes(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plainText;
    }

    public static String makeIV(String id, byte aes, int sequence) {
        String padding = "00000000";
        String filler = "484B484C";

        String deviceID = String.format("%8.8s", id).replace(' ', '0');
        String aesCounter = String.format("%5.5s", Integer.toHexString(aes)).replace(' ', '0');
        String sigfoxSequence = String.format("%3.3s", Integer.toHexString(sequence)).replace(' ', '0');
        String iv = padding + deviceID + filler + aesCounter + sigfoxSequence;
        return iv;
    }

//    private String getDec(String id) {
//        Kamstrup kamstrup;
//        String dec = null;
//        System.out.println("kamstrupService " + kamstrupService);
//        try {
//            System.out.println("__");
//            kamstrupService.find();
//            kamstrup = kamstrupService.findDecodeKey("2176A59");
//            System.out.println("___");
//            System.out.println(kamstrup.getSigfoxId() + " " + kamstrup.getDecodeKey());
//            System.out.println("________");
//            dec = kamstrup.getDecodeKey();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//
////        try {
////            System.out.println("__");
////            kamstrup = kamstrupService.findDecodeKey("AA");
////            System.out.println(kamstrup.getSigfoxId() + " " + kamstrup.getDecodeKey());
////            System.out.println("________");
////            dec = kamstrup.getDecodeKey();
////        } catch (Exception ex) {
////            System.out.println(ex.getMessage());
////        }
//        return dec;

//        Map<String, String> dec;
//        dec = new HashMap<>();
//        dec.put("2176A3A", "EF2774E3EBE678589B1B51A31F068D57");
//        dec.put("2176A48", "8185A73AEEEE95BD46232924447481CA");
//        dec.put("2176A3C", "047253B40DD4FA47AD907E64A1E46082");
//        dec.put("2176A3D", "9782E386F5913D07CB7B113A2FE08191");
//        dec.put("2176A3E", "9FED22647A759381BE8E1C955DE6E093");
//        dec.put("2176A3F", "2DCBEFB449F653E087EBC61BE18D3136");
//        dec.put("2176A40", "631317A11E1EC0D56A448CCA7E99B076");
//        dec.put("2176A41", "18679182D0A3527CD8C4A3ACEEBB0DEF");
//        dec.put("2176A42", "27452D8180924F85F6B02F66E92EF0B0");
//        dec.put("2176A43", "D06B09266187976E83E82756FFAC9DAE");
//        dec.put("2176A44", "8F4D1C88B4771A1DFD6AD8DF022AB1BD");
//        dec.put("2176A45", "81BF3FE3FEF64D8614E4EEC5B1CEC835");
//        dec.put("2176A46", "D2F8EBD2735839DEDE349CA4624F8981");
//        dec.put("2176A47", "C2356C3F2FEE1AB0FF154130355247D6");
//        dec.put("2176A49", "931EEABBB26A4A26DAE6F85382A34EC2");
//        dec.put("2176A4A", "9CF5E353F2E771452459B13CECE1B0B7");
//        dec.put("2176A4B", "574BA23273512AC745E3F6E16BA1B5F1");
//        dec.put("2176A58", "5DB62E656483782B521300F302AEE915");
//        dec.put("2176A4D", "A932ACBFF4DDB3E656122FDC57A69349");
//        dec.put("2176A4E", "64030C2DA65C50A88723BDD9B22ED4F0");
//        dec.put("2176A4F", "D259F3B9B5FCD0CE3CF189E70E82D8E7");
//        dec.put("2176A50", "9AED45C75631678112138CF9D3CB3119");
//        dec.put("2176A51", "4722BF08B3B4FDB42CF431DE3F821C72");
//        dec.put("2176A52", "F2201FAA2EDBDB1EE014496020D813C6");
//        dec.put("2176A53", "112903C4E14D5C5C0FD8B143A103ABF4");
//        dec.put("2176A54", "27791BBCB2F0531B815742BE1CB2FE47");
//        dec.put("2176A55", "27AE12C3801A56B05D192D068659266A");
//        dec.put("2176A56", "9658EB41F9698C17CCB393D07E58778F");
//        dec.put("2176A57", "8BC33C422874B289222BE75960E691AD");
//        dec.put("2176A59", "0F1853619AA6FD7A3D946C179E026B93");
//
//        for (Map.Entry<String, String> pair : dec.entrySet()) {
//            if (pair.getKey().equals(id)) return pair.getValue();
//        }
//        return null;
//    }

    public SigfoxData parse(String id, String input, int sequence, String dec) {
        //String dec = getDec(id);

        byte[] bytes = asBytes(input);
        boolean dry = false;
        boolean reverse = false;
        boolean leak = false;
        boolean burst = false;
        double value = 0;
        byte[] output = null;

        int log = ((bytes[0] & 0x04) >> 2); // 1-5: Day =0, Hour =1; for 7: Month =0; Event=1

        int u = (bytes[0] & 0x30) >> 4;
        sigfoxData.setUnits(u);
        //String units = (u == 0 ? "m3,L/hr,C" : u == 1 ? "ft3,GPM,F" : "Gal,GPM,F");

        int div = (bytes[0] & 0xC0) >> 6;
        if (div == 0) sigfoxData.setDiv("1");
        else if (div == 1) sigfoxData.setDiv("10");
        else if (div == 2) sigfoxData.setDiv("100");
        else if (div == 3) sigfoxData.setDiv("1000");

        int packageType = bytes[0] & 0x07;

        //System.out.println("packageType "+packageType);

        switch (packageType) {
            case 0: {
//                System.out.println("-0-");
                //DAILY
                if (log == 0) sigfoxData.setType(TypeMessage.DAILY);
                    //HOURLY
                else if (log == 1) sigfoxData.setType(TypeMessage.HOURLY);

                byte aes = bytes[1];
                byte[] b = {aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes};
                String iv = asString(b);
                String encryptedTxt = String.format("%s", input.substring(4));
                output = decrypt(encryptedTxt, dec, iv);

                double minFlow = ((output[6] & 0xFF) | ((output[7] & 0xFF) << 8));
                sigfoxData.setMinFlow(String.valueOf(minFlow));
                dry = ((output[0] & 0x01) == 1);
                reverse = ((output[0] & 0x02) >> 1) == 1;
                leak = ((output[0] & 0x04) >> 2) == 1;
                burst = ((output[0] & 0x08) >> 3) == 1;
                value = ((((output[2] & 0xFF) | ((output[3] & 0xFF) << 8) | ((output[4] & 0xFF) << 16) | ((output[5] & 0xFF) << 24))));
                break;
            }
            case 1: {
//                System.out.println("-1-");
                //DAILY
                if (log == 0) sigfoxData.setType(TypeMessage.DAILY);
                    //HOURLY
                else if (log == 1) sigfoxData.setType(TypeMessage.HOURLY);

                byte aes = bytes[1];
                byte[] b = {aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes, aes};
                String iv = asString(b);
                String encryptedTxt = String.format("%s", input.substring(4));
                output = decrypt(encryptedTxt, dec, iv);
                int maxFlow = ((output[6] & 0xFF) | ((output[7] & 0xFF) << 8));
                sigfoxData.setMaxFlow(String.valueOf(maxFlow));
                dry = ((output[0] & 0x01) == 1);
                reverse = ((output[0] & 0x02) >> 1) == 1;
                leak = ((output[0] & 0x04) >> 2) == 1;
                burst = ((output[0] & 0x08) >> 3) == 1;
                value = ((((output[2] & 0xFF) | ((output[3] & 0xFF) << 8) | ((output[4] & 0xFF) << 16) | ((output[5] & 0xFF) << 24))));
                break;
            }
            case 2: {
//                System.out.println("-2-");
                //DAILY
                if (log == 0) sigfoxData.setType(TypeMessage.DAILY);
                    //HOURLY
                else if (log == 1) sigfoxData.setType(TypeMessage.HOURLY);
                byte aes = bytes[1];
                String iv = makeIV(id, aes, sequence);
                //String encryptedTxt = String.format("%s", input.substring(4));
                output = decrypt(input, dec, iv);
                int minFlow = ((output[8] & 0xFF) | ((output[9] & 0xFF) << 8));
                int maxFlow = ((output[10] & 0xFF) | ((output[11] & 0xFF) << 8));
                sigfoxData.setMinFlow(String.valueOf(minFlow));
                sigfoxData.setMaxFlow(String.valueOf(maxFlow));
                dry = (output[2] & 0x01) == 1;
                reverse = ((output[2] & 0x02) >> 1) == 1;
                leak = ((output[2] & 0x04) >> 2) == 1;
                burst = ((output[2] & 0x08) >> 3) == 1;
                value = ((((output[4] & 0xFF) | ((output[5] & 0xFF) << 8) | ((output[6] & 0xFF) << 16) | ((output[7] & 0xFF) << 24))));
                break;
            }
            case 3: {
//                System.out.println("-3-");
                //DAILY
                if (log == 0) sigfoxData.setType(TypeMessage.DAILY);
                    //HOURLY
                else if (log == 1) sigfoxData.setType(TypeMessage.HOURLY);
                byte aes = bytes[1];
                String iv = makeIV(id, aes, sequence);
                //String encryptedTxt = String.format("%s", input.substring(4));
                output = decrypt(input, dec, iv);
                int maxFlow = ((output[8] & 0xFF) | ((output[9] & 0xFF) << 8));
                int minWaterTemp = output[10] & 0xFF;
                int minAmbTemp = output[11] & 0xFF;
                sigfoxData.setMaxFlow(String.valueOf(maxFlow));
                if (minWaterTemp < 127) sigfoxData.setMinWaterTemp(String.valueOf(minWaterTemp));
                if (minAmbTemp < 127) sigfoxData.setMinAmbTemp(String.valueOf(minAmbTemp));
                dry = (output[2] & 0x01) == 1;
                reverse = ((output[2] & 0x02) >> 1) == 1;
                leak = ((output[2] & 0x04) >> 2) == 1;
                burst = ((output[2] & 0x08) >> 3) == 1;
                value = ((((output[4] & 0xFF) | ((output[5] & 0xFF) << 8) | ((output[6] & 0xFF) << 16) | ((output[7] & 0xFF) << 24))));
                break;
            }
            case 4: {
//                System.out.println("-4-");
                //DAILY
                if (log == 0) sigfoxData.setType(TypeMessage.DAILY);
                    //HOURLY
                else if (log == 1) sigfoxData.setType(TypeMessage.HOURLY);
                byte aes = bytes[1];
                String iv = makeIV(id, aes, sequence);
                //String encryptedTxt = String.format("%s", input.substring(4));
                output = decrypt(input, dec, iv);
                int maxFlow = ((output[8] & 0xFF) | ((output[9] & 0xFF) << 8));
                int minWaterTemp = output[10] & 0xFF;
                int maxAmbTemp = output[11] & 0xFF;
                sigfoxData.setMaxFlow(String.valueOf(maxFlow));
                if (minWaterTemp < 127) sigfoxData.setMinWaterTemp(String.valueOf(minWaterTemp));
                if (maxAmbTemp < 127) sigfoxData.setMaxAmbTemp(String.valueOf(maxAmbTemp));
                dry = (output[2] & 0x01) == 1;
                reverse = ((output[2] & 0x02) >> 1) == 1;
                leak = ((output[2] & 0x04) >> 2) == 1;
                burst = ((output[2] & 0x08) >> 3) == 1;
                value = ((((output[4] & 0xFF) | ((output[5] & 0xFF) << 8) | ((output[6] & 0xFF) << 16) | ((output[7] & 0xFF) << 24))));
                break;
            }
            case 5: {
//                System.out.println("-5-");
                //DAILY
                if (log == 0) sigfoxData.setType(TypeMessage.DAILY);
                    //HOURLY
                else if (log == 1) sigfoxData.setType(TypeMessage.HOURLY);
                byte aes = (byte) (bytes[1] & 0x3F);
                int[] hr = new int[24];
                String iv = makeIV(id, aes, sequence);
                //String encryptedTxt = String.format("%s", input.substring(4));
                output = decrypt(input, dec, iv);
                dry = (output[2] & 0x04) >> 2 == 1;
                reverse = ((output[2] & 0x08) >> 3) == 1;
                leak = ((output[2] & 0x16) >> 4) == 1;
                burst = ((output[2] & 0x32) >> 5) == 1;

                int id5 = (bytes[2] & 0xC0) >> 6;
                if (id5 == 0) {
                    value = ((((output[2] & 0x03) | ((output[3] & 0xFF) << 2) | ((output[4] & 0xFF) << 10) | ((output[5] & 0xFF) << 18)) | ((output[6] & 0x0F) << 28)));
                    //Hr1-4
                    hr[1] = ((output[8] & 0x01) << 6) | ((output[9] & 0xFC) >> 2);
                    hr[2] = ((output[9] & 0x03) << 5) | ((output[10] & 0xF8) >> 3);
                    hr[3] = ((output[10] & 0x07) << 4) | ((output[11] & 0xF0) >> 4);
                    hr[4] = ((output[11] & 0x0F) << 3);
                } else if (id5 == 1) {
                    //Hr4-15
                    int hr4 = sigfoxData.getHr(4);
                    hr[4] = hr4 | ((output[2] & 0x03) << 1) | ((output[3] & 0x80) >> 8);
                    hr[5] = (output[3] & 0x7F);
                    hr[6] = ((output[4] & 0xFE) >> 1);
                    hr[7] = ((output[4] & 0x01) << 6) | ((output[5] & 0xFC) >> 2);
                    hr[8] = ((output[5] & 0x03) << 5) | ((output[6] & 0xF8) >> 3);
                    hr[9] = ((output[6] & 0x07) << 4) | ((output[7] & 0xF0) >> 4);
                    hr[10] = ((output[7] & 0x07) << 3) | ((output[8] & 0xE0) >> 5);
                    hr[11] = ((output[8] & 0x1F) << 2) | ((output[9] & 0xC0) >> 6);
                    hr[12] = ((output[9] & 0x3F) << 1) | ((output[10] & 0x80) >> 7);
                    hr[13] = (output[10] & 0x7F);
                    hr[14] = (output[11] & 0xFE) >> 1;
                    hr[15] = (output[11] & 0x01) << 6;
                } else if (id5 == 2) {
                    value = ((((output[2] & 0x03) | ((output[3] & 0xFF) << 3) | ((output[4] & 0xFF) << 11) | ((output[5] & 0xFF) << 19)) | ((output[6] & 0x0F) << 29)));
                    //Hr15-21
                    int hr15 = sigfoxData.getHr(15);
                    hr[15] = hr15 | ((output[6] & 0x07) << 4) | ((output[7] & 0xC0) >> 6);
                    hr[16] = ((output[7] & 0x3F) << 1) | ((output[8] & 0x80) >> 7);
                    hr[17] = (output[8] & 0x7F);
                    hr[18] = (output[9] & 0xFE) >> 1;
                    hr[19] = ((output[9] & 0x01) << 6) | ((output[10] & 0xFC) >> 2);
                    hr[20] = ((output[10] & 0x03) << 5) | (output[11] & 0xF8) >> 1;
                    hr[21] = (output[11] & 0x07) << 4;
                } else if (id5 == 3) {
                    //Hr21-24
                    int hr21 = sigfoxData.getHr(21);
                    hr[21] = hr21 | ((output[2] & 0x03) << 2) | ((output[3] & 0xC0) >> 6);
                    hr[22] = ((output[3] & 0x3F) << 1) | ((output[4] & 0x80) >> 7);
                    hr[23] = (output[4] & 0x7F);
                    int minFlow = ((output[10] & 0xFF) | ((output[11] & 0xFF) << 8));
                    int maxFlow = ((output[8] & 0xFF) | ((output[9] & 0xFF) << 8));
                    int minWaterTemp = output[7] & 0xFF;
                    int minAmbTemp = output[6] & 0xFF;
                    int maxAmbTemp = output[5] & 0xFF;
                    sigfoxData.setMinFlow(String.valueOf(minFlow));
                    sigfoxData.setMaxFlow(String.valueOf(maxFlow));
                    if (minWaterTemp < 127) sigfoxData.setMinWaterTemp(String.valueOf(minWaterTemp));
                    if (minAmbTemp < 127) sigfoxData.setMinAmbTemp(String.valueOf(minAmbTemp));
                    if (maxAmbTemp < 127) sigfoxData.setMaxAmbTemp(String.valueOf(maxAmbTemp));
                }
                sigfoxData.setHr(hr);
                break;
            }
            case 7: {
//                System.out.println("-7-");
                //INFO MONTHLY
                if (log == 0) sigfoxData.setType(TypeMessage.INFO);
                    //EVENT
                else if (log == 1) sigfoxData.setType(TypeMessage.EVENT);

                byte aes = bytes[1];
                String iv = makeIV(id, aes, sequence);
//                System.out.println(iv);
//                System.out.println(input);

                output = decrypt(input, dec, iv);
                //System.out.println(output[0]+" "+output[1]+" "+output[2]+" "+output[3]+" "+output[4]+" "+output[5]+" "+output[6]+" "+output[7]+" "+output[8]+" "+output[9]);

                dry = (output[2] & 0x01) == 1;
                reverse = ((output[2] & 0x02) >> 1) == 1;
                leak = ((output[2] & 0x04) >> 2) == 1;
                burst = ((output[2] & 0x08) >> 3) == 1;

                double minFlow = ((output[4] & 0xFF) | ((output[5] & 0xFF) << 8)) / sigfoxData.getDiv();
                double maxFlow = ((output[6] & 0xFF) | ((output[7] & 0xFF) << 8)) / sigfoxData.getDiv();
                int minWaterTemp = output[8] & 0xFF;
                int minAmbTemp = output[9] & 0xFF;
                int maxAmbTemp = output[10] & 0xFF;
                int remainBatteryLife = output[11] & 0xFF;
                sigfoxData.setRemainBatteryLife(remainBatteryLife);
                sigfoxData.setMinFlow(String.valueOf(minFlow));
                sigfoxData.setMaxFlow(String.valueOf(maxFlow));
                if (minWaterTemp < 127) sigfoxData.setMinWaterTemp(String.valueOf(minWaterTemp));
                if (minAmbTemp < 127) sigfoxData.setMinAmbTemp(String.valueOf(minAmbTemp));
                if (maxAmbTemp < 127) sigfoxData.setMaxAmbTemp(String.valueOf(maxAmbTemp));
                break;
            }
        }
        sigfoxData.setValue(value / sigfoxData.getDiv());
        sigfoxData.setId(id);
        sigfoxData.setMessage(asString(output));
        if (dry) sigfoxData.setErrorDry(TypeError.DRY);
        if (reverse) sigfoxData.setErrorReverse(TypeError.REVERSE);
        if (leak) sigfoxData.setErrorLeak(TypeError.LEAK);
        if (burst) sigfoxData.setErrorBurst(TypeError.BURST);

        return sigfoxData;
    }
}
