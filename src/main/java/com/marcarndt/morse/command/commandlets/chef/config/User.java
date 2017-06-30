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
public class User implements Commandlet {

  public static String ChefUserState = "Chef User";

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.CHEFUSER);
  }

  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter user");
  }

  public String getNewState(Message message, String command) {
    return ChefUserState;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
