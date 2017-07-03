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
   *
   * @param message input message
   * @param state current state
   * @return AskForChefKey.STATE
   */
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(AskForChefKey.STATE);
  }

  /**
   * Updates the key as provided.
   *
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
  public void handleCommand(final Message message, final String state,
      final List<String> parameters,
      final MorseBot morseBot) {
    final String keyPath = message.getText();

    try {
      chefService.updateKey(keyPath);
    } catch (MorseBotException e) {
      morseBot.sendMessage(e.getMessage(), message.getChatId().toString());
    }

    morseBot.sendMessage("Chef Key Updated", message.getChatId().toString());

  }

  /**
   * Null - end of the process.
   *
   * @param message message
   * @param command command
   * @return Null - Done
   */
  public String getNewState(final Message message, final String command) {
    return null;
  }

  /**
   * Null - No new parameters.
   *
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @return null.
   */
  public List<String> getNewStateParams(final Message message, final String state, final List<String> parameters) {
    return null;
  }
}
