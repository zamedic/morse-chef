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

  /**
   * The constant STATE.
   */
  public static final String ADD_CHEF_RECIPE = "Add Chef Recipe";

  /**
   * Checks if the request is a Add Cookbook from {@link ChefRecipe}.
   * @param message input message
   * @param state current state
   * @return true if the message can be handled by the current state.
   */
  @Override
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(ChefRecipe.CHEF_RECIPE_STATE) && message.getText()
        .equals(ChefRecipe.ADD_A_COOKBOOK);
  }

  /**
   * Asks user for recipe description.
   * @param message input message
   * @param state current state
   * @param parameters current parameters
   * @param morseBot morse bot
   */
  @Override
  public void handleCommand(final Message message, final String state,
      final List<String> parameters, final MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Recipe description");
  }

  /**
   * STATE.
   * @param message input message
   * @param command input command
   * @return STATE
   */
  @Override
  public String getNewState(final Message message, final String command) {
    return ADD_CHEF_RECIPE;
  }

  /**
   * Does not change the parameters.
   * @param message input message
   * @param state current state
   * @param parameters current paramters
   * @return parameters
   */
  @Override
  public List<String> getNewStateParams(final Message message, final String state,
      final List<String> parameters) {
    return parameters;
  }
}
