package ru.micron.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherBotService extends TelegramLongPollingBot {

  private final WeatherService weatherService;

  @Value("${app.bot.username}")
  private String username;

  @Value("${app.bot.token}")
  private String token;

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    try {
      execute(getMessage(update));
    } catch (TelegramApiException e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }

  private SendMessage getMessage(Update update) {
    SendMessage sendMessage = new SendMessage();
    Message message = update.getMessage();
    sendMessage.setChatId(message.getChatId().toString());

    String messageText = message.getText();

    if (messageText.contains("/start")) {
      sendMessage.setText("Введите название города");
    } else {
      try {
        sendMessage.setText(weatherService.getWeather(messageText));
      } catch (Exception e) {
        sendMessage.setText(
            "Проверьте правильность введенного города.\nДля начала отправьте /start");
      }
    }
    return sendMessage;
  }
}
