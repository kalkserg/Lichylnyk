package ua.utilix.model.strategy;

import ua.utilix.model.SigfoxData;

import java.util.*;

public class Water5Strategy extends DefaultStrategy {

    private static Map<String, List<String>> initial = new HashMap<>();

    public Water5Strategy(SigfoxData sigfoxData) {
        super(sigfoxData);
        makeInitial();
    }

    private static void makeInitial() {
        //for test
        //channel1
        //0 - inital impuls
        //1 - impuls per m.cub
        //2 - inital m.cub
        //3 - raw value channel 1
        //channel2
        //4 - inital impuls
        //5 - impuls per m.cub
        //6 - inital m.cub
        //7 - raw value channel 2
        initial.put("216D4A8", new ArrayList<String>(Arrays.asList("0","1000","31.325","0",   "0","100","31.02","0"))); //двухканальний jupiter
        initial.put("216D4A9", new ArrayList<String>(Arrays.asList("0","100","0","0",   "0","100","0","0"))); //двухканальний jupiter
        initial.put("216D4AA", new ArrayList<String>(Arrays.asList("0","100","0","0",   "0","100","0","0"))); //двухканальний jupiter
        initial.put("216D494", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D499", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D497", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D498", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D49E", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D495", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D4A7", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("216D4A2", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO
        initial.put("1B3128B", new ArrayList<String>(Arrays.asList("0","1000","0","0"))); //одноканальний FLUO

    }

    private static void setRawValue(String id, String val, int chan) {
        for (Map.Entry<String, List<String>> pair : initial.entrySet()) {
            if (pair.getKey().equals(id)) {
                List<String> list = getInitial(id);
                if(chan==1) list.set(3,val);
                else list.set(7,val);
                initial.put(id,list);
            }
        }
    }

    private static List<String> getInitial(String id) {
        //0 - inital impuls 1
        //1 - impuls per m.cub 1
        //2 - inital m.cub 1
        //3 - raw value channel 1
        //4 - inital impuls 2
        //5 - impuls per m.cub 2
        //6 - inital m.cub 2
        //7 - raw value channel 2
        for (Map.Entry<String, List<String>> pair : initial.entrySet()) {
            if (pair.getKey().equals(id)) return pair.getValue();
        }
        return null;
    }

    public SigfoxData parse(String id, String input, int sequence) {
        int numChannels;
        List<String> startValue = getInitial(id);
        int initImpuls,initImpuls1,initImpuls2 = 0;
        int rawValue,rawValue1,rawValue2 = 0;
        double impulsPerMCub,impulsPerMCub1,impulsPerMCub2 = 0;
        float initMCub,initMCub1,initMCub2 = 0;
        //chan1
        initImpuls1 = Integer.parseInt(startValue.get(0));
        impulsPerMCub1 = Integer.parseInt(startValue.get(1));
        initMCub1 = Float.parseFloat(startValue.get(2));
        rawValue1 = Integer.parseInt(startValue.get(3));
        //chan2
        try {
            initImpuls2 = Integer.parseInt(startValue.get(4));
            impulsPerMCub2 = Integer.parseInt(startValue.get(5));
            initMCub2 = Float.parseFloat(startValue.get(6));
            rawValue2 = Integer.parseInt(startValue.get(7));
            numChannels = 2;
        }catch (Exception e){
            numChannels = 1;
        }

        sigfoxData.setId(id);
        sigfoxData.setUnits(0);
        byte[] bytes = asBytes(input);
        sigfoxData.setMessage(input);

        //DAILY
        if (!isBitSet(bytes[0], 1)) {
            //15low bit
            int chan;
            if(numChannels==2) {
                chan = sequence%2==0?2:1;
                sigfoxData.setChan(String.valueOf(chan));
            } else chan = 1;
            if(chan == 1) {
                rawValue = rawValue1;
                initImpuls = initImpuls1;
                impulsPerMCub = impulsPerMCub1;
                initMCub = initMCub1;
            }else{
                rawValue = rawValue2;
                initImpuls = initImpuls2;
                impulsPerMCub = impulsPerMCub2;
                initMCub = initMCub2;
            }
            int lo = (rawValue & 0x7FFF);
            int hi = (rawValue & 0xFFFF8000);
            int newValue = ((bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8)) >> 1;
            if (newValue > lo) rawValue = hi + newValue;
            else rawValue = rawValue + newValue + (lo==0?0:(0x8000-lo));
            setRawValue(id,String.valueOf(rawValue),chan);
            sigfoxData.setValue((rawValue - initImpuls)/impulsPerMCub+initMCub);
            sigfoxData.setType(TypeMessage.DAILY);
            sigfoxData.setSendCounter(0);

        //INFO MONTHLY CHANNEL1 | CHANNEL2
        } else if (bytes[0] == 0x61 | bytes[0] == 0x69) {
            int chan = bytes[0]==0x61?1:2;
            //32bit
            int newValue = (((bytes[1] & 0xFF) | ((bytes[2] & 0xFF) << 8) | ((bytes[3] & 0xFF) << 16) | ((bytes[4] & 0xFF) << 24)));
            if(chan == 1) {
                initImpuls = initImpuls1;
                impulsPerMCub = impulsPerMCub1;
                initMCub = initMCub1;
            }else{
                initImpuls = initImpuls2;
                impulsPerMCub = impulsPerMCub2;
                initMCub = initMCub2;
            }
            setRawValue(id,String.valueOf(newValue),chan);
            sigfoxData.setValue((newValue - initImpuls)/impulsPerMCub+initMCub);
            sigfoxData.setType(TypeMessage.INFO);
            sigfoxData.setSendCounter((bytes[5] & 0xFF) | ((bytes[6] & 0xFF) << 8));
            sigfoxData.setRadioPowerInfo(((bytes[7] & 0xC0)) >> 6);
            sigfoxData.setChan(String.valueOf(chan));

        //EXTENDED MONTHLY
        } else if (bytes[0] == (byte) 0x81 | bytes[0] == (byte) 0x89) {
            //Contains information about the hardware and software version, temperature inside the device, battery voltage, RF output level, and maximum recorded flow.
            //Sent instead of every sixth monthly message.
            sigfoxData.setValue(0);
            sigfoxData.setType(TypeMessage.EXTENDED);
            sigfoxData.setSendCounter(0);
            sigfoxData.setHardwareRev(bytes[1]);
            sigfoxData.setSoftwareRev((bytes[2] & 0x3F));
            sigfoxData.setTemperature(bytes[4] & 0xFF);
            sigfoxData.setBatteryPower(bytes[5]);
            sigfoxData.setRadioPower(bytes[6] & 0xFF);
            int div = ((bytes[2] & 0xC0) >> 6);
            // '01' - div 1; '10','00' - div = 10; '11' - div = 100;
            if (div == 1) sigfoxData.setDiv("1");
            else if (div == 2) sigfoxData.setDiv("10");
            else if (div == 0) sigfoxData.setDiv("10");
            else if (div == 3) sigfoxData.setDiv("100");

        //WEEKLY CHANNEL1 | CHANNEL2
        } else if (bytes[0] == 0x73 | bytes[0] == 0x7B) {
            int chan = bytes[0]==0x73?1:2;
            //27 bit
            if(chan == 1) {
                initImpuls = initImpuls1;
                impulsPerMCub = impulsPerMCub1;
                initMCub = initMCub1;
            }else{
                initImpuls = initImpuls2;
                impulsPerMCub = impulsPerMCub2;
                initMCub = initMCub2;
            }
            int newValue = (bytes[1] & 0xFF) | ((bytes[2] & 0xFF) << 8) | ((bytes[3] & 0xFF) << 16) | ((bytes[4] & 0x07) << 24) ;
            setRawValue(id,String.valueOf(newValue),chan);
            sigfoxData.setValue((newValue - initImpuls)/impulsPerMCub+initMCub);
            sigfoxData.setType(TypeMessage.WEEKLY);
            sigfoxData.setSendCounter(0);
            sigfoxData.setBatteryPower(bytes[7]);
            sigfoxData.setChan(String.valueOf(chan));


        //COMMAND CHANNEL1 | CHANNEL2
        } else if (bytes[0] == 0x51 | bytes[0] == 0x59) {

            //COMMAND 51aaaaaaaa
            if (bytes[1] == (byte)0xAA & bytes[2] == (byte)0xAA & bytes[3] == (byte) 0xAA & bytes[4] == (byte)0xAA & bytes[5] == (byte)0xAA & bytes[6] == (byte)0xAA & bytes[7] == (byte)0xAA) {
                //detect magnet
                sigfoxData.setErrorMagnet(TypeError.MAGNETE);
                sigfoxData.setType(TypeMessage.COMMAND);
            }else {
                int chan = bytes[0] == 0x51 ? 1 : 2;
                //the message is sent 1 time when a command is entered into the modem, by bringing a magnet to the modem
                //(pressing a button for modems with a button). The command number is determined by the number of LED flashes
                //until the magnet is removed from the modem (the button is released)
                //32 bit
                if (chan == 1) {
                    initImpuls = initImpuls1;
                    impulsPerMCub = impulsPerMCub1;
                    initMCub = initMCub1;
                } else {
                    initImpuls = initImpuls2;
                    impulsPerMCub = impulsPerMCub2;
                    initMCub = initMCub2;
                }
                int newValue = (bytes[1] & 0xFF) | ((bytes[2] & 0xFF) << 8) | ((bytes[3] & 0xFF) << 16) | ((bytes[4] & 0xFF) << 24);
                setRawValue(id, String.valueOf(newValue), chan);
                sigfoxData.setValue((newValue - initImpuls) / impulsPerMCub + initMCub);
                sigfoxData.setType(TypeMessage.COMMAND);
                sigfoxData.setSendCounter(0);
                //int commandNumber = bytes[5];
                sigfoxData.setTemperature(bytes[6] & 0xFF);
                sigfoxData.setBatteryPower(bytes[7]);
                sigfoxData.setChan(String.valueOf(chan));
            }

        //HOURLY CHANNEL1 | CHANNEL2
        } else if (bytes[0] == (byte) 0x91 | bytes[0] == (byte) 0x99) {
            int chan = bytes[0]==(byte) 0x91?1:2;
            //32 bit
            if(chan == 1) {
                initImpuls = initImpuls1;
                impulsPerMCub = impulsPerMCub1;
                initMCub = initMCub1;
            }else{
                initImpuls = initImpuls2;
                impulsPerMCub = impulsPerMCub2;
                initMCub = initMCub2;
            }
            int newValue = (bytes[1] & 0xFF) | ((bytes[2] & 0xFF) << 8) | ((bytes[3] & 0xFF) << 16) | ((bytes[4] & 0xFF) << 24);
            setRawValue(id,String.valueOf(newValue),chan);
            sigfoxData.setValue((newValue - initImpuls)/impulsPerMCub+initMCub);
            sigfoxData.setType(TypeMessage.HOURLY);
            sigfoxData.setSendCounter(0);
            sigfoxData.setChan(String.valueOf(chan));

        //RESET CHANNEL1 | CHANNEL2
        } else if (bytes[0] == 0x41 | bytes[0] == 0x49) {
            int chan = bytes[0]==0x41?1:2;
            //32 bit
            if(chan == 1) {
                initImpuls = initImpuls1;
                impulsPerMCub = impulsPerMCub1;
                initMCub = initMCub1;
            }else{
                initImpuls = initImpuls2;
                impulsPerMCub = impulsPerMCub2;
                initMCub = initMCub2;
            }
            int newValue = (bytes[1] & 0xFF) | ((bytes[2] & 0xFF) << 8) | ((bytes[3] & 0xFF) << 16) | ((bytes[4] & 0xFF) << 24);
            setRawValue(id,String.valueOf(newValue),chan);
            sigfoxData.setValue((newValue - initImpuls)/impulsPerMCub+initMCub);
            sigfoxData.setType(TypeMessage.RESET);
            sigfoxData.setSendCounter(0);
            sigfoxData.setHardwareRev(bytes[5] & 0xFF);
            sigfoxData.setSoftwareRev(bytes[6] & 0x3F);
            int div = ((bytes[6] & 0xC0) >> 6);
            // '01' - div 1; '10','00' - div = 10; '11' - div = 100;
            if (div == 1) sigfoxData.setDiv("1");
            else if (div == 2) sigfoxData.setDiv("10");
            else if (div == 0) sigfoxData.setDiv("10");
            else if (div == 3) sigfoxData.setDiv("100");
            sigfoxData.setChan(String.valueOf(chan));
        }

        return sigfoxData;
    }
}
