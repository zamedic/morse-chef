package com.marcarndt.morse.command.commandlets.chefconfig;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.command.commandlet.Commandlet;
import com.marcarndt.morse.service.ChefService;
import com.marcarndt.morse.telegrambots.api.objects.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class UpdateKey implements Commandlet {

  @Inject
  ChefService chefService;

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(Key.ChefKeyState);
  }

  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    String keyPath = message.getText();

    try {
      chefService.updateKey(keyPath);
    } catch (MorseBotException e) {
      morseBot.sendMessage(e.getMessage(), message.getChatId().toString());
    }

    morseBot.sendMessage("Key Updated", message.getChatId().toString());

  }

  public String getNewState(Message message, String command) {
    return null;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
