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
public class Server implements Commandlet {

  /**
   * The constant STATE.
   */
  public static final String STATE = "ChefServer";

  /**
   * @inheritDoc
   */
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.SERVER);
  }

  /**
   * @inheritDoc
   */
  public void handleCommand(final Message message, final String state, final List<String> parameters,
      final MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter chef SERVER URL");
  }

  /**
   * @inheritDoc
   */
  public String getNewState(final Message message, final String command) {
    return STATE;
  }

  /**
   * @inheritDoc
   */
  public List<String> getNewStateParams(final Message message, final String state, final List<String> parameters) {
    return null;
  }
}
