package com.marcarndt.morse.command.commandlets.chefapplication;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.command.commandlet.Commandlet;
import com.marcarndt.morse.telegrambots.api.objects.Message;
import com.marcarndt.swarm.service.ApplicationService;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/06/29.
 */
public class AddRecipeApplication implements Commandlet {

  public static final String addRecipeApplicationState = "Add Recipe Application";
  @Inject
  ApplicationService applicationService;

  @Override
  public boolean canHandleCommand(Message message, String s) {
    return s.equals(AddRecipe.addChefRecipeState);
  }

  @Override
  public void handleCommand(Message message, String s, List<String> list, MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message, "Select application",
        applicationService.getApplications());
  }

  @Override
  public String getNewState(Message message, String s) {
    return addRecipeApplicationState;
  }

  @Override
  public List<String> getNewStateParams(Message message, String s, List<String> list) {
    return Arrays.asList(message.getText());
  }
}
