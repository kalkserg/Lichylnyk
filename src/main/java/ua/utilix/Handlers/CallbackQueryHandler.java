package ua.utilix.Handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ua.utilix.bot.ChatBot;
import ua.utilix.bot.DeviceContext;
import ua.utilix.bot.DeviceState;
import ua.utilix.model.Device;
import ua.utilix.model.User;
import ua.utilix.service.DeviceService;
import ua.utilix.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {
    @Autowired
    UserService userService;
    @Autowired
    DeviceService deviceService;

    @Override
    public void choose(ChatBot chatBot, CallbackQuery callbackQuery) {
//        System.out.println("------------------------------------------------------------ ");
//        System.out.println("callbackQuery getid " + callbackQuery.getId());
//        System.out.println("callbackQuery getData " + callbackQuery.getData());
//        System.out.println("callbackQuery getChatInstance " + callbackQuery.getChatInstance());
//        System.out.println("callbackQuery getInlineMessageId " + callbackQuery.getInlineMessageId());
//        System.out.println("callbackQuery getMessage " + callbackQuery.getMessage().getMessageId());
//        System.out.println("------------------------------------------------------------ ");
        Device device = null;
        DeviceState devstate = null;
        DeviceContext devcontext = null;

        final long chatId = callbackQuery.getMessage().getChatId();
        User user = userService.findByChatId(chatId);

        if (callbackQuery.getData().equals(CALLBACK_DEL_REQUEST)) {
            Integer messageId = callbackQuery.getMessage().getMessageId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
            editMessageText.setMessageId(messageId);
            editMessageText.setText(" ");
            System.out.println(callbackQuery.getMessage().getText());
            sendEditMessage(chatBot, chatId, editMessageText, null, null);
            ArrayList<String> list;
            if(user.getAdmin()) {
                list = getListAdminDevices(chatBot, deviceService);
            } else {
                list = getListDevices(chatBot, chatId, deviceService);
            }
            sendMessage(chatBot, chatId, "Натиснуть для видалення", getMainMenu(), getInlineListMenu(list, CALLBACK_DEL_REQUEST));
        } else if (callbackQuery.getData().contains(CALLBACK_DEL_REQUEST)) {
            String[] subStr = callbackQuery.getData().split(delimeter);
            long Id;
            Id = Long.parseLong(subStr[1]);
            if(user.getAdmin()){
                device = deviceService.findById(Id);
            } else {
                device = deviceService.findByIdAndChatId(Id, chatId);
            }
            if(device!=null) {
                sendMessage(chatBot, chatId, "Видалено " + device.getSigfoxName() + " sigfoxId: " + device.getSigfoxId(), null, null);
                deviceService.delDevice(device);
                device = null;
            }else{
                sendMessage(chatBot, chatId, "Видалення неможливе!", null, null);
            }

        } else if (callbackQuery.getData().equals(CALLBACK_ADD_REQUEST)) {
            devstate = DeviceState.StartRegDevice;
            device = new Device(chatId, devstate.ordinal());
            deviceService.addDevice(device);

            devcontext = DeviceContext.of(this, chatBot, user, device, "text");
            devstate.enter(devcontext, this);

            devstate = devstate.nextState();
            devstate.enter(devcontext, this);
            device.setStateId(devstate.ordinal());
            deviceService.updateDevice(device);

        } else if (callbackQuery.getData().equals(CALLBACK_VIEWALL_REQUEST)) {
            Integer messageId = callbackQuery.getMessage().getMessageId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
            editMessageText.setMessageId(messageId);
            editMessageText.setText(" ");
            System.out.println(callbackQuery.getMessage().getText());
            sendEditMessage(chatBot, chatId, editMessageText, null, null);
            ArrayList<String> list;
            list = getListErrMessageDevices(chatBot, chatId, deviceService);
            System.out.println(list.size());
            if(list.size()>0)sendMessage(chatBot, chatId, "Натиснуть для показу всіх повідомлень", getMainMenu(), getInlineListMenu(list,CALLBACK_VIEWALL_REQUEST));
            else sendMessage(chatBot, chatId, "Віконано", getMainMenu(), null);
        } else if (callbackQuery.getData().contains(CALLBACK_VIEWALL_REQUEST)) {
            String[] subStr = callbackQuery.getData().split(delimeter);
            long Id;
            Id = Long.parseLong(subStr[1]);
            device = deviceService.findById(Id);
            device.setAllMessage(true);
            sendMessage(chatBot, chatId, "Виконано " + device.getSigfoxName() + " sigfoxId: " + device.getSigfoxId(), null, null);
            deviceService.updateDevice(device);

        } else if (callbackQuery.getData().equals(CALLBACK_VIEWERR_REQUEST)) {
            Integer messageId = callbackQuery.getMessage().getMessageId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
            editMessageText.setMessageId(messageId);
            editMessageText.setText(" ");
            System.out.println(callbackQuery.getMessage().getText());
            sendEditMessage(chatBot, chatId, editMessageText, null, null);
            ArrayList<String> list;
            list = getListAllMessageDevices(chatBot, chatId, deviceService);
            if(list.size()>0)sendMessage(chatBot, chatId, "Натиснуть для показу помилок", getMainMenu(), getInlineListMenu(list,CALLBACK_VIEWERR_REQUEST));
            else sendMessage(chatBot, chatId, "Віконано", getMainMenu(), null);
        } else if (callbackQuery.getData().contains(CALLBACK_VIEWERR_REQUEST)) {
            String[] subStr = callbackQuery.getData().split(delimeter);
            long Id;
            Id = Long.parseLong(subStr[1]);
            device = deviceService.findById(Id);
            device.setAllMessage(false);
            sendMessage(chatBot, chatId, "Виконано " + device.getSigfoxName() + " sigfoxId: " + device.getSigfoxId(), null, null);
            deviceService.updateDevice(device);

        } else if (callbackQuery.getData().equals(CALLBACK_MENU_REQUEST)) {
            sendMessage(chatBot, chatId, "Меню:", getMainMenu(), getInlineSubMenu());

        }else if (callbackQuery.getData().equals(CALLBACK_NOTIFY_REQUEST)) {
            if (user.getAdmin()) {
                Integer messageId = callbackQuery.getMessage().getMessageId();
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
                editMessageText.setMessageId(messageId);
                editMessageText.setText(" ");
                System.out.println(callbackQuery.getMessage().getText());
                sendEditMessage(chatBot, chatId, editMessageText, null, null);
                ArrayList<String> list;
                list = getListAdminNoValidDevices(chatBot, deviceService);
                if(list.size()>0)sendMessage(chatBot, chatId, "Натиснуть для підтвердження", getMainMenu(), getInlineListMenu(list,CALLBACK_NOTIFY_REQUEST));
                else sendMessage(chatBot, chatId, "Все підтверджено", getMainMenu(), null);
            }
        } else if (callbackQuery.getData().contains(CALLBACK_NOTIFY_REQUEST)) {
            String[] subStr = callbackQuery.getData().split(delimeter);
            long Id;
            Id = Long.parseLong(subStr[1]);
            device = deviceService.findById(Id);
            device.setNotified(true);
            sendMessage(chatBot, chatId, "Підтверджено " + device.getSigfoxName() + " sigfoxId: " + device.getSigfoxId(), null, null);
            deviceService.updateDevice(device);

        } else if (callbackQuery.getData().equals(CALLBACK_LOGOUT_REQUEST)) {
            if(user.getAdmin()){
                user.setAdmin(false);
                sendMessage(chatBot, user.getChatId(),"Бувай, адміністратор!", getMainMenu(), null);
                userService.updateUser(user);
            }
        }
    }

//    private void printed(String[] str) {
//        for(int i=0;i<str.length; i++) {
//            System.out.println(i + " " + str[i]);
//        }
//    }

    private ArrayList<String> getListDevices(ChatBot chatBot, long chatId, DeviceService deviceService) {

        List<Device> devices = Arrays.asList(deviceService.findByChatId(chatId));
        ArrayList<String> list = new ArrayList<>();

        devices.forEach(device ->
                list.add(device.getId().toString() + " - " + device.getSigfoxName() + " (sigfoxId: " + device.getSigfoxId() + ")")
        );
        return list;
    }

        private ArrayList<String> getListAdminDevices(ChatBot chatBot, DeviceService deviceService) {

        List<Device> devices = deviceService.findAllDevices();
        ArrayList<String> list = new ArrayList<>();

        devices.forEach(device ->
                list.add(device.getId().toString() + " - " + device.getSigfoxName() +
                        " (sigfoxId: " + String.format("%8.8s", device.getSigfoxId()).replace(' ', '0') +
                        " chatId: " + device.getChatId() +
                        " user: " + userService.findByChatId(device.getChatId()).getUserName() + ")")
        );
        return list;
    }

    private ArrayList<String> getListAdminNoValidDevices(ChatBot chatBot, DeviceService deviceService) {

        List<Device> devices = deviceService.findNewDevices();
        ArrayList<String> list = new ArrayList<>();

        devices.forEach(device ->
                list.add(device.getId().toString() + " - " + device.getSigfoxName() +
                        " sigfoxId: " +  String.format("%8.8s", device.getSigfoxId()).replace(' ', '0') +
                        " chatId: " + device.getChatId() +
                        " user: " + userService.findByChatId(device.getChatId()).getUserName() + ")")
        );
        return list;
    }

    private ArrayList<String> getListAllMessageDevices(ChatBot chatBot, long chatId, DeviceService deviceService) {

        List<Device> devices = deviceService.findByChatIdAllMessageDevices(chatId);//Arrays.asList(deviceService.findByChatId(chatId));
        ArrayList<String> list = new ArrayList<>();

        devices.forEach(device ->
                list.add(device.getId().toString() + " - " + device.getSigfoxName() + " (sigfoxId: " + device.getSigfoxId() + ")")
        );
        return list;
    }

    private ArrayList<String> getListErrMessageDevices(ChatBot chatBot, long chatId, DeviceService deviceService) {

        List<Device> devices = deviceService.findByChatIdErrMessageDevices(chatId);//Arrays.asList(deviceService.findByChatId(chatId));
        ArrayList<String> list = new ArrayList<>();

        devices.forEach(device ->
                list.add(device.getId().toString() + " - " + device.getSigfoxName() + " (sigfoxId: " + device.getSigfoxId() + ")")
        );
        return list;
    }

}
