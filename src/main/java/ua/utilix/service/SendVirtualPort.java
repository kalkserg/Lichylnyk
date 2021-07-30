package ua.utilix.service;

import com.fazecast.jSerialComm.SerialPort;


public final class SendVirtualPort {

    private static SendVirtualPort sendVirtualPort;
    private SerialPort serialPort;
    private int baudRate;
    private final byte[] commandE = {0x35,0x30,0x31,0x30,0x20,0x31,0x38,0x37,0x37,0x37,0x37,0x45,0x31,0x32,0x30,0x30,0x31,0x30,0x30,0x31,0x14};
    private final byte[] commandR = {0x35,0x30,0x31,0x30,0x20,0x31,0x38,0x37,0x37,0x37,0x37,0x52,0x31,0x32,0x30,0x30,0x32,0x30,0x30,0x31,0x14};
    private final byte[] readBuffer = {};

    private SendVirtualPort(String com, int baudRate) {
        this.serialPort = SerialPort.getCommPort(com);
        this.serialPort.setParity(SerialPort.EVEN_PARITY);
        this.serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        this.serialPort.setNumDataBits(8);
        this.serialPort.setBaudRate(baudRate);
        this.serialPort.openPort();
    }

    public static SendVirtualPort getInstance(String com, int baudRate) {
        if (sendVirtualPort == null ) {
            sendVirtualPort = new SendVirtualPort(com, baudRate);
        }
        return sendVirtualPort;
    }

    public static SendVirtualPort getInstance() {
        return sendVirtualPort;
    }

    private boolean send(byte[] bytes){
        try {
            int numRead = serialPort.writeBytes(bytes, bytes.length);
            System.out.print(" Send " + numRead + " bytes ");
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    private boolean read(){
        try {
            int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
            System.out.print(" Read " + numRead + " bytes ");
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public void startProcessSendToVirtualPort() throws InterruptedException {
        boolean state = send(commandE);
        Thread.sleep(100);
        if(!state) System.exit(0);

    }
}
