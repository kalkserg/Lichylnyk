package ua.utilix.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static ua.utilix.Handlers.Handler.DEVICES;

public enum BotState {

    StartRegUser {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Привіт!");
        }

        @Override
        public BotState nextState() {
            return EnterUserName;
        }
    },

    EnterUserName {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Введіть ім'я користувача:");
        }

        @Override
        public void handleInput(BotContext context) {
            String name = context.getInput();
            if(!name.contains(DEVICES)) {
                context.getUser().setUserName(name);
                next = EnterUserPhone;
            }else{
                sendMessage(context, "Неприпустиме ім'я користувача!");
                next = EnterUserName;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    EnterUserPhone {
        private BotState next;
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Введіть телефон користувача:");
        }

        @Override
        public void handleInput(BotContext context) {
            String str = context.getInput();
            if(BotState.checkTelNumber(str)) {
                context.getUser().setPhoneNumber(context.getInput());
                next = EndUserReg;
            }else{
                sendMessage(context, "Невірний телефон користувача!");
                next = EnterUserPhone;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    EndUserReg {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Користувач зареєстрований.");
        }

        @Override
        public BotState nextState() {
            return Done;
        }
    },

    Done {

        @Override
        public BotState nextState() {
            return Done;
        }
    },

    BeginDelUser {
        @Override
        public void enter(BotContext context) { sendMessage(context, "Введіть ідентифікатор для видалення:"); }

//        @Override
//        public void handleInput(BotContext context) {
//            context.getUser().setId(Long.parseLong(context.getInput()));
//            System.out.println("start removing context " +Long.parseLong(context.getInput()) );
//        }

        @Override
        public BotState nextState() {
            return Removing;
        }
    },

    Removing {
//        private BotState next;
        @Override
        public void enter(BotContext context) { }

        @Override
        public void handleInput(BotContext context) {
            context.getUser().setId(Long.parseLong(context.getInput()));
            //System.out.println("start removing context " +Long.parseLong(context.getInput()) );
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
        public BotState nextState() {
            return Removed;
        }
    },

    Removed {
        private BotState next;
        @Override
        public void enter(BotContext context) { sendMessage(context, "Yes:No"); }

        @Override
        public void handleInput(BotContext context) {
            context.getUser().setId(Long.parseLong(context.getInput()));
            //System.out.println("start removing context " +Long.parseLong(context.getInput()) );
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
        public BotState nextState() {
            return next;
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;

    BotState() {
        this.inputNeeded = true;
    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }
        //System.out.println("byId state "  + " id " + id);
        return states[id];
    }

    protected void sendMessage(BotContext botContext, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(botContext.getUser().getChatId()))
                .text(text)
                //.replyMarkup(context.getBot().getMainMenu())
                .build();
        try {
            botContext.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputNeeded() {
        //System.out.println("inputneeded "+inputNeeded);
        return inputNeeded;
    }


    public void handleInput(BotContext botContext) {
        // do nothing by default
    }


    public void enter(BotContext botContext) {};

    public abstract BotState nextState();

    private static boolean checkTelNumber(String telNumber) {
        String temp = telNumber;
        int length = temp.replaceAll("\\D", "").length();
        if (telNumber.contains("[a-aA-Z]")) {return false;}
        if (length==12) {
            return telNumber.matches("(^\\+{1})\\d*(\\(\\d{3}\\))?\\d*(\\-?\\d+)?\\-?\\d*\\d$");
        }
        else if (length==10) {
            return telNumber.matches("^(\\d|\\(\\d{3}\\))\\d*(\\-?\\d+)?\\-?\\d*\\d$");
        }
        return false;
    }

}
