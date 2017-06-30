package com.marcarndt.morse.command.commandlets.chef.config;

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
public class UpdateOrg implements Commandlet {

  /**
   * Chef Service.
   */
  @Inject
  private transient ChefService chefService;

  public boolean canHandleCommand(Message message, String state) {
    return state.equals(Org.ChefOrgStage);
  }

  /**
   * Updates the chef org.
   * @param message Chef Org message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    String org = message.getText();
    chefService.updateOrg(org);
    morseBot.sendMessage("Updated ORG", message.getChatId().toString());


  }

  public String getNewState(Message message, String command) {
    return null;
  }

  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
