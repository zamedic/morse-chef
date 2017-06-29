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
public class UpdateServer implements Commandlet {

  /**
   * The Chef service.
   */
  @Inject
  private transient ChefService chefService;

  /**
   * True when previous call was from Server
   * @param message message
   * @param state state
   * @return true if this can execute
   */
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(Server.ChefServerState);
  }

  /**
   * Update the server with the provided details
   * @param message message
   * @param state state
   * @param parameters parameters
   * @param morseBot morse bot
   */
  public void handleCommand(final Message message, final String state, final List<String> parameters,
      final MorseBot morseBot) {
    final String server = message.getText();
    try {
      chefService.updateServer(server);
      morseBot.sendMessage("Updated Server", message.getChatId().toString());
    } catch (MorseBotException e) {
      morseBot.sendMessage(e.getMessage(),message.getChatId().toString());
    }
  }

  /**
   * None returned fromt his class
   * @param message message
   * @param command command
   * @return null
   */
  public String getNewState(final Message message, final String command) {
    return null;
  }

  /**
   * Null
   * @param message message
   * @param state message
   * @param parameters message
   * @return null
   */
  public List<String> getNewStateParams(final Message message, final String state, final List<String> parameters) {
    return null;
  }
}
