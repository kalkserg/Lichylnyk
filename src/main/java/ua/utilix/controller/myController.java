package ua.utilix.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sigfox.Sigfox;
import ua.utilix.model.Device;
import ua.utilix.model.Kamstrup;
import ua.utilix.model.SigfoxData;
import ua.utilix.model.SigfoxParser;
import ua.utilix.service.DeviceService;
import ua.utilix.service.KamstrupService;
import ua.utilix.service.SendMessageService;
import ua.utilix.service.UserService;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Controller
public class myController {

    private UserService userService;
    private DeviceService deviceService;
//    private KamstrupService kamstrupService;
    private SendMessageService sendMessageService;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

//    @Autowired
//    public void setKamstrupService(KamstrupService kamstrupService) {
//        this.kamstrupService = kamstrupService;
//    }

    String text = "";
//    @RequestMapping(value = "/greeting")
//    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "World!") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }

    //    @RequestMapping()
    /*
    @PostMapping()
    public void postBody(@RequestBody(required = false) String str, Model model) {
        System.out.println("post  " + str);

        if (str!=null) { text = text + str + "\n";model.addAttribute("str", text);}
        else model.addAttribute("str", "EMPTY");
        //return "sample";
    }

    //{"device" : "{device}","time" : "{time}","data" : "{data}","seqNumber" : "{seqNumber}","lqi" : "{lqi}","operatorName" : "{operatorName}"}

    @GetMapping()
    public void getBody(@RequestBody(required = false) String str, Model model) {
        System.out.println("get" + str);
        model.addAttribute("str", text);

        //HelloWorldBot helloWorldBot = new HelloWorldBot();

        //return "byby";
    },

     */

    String messageIn ="";
    String messageOut ="";


    @PostMapping(produces = "application/json")
    public ResponseEntity<String> postBody(@RequestBody(required = false) String str, Model model) {
        System.out.println("post  " + str);
//        SendMessage sm = new SendMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JSONObject obj = new JSONObject(str);

        String sigfoxId = obj.getString("device");
        String unixTime = obj.getString("time");
        String data = obj.getString("data");
        int seqNumber = obj.getInt("seqNumber");

        SigfoxData sigfoxData;
        SigfoxParser sigfoxParser = new SigfoxParser();

        messageIn = messageIn + str;
//        System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
//        Kamstrup k = kamstrupService.findDecodeKey("11");
//        System.out.println(k.getSigfoxId() + " " + k.getDecodeKey());
//
//        k = kamstrupService.findSecureKey("AA");
//        System.out.println(k.getSigfoxId() + " " + k.getSecureKey());

        Device[] devices = null;
        try {
            devices = deviceService.findBySigfoxId(sigfoxId);
        } catch (Exception e) {
            System.out.println("There is not user");
        }

        try{
            for (Device device : devices) {
                String sigfoxName = device.getSigfoxName();
                Long chatId = device.getChatId();
//                final String formattedDtm = Instant.ofEpochSecond(Long.parseLong(unixTime))
//                        .atZone(ZoneId.of("GMT+3"))
//                        .format(formatter);
                sigfoxData = sigfoxParser.getData(sigfoxId, data, device.getProtocol(), seqNumber);

                if (device.getNotified()) {
                    //Err
                    if (sigfoxData.getErrorBurst().equals(Sigfox.TypeError.BURST) ||
                            sigfoxData.getErrorDry().equals(Sigfox.TypeError.DRY) ||
                            sigfoxData.getErrorLeak().equals(Sigfox.TypeError.LEAK) ||
                            sigfoxData.getErrorMagnet().equals(Sigfox.TypeError.MAGNETE) ||
                            sigfoxData.getErrorReverse().equals(Sigfox.TypeError.REVERSE)) {

                        String user = "6107a6e641758161bc41ddd0";
                        String pwd = "abea5dd6ead1c6e14b6ada4a02bd9e2e";
                        Double latitude = 0.;
                        Double longitude = 0.;
                        CredentialsProvider provider = new BasicCredentialsProvider();
                        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, pwd);
                        provider.setCredentials(AuthScope.ANY, credentials);
                        HttpClient client = HttpClientBuilder.create()
                                .setDefaultCredentialsProvider(provider)
                                .build();
                        HttpResponse response = client.execute(new HttpGet("https://api.sigfox.com/v2/devices/" + sigfoxId));
                        //HttpResponse response = client.execute(new HttpGet("https://api.sigfox.com/v2/devices/32FFCE"));
                        int statusCode = response.getStatusLine().getStatusCode();
                        System.out.println(statusCode);
                        if(statusCode==200) {
                            // CONVERT RESPONSE TO STRING
                            String result = EntityUtils.toString(response.getEntity());
                            JSONObject objResponse = new JSONObject(result);
                            try {
                                latitude = objResponse.getJSONObject("location").getDouble("lat");
                                longitude = objResponse.getJSONObject("location").getDouble("lng");
                            } catch (Exception ex) {
                                //do nothing
                            }
                        }

                        String coordinateHref = latitude!=0&&longitude!=0?"\n<a href=\"https://www.google.com/maps/place/" + latitude + "," + longitude + "\"><b>Показати положення на мапі </b></a>":"";
                        String message = "\u26A0" + sigfoxData.toString() + coordinateHref;
                        System.out.println(message);
                        sendMessageService.sending(message, device.getChatId());
                    }else {
                        //All messages No err
                        if (device.getAllMessage() == true) {
                            sendMessageService.sending("\uD83D\uDCAC" + sigfoxData.toString(), device.getChatId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error get data!");
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @GetMapping()
    public String getBody(@RequestBody(required = false)  String str, Model model) {
        System.out.println("GET "+ messageIn);
        System.out.println("GET "+ messageOut);
//        model.addAttribute("str", str);
//        text = text + "GET " + str + "\n";
        model.addAttribute("str", messageIn + messageOut);
        return "byby";
    }


    private byte[] intToByte(int num){
        byte[] bytes = new byte[4];
        String str = String.valueOf(num);
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) str.charAt(i);
            //bytes[i] = (byte)(num >>> (i * 8));
            System.out.println(num + " " +bytes[i]);
        }
        return bytes;
    }
}
