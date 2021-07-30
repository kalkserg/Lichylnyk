package ua.utilix.processor;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.utilix.bot.ChatBot;

public interface Processor {

    void executeMessage(ChatBot chatBot, Message messsage);

    void executeCallBackQuery(ChatBot chatBot, CallbackQuery callbackQuery);

    default void proc(ChatBot chatBot, Update update) {
        if (update.hasMessage()) {
            executeMessage(chatBot, update.getMessage());
        } else if (update.hasCallbackQuery()) {
            executeCallBackQuery(chatBot, update.getCallbackQuery());
        }
    }
}
