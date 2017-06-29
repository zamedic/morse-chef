package com.marcarndt.morse.command.commandlets.chefConfig;

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
public class UpdateUser implements Commandlet {

  @Inject
  ChefService chefService;

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(User.ChefUserState);
  }

  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    String user = message.getText();
    chefService.updateUser(user);
    morseBot.sendMessage("Updated user", message.getChatId().toString());
  }

  public String getNewState(Message message, String command) {
    return null;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
