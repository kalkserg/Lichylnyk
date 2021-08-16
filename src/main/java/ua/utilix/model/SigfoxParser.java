package ua.utilix.model;


import ua.utilix.model.strategy.BoveStrategy;
import ua.utilix.model.strategy.DefaultStrategy;
import ua.utilix.model.strategy.KamstrupStrategy;
import ua.utilix.model.strategy.Water5Strategy;

public class SigfoxParser {

    public SigfoxData getData(String id, String input, String dev, int sequence, String dec, Water5 startValue) throws Exception {
        SigfoxData sigfoxData = new SigfoxData();
        try {
            if (dev.equals("Water5")) {
                sigfoxData.setStrategy(new Water5Strategy(sigfoxData));
                sigfoxData = sigfoxData.getStrategy().parse(id, input, sequence, startValue);
//                sigfoxData = sigfoxData.getStrategy().parse(id, input, sequence, startValue);
            }
            else if(dev.equals("Kamstrup")) {
                sigfoxData.setStrategy(new KamstrupStrategy(sigfoxData));
                sigfoxData = sigfoxData.getStrategy().parse(id, input, sequence, dec);
            }
            else if(dev.equals("Bove")) {
                sigfoxData.setStrategy(new BoveStrategy(sigfoxData));
                sigfoxData = sigfoxData.getStrategy().parse(id, input, sequence);
            }
            else {
                sigfoxData.setStrategy(new DefaultStrategy(sigfoxData));
                sigfoxData = sigfoxData.getStrategy().parse(id, input, sequence);
            }
        }catch (Exception ex) {
            throw new Exception("Error input data id: " + id);
        }
        return sigfoxData;
    }

}
