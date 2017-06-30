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
public class UpdateChefUser implements Commandlet {

  /**
   * The constant STATE.
   */
  public static final String STATE = "Chef UpdateChefUser";

  /**
   * @inheritDoc
   */
  public boolean canHandleCommand(final Message message,final String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.CHEFUSER);
  }

  /**
   * @inheritDoc
   */
  public void handleCommand(final Message message, final String state, final List<String> parameters,
      final MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter user");
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
