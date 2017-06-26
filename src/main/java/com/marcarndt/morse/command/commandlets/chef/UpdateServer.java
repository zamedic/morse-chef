package com.marcarndt.morse.command.commandlets.chef;

import com.marcarndt.morse.MorseBot;
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

  @Inject
  ChefService chefService;

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(Server.ChefServerState);
  }

  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    String server = message.getText();
    chefService.updateServer(server);

    morseBot.sendMessage("Udpated Server", message.getChatId().toString());
  }

  public String getNewState(Message message, String command) {
    return null;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
