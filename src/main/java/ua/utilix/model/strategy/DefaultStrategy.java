package ua.utilix.model.strategy;

import org.springframework.stereotype.Component;
import sigfox.Sigfox;
import ua.utilix.model.SigfoxData;

public class DefaultStrategy implements Sigfox {

    SigfoxData sigfoxData;

    public DefaultStrategy(SigfoxData sigfoxData) {
        this.sigfoxData = sigfoxData;
        sigfoxData.setErrorBurst(TypeError.NOERROR);
        sigfoxData.setErrorLeak(TypeError.NOERROR);
        sigfoxData.setErrorDry(TypeError.NOERROR);
        sigfoxData.setErrorReverse(TypeError.NOERROR);
        sigfoxData.setErrorMagnet(TypeError.NOERROR);
        sigfoxData.setErrorTamper(TypeError.NOERROR);
        sigfoxData.setErrorFreezing(TypeError.NOERROR);
        sigfoxData.setErrorBatteryAlarm(TypeError.NOERROR);
        sigfoxData.setErrorOverRange(TypeError.NOERROR);
        sigfoxData.setErrorTemperatureAlarm(TypeError.NOERROR);
        sigfoxData.setErrorEEPROM(TypeError.NOERROR);
        sigfoxData.setErrorSensorbreak(TypeError.NOERROR);
        sigfoxData.setErrorShortcircuit(TypeError.NOERROR);
        sigfoxData.setErrorTemperatureless(TypeError.NOERROR);
        sigfoxData.setErrorTemperaturemore(TypeError.NOERROR);
        sigfoxData.setValue(0);
        sigfoxData.setType(null);
        sigfoxData.setSendCounter(0);
    }

    public static byte[] asBytes(String s) {
        String tmp;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            tmp = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte) (Integer.parseInt(tmp, 16) & 0xff);
        }
        return b;                                            //return bytes
    }

    public static String asString(byte[] b) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[b.length * 2];
        for (int j = 0; j < b.length; j++) {
            int v = b[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static boolean isBitSet(byte num, int position) {
        return (((num >> (position - 1)) & 1) == 1);
    }
}
