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
public class Server implements Commandlet {

  public static String ChefServerState = "ChefServer";

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(ChefConfigure.CHEF_CONFIG_STATE) && message.getText()
        .equals(ChefConfigure.SERVER);
  }

  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter chef SERVER URL");
  }

  public String getNewState(Message message, String command) {
    return ChefServerState;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
