package ua.utilix.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.utilix.processor.Processor;

@Component
@PropertySource("classpath:telegram.properties")
public class ChatBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LogManager.getLogger(ChatBot.class);

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final Processor processor;

    public ChatBot(Processor processor) {
        this.processor = processor;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        processor.proc(this,update);
    }

}
