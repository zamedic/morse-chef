package com.marcarndt.morse.command.commandlets.chefApplication;

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

  @Inject
  ChefApplicationService chefApplicationService;


  @Override
  public boolean canHandleCommand(Message message, String state) {
    return state.equals(AddRecipeDescription.addRecipeDescription);
  }

  @Override
  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    String cookbook = message.getText();
    String application = parameters.get(0);
    String description = parameters.get(1);

    chefApplicationService.addRecipeToApplication(application, description, cookbook);
    morseBot.sendMessage(
        "Added cookbook " + cookbook + " with description " + description + " to application "
            + application, message.getChatId().toString());
  }

  @Override
  public String getNewState(Message message, String command) {
    return null;
  }

  @Override
  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return null;
  }
}
