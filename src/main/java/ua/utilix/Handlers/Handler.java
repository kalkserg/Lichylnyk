package ua.utilix.Handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.utilix.bot.ChatBot;

import java.util.ArrayList;
import java.util.List;

public interface Handler<T> {
    static final Logger LOGGER = LogManager.getLogger(ChatBot.class);

    static final String BROADCAST = "broadcast ";
    static final String DEVICES = "Список лічильників";
    static final String USERS = "Список юзерів";
//    static final String LOGOUT = "Вихід";

    public static final String START = "/start";

    public static final String ADD_REQUEST = "add";
    public static final String REMOVE_REQUEST = "del";
    public static final String NOTIFY_REQUEST = "notify";

    public static final String CALLBACK_LOGOUT_BUTTON = "Вихід";
    public static final String CALLBACK_LOGOUT_REQUEST = "/logout";

    public static final String CALLBACK_ADD_BUTTON = "Додати";
    public static final String CALLBACK_ADD_REQUEST = "/add";

    public static final String CALLBACK_MENU_BUTTON = "Меню";
    public static final String CALLBACK_MENU_REQUEST = "/menu";

    public static final String CALLBACK_VIEWALL_BUTTON = "Всі повідомлення";
    public static final String CALLBACK_VIEWALL_REQUEST = "/vall";

    public static final String CALLBACK_VIEWERR_BUTTON = "Тільки помилки";
    public static final String CALLBACK_VIEWERR_REQUEST = "/verr";

    public static final String CALLBACK_DEL_BUTTON = "Видалити";
    public static final String CALLBACK_DEL_REQUEST = "/del";

    public static final String CALLBACK_NOTIFY_BUTTON = "Підтвердити";
    public static final String CALLBACK_NOTIFY_REQUEST = "/confirm";


    public static final int MODE_BUTTON = 1;
    public static final int MODE_REED = 12;
    public static final int MODE_TRACK = 35;


    String delimeter = "\\s+";


    void choose(ChatBot chatBot, T t);

    default ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(DEVICES);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row1);
        markup.setKeyboard(rows);
        markup.setResizeKeyboard(true);
        return markup;
    }


    default InlineKeyboardMarkup getInlineMenu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        inlineKeyboardButton1 = InlineKeyboardButton.builder()
                .text(CALLBACK_MENU_BUTTON)
                .callbackData(CALLBACK_MENU_REQUEST)
                .build();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        rows.add(keyboardButtonsRow1);
        markup.setKeyboard(rows);
        return markup;
    }

    default InlineKeyboardMarkup getInlineSubMenu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        inlineKeyboardButton1 = InlineKeyboardButton.builder()
                .text(CALLBACK_ADD_BUTTON)
                .callbackData(CALLBACK_ADD_REQUEST)
                .build();
        inlineKeyboardButton2 = InlineKeyboardButton.builder()
                .text(CALLBACK_DEL_BUTTON)
                .callbackData(CALLBACK_DEL_REQUEST)
                .build();

        inlineKeyboardButton3 = InlineKeyboardButton.builder()
                .text(CALLBACK_VIEWALL_BUTTON)
                .callbackData(CALLBACK_VIEWALL_REQUEST)
                .build();

        inlineKeyboardButton4 = InlineKeyboardButton.builder()
                .text(CALLBACK_VIEWERR_BUTTON)
                .callbackData(CALLBACK_VIEWERR_REQUEST)
                .build();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        rows.add(keyboardButtonsRow1);
        rows.add(keyboardButtonsRow2);
        markup.setKeyboard(rows);
        return markup;
    }

    default InlineKeyboardMarkup getInlineStartMenu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1;
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        inlineKeyboardButton1 = InlineKeyboardButton.builder()
                .text(CALLBACK_ADD_BUTTON)
                .callbackData(CALLBACK_ADD_REQUEST)
                .build();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        rows.add(keyboardButtonsRow1);
        markup.setKeyboard(rows);
        return markup;
    }

    default InlineKeyboardMarkup getInlineAdminMenu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        inlineKeyboardButton1 = InlineKeyboardButton.builder()
                .text(CALLBACK_NOTIFY_BUTTON)
                .callbackData(CALLBACK_NOTIFY_REQUEST)
                .build();
        inlineKeyboardButton2 = InlineKeyboardButton.builder()
                .text(CALLBACK_DEL_BUTTON)
                .callbackData(CALLBACK_DEL_REQUEST)
                .build();

        inlineKeyboardButton3 = InlineKeyboardButton.builder()
                .text(CALLBACK_LOGOUT_BUTTON)
                .callbackData(CALLBACK_LOGOUT_REQUEST)
                .build();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        rows.add(keyboardButtonsRow1);
        rows.add(keyboardButtonsRow2);
        markup.setKeyboard(rows);
        return markup;
    }

    default InlineKeyboardMarkup getInlineListMenu(List list, String callback) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i<list.size(); i++) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(InlineKeyboardButton.builder()
                    .text(String.valueOf(list.get(i)))
                    .callbackData(String.valueOf(callback + " " + list.get(i).toString().split(delimeter)[0]))
                    .build());
            rows.add(keyboardButtonsRow);
        }

//        keyboardButtonsRow1.add(inlineKeyboardButton1);
//        keyboardButtonsRow1.add(inlineKeyboardButton2);
//        rows.add(keyboardButtonsRow1);
        markup.setKeyboard(rows);
        return markup;
    }

    default void sendMessage(ChatBot chatBot, Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                //.replyMarkup(getMainMenu())
                .build();
        if (replyKeyboardMarkup != null) message.setReplyMarkup(replyKeyboardMarkup);
        if (inlineKeyboardMarkup != null) message.setReplyMarkup(inlineKeyboardMarkup);
        message.setParseMode("HTML");
        try {
            chatBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    default void sendEditMessage(ChatBot chatBot, Long chatId, EditMessageText text, ReplyKeyboardMarkup replyKeyboardMarkup, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("")
                //.replyMarkup(getMainMenu())
                .build();
        if (replyKeyboardMarkup != null) message.setReplyMarkup(replyKeyboardMarkup);
        if (inlineKeyboardMarkup != null) message.setReplyMarkup(inlineKeyboardMarkup);
        message.setParseMode("HTML");
        try {
            chatBot.execute(text);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
