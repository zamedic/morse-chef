package com.marcarndt.morse.command.commandlets.chef.application;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.command.commandlet.Commandlet;
import com.marcarndt.morse.telegrambots.api.objects.Message;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class AddRecipeDescription implements Commandlet {

  public final static String addRecipeDescription = "Add chef recipe description";

  @Override
  public boolean canHandleCommand(Message message, String state) {
    return state.equals(AddRecipeApplication.addRecipeApplicationState);
  }

  @Override
  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Cookbook Name");
  }

  @Override
  public String getNewState(Message message, String command) {
    return addRecipeDescription;
  }

  @Override
  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    parameters.add(message.getText());
    return parameters;
  }
}
