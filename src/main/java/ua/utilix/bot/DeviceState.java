package ua.utilix.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.utilix.Handlers.Handler;

import java.util.Locale;

public enum DeviceState {

    StartEmptyRegDevice {
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) {
            String name = deviceContext.getUser().getUserName();
            sendMessage(handler, deviceContext, "Привіт" + (name==null?"":(", " + name)) + "!\n У вас немає жодного пристрою." );
        }

        @Override
        public DeviceState nextState() {
            return EnterSigfoxName;
        }
    },

    StartRegDevice {
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) {
            String name = deviceContext.getUser().getUserName();
            sendMessage(handler, deviceContext, "Привіт" + (name==null?"":(", " + name)) + "!" );
        }

        @Override
        public DeviceState nextState() {
            return EnterSigfoxName;
        }
    },

    EnterSigfoxName {
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) {
            sendMessage(handler,deviceContext, "Введіть нік для лічильника:");
        }

        @Override
        public void handleInput(DeviceContext deviceContext, Handler handler) {
            deviceContext.getDevice().setSigfoxName(deviceContext.getInput());
        }

        @Override
        public DeviceState nextState() {
            return EnterSigfoxID;
        }
    },

    EnterSigfoxID {
        private DeviceState next;
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) {
            sendMessage(handler, deviceContext, "Введіть ідентифікатор Sigfox:");
        }

        @Override
        public void handleInput(DeviceContext deviceContext, Handler handler) {
            String str = deviceContext.getInput().toUpperCase(Locale.ROOT);
            if(str.matches("[0-9A-F]{1,8}")) {
                str = str.replaceFirst ("^0*", "");
                deviceContext.getDevice().setSigfoxId(str);
                next = EnterMode;
            }else{
                sendMessage(handler, deviceContext, "Невірний ідентифікатор Sigfox!");
                next = EnterSigfoxID;
            }
            //deviceContext.getDevice().setNotified(true);
        }

        @Override
        public DeviceState nextState() {
            return next;
        }
    },

    EnterMode {
        private DeviceState next;
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) {
            sendMessage(handler, deviceContext, "Введіть тип личильника: 1-Kamstrup, 2-Water5, 3-Bove");
        }

        @Override
        public void handleInput(DeviceContext deviceContext, Handler handler) {
            String str = deviceContext.getInput().toUpperCase(Locale.ROOT);
            deviceContext.getDevice().setAllMessage(false);
            if(str.matches("1")) {
                deviceContext.getDevice().setProtocol("Kamstrup");
                next = Registred;
            }else
            if(str.matches("2")) {
                deviceContext.getDevice().setProtocol("Water5");
                next = Registred;
            }else
            if(str.matches("3")) {
                deviceContext.getDevice().setProtocol("Bove");
                next = Registred;
            }else{
                sendMessage(handler, deviceContext, "Невірний тип личильника!");
                next = EnterMode;
            }
            //deviceContext.getDevice().setNotified(true);
        }

        @Override
        public DeviceState nextState() {
            return next;
        }
    },

    Registred {
        private DeviceState next;
        @Override
        public void enter(DeviceContext context, Handler handler) {
            sendMessage(handler, context, "Пристрій зареєстровано.");
        }

        @Override
        public DeviceState nextState() {
            return Done;
        }
    },

    Done {

        @Override
        public DeviceState nextState() {
            return Done;
        }
    },

    BeginRemoving {
        @Override
        public DeviceState nextState() {
            return Done;
        }
    },

    Removing {
//        private BotState next;
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) { sendMessage(handler,deviceContext, "Removed");}

        @Override
        public void handleInput(DeviceContext deviceContext, Handler handler) {
            deviceContext.getDevice().setId(Long.parseLong(deviceContext.getInput()));
            //System.out.println("start removing context " + Long.parseLong(deviceContext.getInput()) );
        }

//        @Override
//        public void handleInput(BotContext context) {
//            if (context.getInput().toLowerCase(Locale.ROOT).contains("y")) {
//                next = Done;
//                sendMessage(context, "Deleted!");
//            } else {
//                next = BeginRemoving;
//                sendMessage(context, "Begin removing");
//            }
//        }

        @Override
        public DeviceState nextState() {
            return Done;
        }
    },

    Removed {
        private DeviceState next;
        @Override
        public void enter(DeviceContext deviceContext, Handler handler) { sendMessage(handler,deviceContext, "Yes:No"); }

        @Override
        public void handleInput(DeviceContext deviceContext, Handler handler) {
            deviceContext.getDevice().setId(Long.parseLong(deviceContext.getInput()));
            //System.out.println("start removing context " +Long.parseLong(deviceContext.getInput()) );
        }

//        @Override
//        public void handleInput(BotContext context) {
//            if (context.getInput().toLowerCase(Locale.ROOT).contains("y")) {
//                next = Done;
//                sendMessage(context, "Deleted!");
//            } else {
//                next = BeginRemoving;
//                sendMessage(context, "Begin removing");
//            }
//        }

        @Override
        public DeviceState nextState() {
            return next;
        }
    };

    //private static BotState[] states;

    private static DeviceState[] states;
//    private static Map<Long, DeviceState> states;
    private final boolean inputNeeded;

    DeviceState() {
        this.inputNeeded = true;
    }

    DeviceState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static DeviceState getInitialState() {
        return byId(0);
    }

    public static DeviceState byId(int id) {
        if (states == null) {
            states = DeviceState.values();
        }
        //System.out.println("byId state "  + " id " + id);
        return states[id];

//        if (states == null) {
//            states = new HashMap<>();
//        }
//        //System.out.println("byId state " + states[id] + " id " + id);
//        return states.get(id);
    }

    protected void sendMessage(Handler handler, DeviceContext deviceContext, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(deviceContext.getDevice().getChatId()))
                .text(text)
                .replyMarkup(handler.getMainMenu())
                .build();
        try {
            deviceContext.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputNeeded() {
        System.out.println("device inputneeded "+inputNeeded);
        return inputNeeded;
    }

    public void handleInput(DeviceContext context, Handler handler) {
        // do nothing by default
        //System.out.println("need next");
    }

    public void enter(DeviceContext context, Handler handler) {};

    public abstract DeviceState nextState();


}
