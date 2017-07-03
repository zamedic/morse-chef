package com.marcarndt.morse.command.commandlets.chef.config;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.command.ChefConfigure;
import com.marcarndt.morse.command.commandlet.Commandlet;
import com.marcarndt.morse.telegrambots.api.objects.Message;

import java.util.List;
import javax.ejb.Stateless;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class AskForChefKey implements Commandlet {

  /**
   * The constant STATE.
   */
  public static final String STATE = "ChefKey";

  /**
   * Checks if the commandlet can be executed
   * @param message input message
   * @param state current state
   * @return true if this is a AskForChefKey command for {@link ChefConfigure}
   */
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.KEY);
  }

  /**
   * Asks the user for the key path
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
  public void handleCommand(final Message message, final String state, final List<String> parameters,
      final MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Paste Key Value");
  }

  /**
   * STATE
   * @param message input message
   * @param command command
   * @return STATE
   */
  public String getNewState(final Message message, final String command) {
    return STATE;
  }

  /**
   * Null
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @return Null
   */
  public List<String> getNewStateParams(final Message message, final String state, final List<String> parameters) {
    return null;
  }
}
