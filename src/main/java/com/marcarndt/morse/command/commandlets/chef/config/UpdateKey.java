package com.marcarndt.morse.command.commandlets.chef.config;

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

  /**
   * The Chef service.
   */
  @Inject
  private transient ChefService chefService;

  /**
   * checks if the current process can be executed.
   * @param message input message
   * @param state current state
   * @return Key.ChefKeyState
   */
  public boolean canHandleCommand(Message message, String state) {
    return state.equals(Key.ChefKeyState);
  }

  /**
   * Updates the key as provided.
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
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

  /**
   * Null - end of the process.
   * @param message message
   * @param command command
   * @return Null - Done
   */
  public String getNewState(Message message, String command) {
    return null;
  }

  /**
   * Null - No new parameters.
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @return null.
   */
  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
