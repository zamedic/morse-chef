package com.marcarndt.morse.command.commandlets.chef.application;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.command.ChefRecipe;
import com.marcarndt.morse.command.commandlet.Commandlet;
import com.marcarndt.morse.telegrambots.api.objects.Message;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class AddRecipe implements Commandlet {

  public static final String addChefRecipeState = "Add Chef Recipe";

  @Override
  public boolean canHandleCommand(Message message, String state) {
    return state.equals(ChefRecipe.CHEF_RECIPE_STATE) && message.getText()
        .equals(ChefRecipe.ADD_A_COOKBOOK);
  }

  @Override
  public void handleCommand(Message message, String state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Recipe description");
  }

  @Override
  public String getNewState(Message message, String command) {
    return addChefRecipeState;
  }

  @Override
  public List<String> getNewStateParams(Message message, String state, List<String> parameters) {
    return parameters;
  }
}
