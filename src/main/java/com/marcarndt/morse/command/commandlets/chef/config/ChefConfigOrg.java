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
public class ChefConfigOrg implements Commandlet {

  /**
   * The constant STATE.
   */
  public static final String STATE = "ChefOrg";

  /**
   * Checks if the commandlet can be executed.
   * @param message input message
   * @param state input state
   * @return true if this is a new ORG request from {@link ChefConfigure}
   */
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.ORG);
  }

  /**
   * Asks user for ORG Name.
   * @param message input message
   * @param state current state
   * @param parameters current paramters
   * @param morseBot morse bot
   */
  public void handleCommand(final Message message, final String state,
      final List<String> parameters, final MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter new ORG name");
  }

  /**
   * State.
   * @param message input message
   * @param command input command
   * @return STATE
   */
  public String getNewState(final Message message, final String command) {
    return STATE;
  }

  /**
   * Null.
   * @param message input message
   * @param state input state
   * @param parameters input parameters
   * @return null
   */
  public List<String> getNewStateParams(final Message message, final String state,
      final List<String> parameters) {
    return null;
  }
}
