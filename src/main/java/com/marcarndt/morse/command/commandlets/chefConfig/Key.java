package com.marcarndt.morse.command.commandlets.chefConfig;

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
public class Key implements Commandlet {

  public static String ChefKeyState = "ChefKey";

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.KEY);
  }

  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter KEY path");
  }

  public String getNewState(Message message, String command) {
    return ChefKeyState;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
