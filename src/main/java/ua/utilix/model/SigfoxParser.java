package ua.utilix.model;


import ua.utilix.model.strategy.DefaultStrategy;
import ua.utilix.model.strategy.KamstrupStrategy;
import ua.utilix.model.strategy.Water5Strategy;

public class SigfoxParser {

    public SigfoxData getData(String id, String input, String dev, int sequence) throws Exception {
        SigfoxData sigfoxData = new SigfoxData();
        int [][]s;
        try {
            if (dev.equals("Water5")) sigfoxData.setStrategy(new Water5Strategy(sigfoxData));
            else if(dev.equals("Kamstrup")) sigfoxData.setStrategy(new KamstrupStrategy(sigfoxData));
            else sigfoxData.setStrategy(new DefaultStrategy(sigfoxData));
            sigfoxData = sigfoxData.getStrategy().parse(id, input, sequence);
        }catch (Exception ex) {
            throw new Exception("Error input data id: " + id);
        }
        return sigfoxData;
    }
}
