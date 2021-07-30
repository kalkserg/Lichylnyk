package ua.utilix.bot;

import ua.utilix.Handlers.Handler;
import ua.utilix.model.Device;
import ua.utilix.model.User;

public class DeviceContext {
    private final ChatBot bot;
    private final User user;
    private final Device device;
    private final String input;

    public static DeviceContext of(Handler handler, ChatBot bot, User user, Device device, String text) {
        return new DeviceContext(handler, bot, user, device, text);
    }

    private DeviceContext(Handler handler, ChatBot bot, User user, Device device, String input) {
        this.bot = bot;
        this.user = user;
        this.device = device;
        this.input = input;
    }

    public ChatBot getBot() {
        return bot;
    }

    public Device getDevice() {
        return device;
    }

    public User getUser() {
        return user;
    }

    public String getInput() {
        return input;
    }
}
