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

  /**
   * {@inheritDoc}.
   */
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(ChefConfigOrg.STATE);
  }

  /**
   * Updates the chef org.
   *
   * @param message Chef ChefConfigOrg message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
  public void handleCommand(final Message message, final String state,
      final List<String> parameters,
      final MorseBot morseBot) {
    final String org = message.getText();
    chefService.updateOrg(org);
    morseBot.sendMessage("Updated ORG", message.getChatId().toString());


  }

  /**
   * {@inheritDoc}.
   */
  public String getNewState(final Message message, final String command) {
    return null;
  }

  /**
   * {@inheritDoc}.
   */
  public List<String> getNewStateParams(final Message message, final String state,
      final List<String> parameters) {
    return null;
  }
}
