package ua.utilix.processor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.utilix.Handlers.CallbackQueryHandler;
import ua.utilix.Handlers.MessageHandler;
import ua.utilix.bot.ChatBot;

@Component
public class DefaultProcessor implements Processor{
    private final CallbackQueryHandler callbackQueryHandler;
    private final MessageHandler messageHandler;

    public DefaultProcessor(CallbackQueryHandler callbackQueryHandler, MessageHandler messageHandler) {
        this.callbackQueryHandler = callbackQueryHandler;
        this.messageHandler = messageHandler;
    }

    @Override
    public void executeMessage(ChatBot chatBot, Message message ){
        messageHandler.choose(chatBot, message);
    }

    @Override
    public void executeCallBackQuery(ChatBot chatBot, CallbackQuery callbackQuery) {
        callbackQueryHandler.choose(chatBot, callbackQuery);
    }
}
