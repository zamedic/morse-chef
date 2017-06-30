package com.marcarndt.morse.command.commandlets.chef.application;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.command.commandlet.Commandlet;
import com.marcarndt.morse.service.ChefApplicationService;
import com.marcarndt.morse.telegrambots.api.objects.Message;

import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/04.
 */
@Singleton
public class AddRecipeCookbook implements Commandlet {

  /**
   * The Chef application service.
   */
  @Inject
  private transient ChefApplicationService chefService;

  /**
   * Checks if the state is correct to execute the command.
   * @param message input message
   * @param state current stage
   * @return true if this came from {@link AddRecipeDescription}
   */
  @Override
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(AddRecipeDescription.STATE);
  }

  /**
   * Adds a cookbook to an application.
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
  @Override
  public void handleCommand(final Message message, final String state, final List<String> parameters,
      final MorseBot morseBot) {
    final String cookbook = message.getText();
    final String application = parameters.get(0);
    final String description = parameters.get(1);

    chefService.addRecipeToApplication(application, description, cookbook);
    morseBot.sendMessage(
        "Added cookbook " + cookbook + " with description " + description + " to application "
            + application, message.getChatId().toString());
  }

  /**
   * Null - end of the process.
   * @param message input message
   * @param command input command
   * @return null
   */
  @Override
  public String getNewState(final Message message, final String command) {
    return null;
  }

  /**
   * Null - We are done.
   * @param message inpuit message
   * @param state current state
   * @param parameters parameters
   * @return null
   */
  @Override
  public List<String> getNewStateParams(final Message message, final String state, final List<String> parameters) {
    return null;
  }
}
