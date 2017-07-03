package com.marcarndt.morse.command.commandlets.chef.application;

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

  /**
   * The constant STATE.
   */
  public static final String STATE = "Add Recipe Application";
  /**
   * The Application service.
   */
  @Inject
  private transient ApplicationService appService;

  /**
   * Verifies state.
   * @param message message
   * @param state state
   * @return true when this came from AddRecipe
   */
  @Override
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(AddRecipe.ADD_CHEF_RECIPE);
  }

  /**
   * Asks user to select an application.
   * @param message message
   * @param state current state
   * @param list parameters
   * @param morseBot morsebot
   */
  @Override
  public void handleCommand(final Message message, final String state, final List<String> list,
      final MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message, "Select application",
        appService.getApplications());
  }

  /**
   * Returns next state.
   * @param message message
   * @param state current state
   * @return STATE
   */
  @Override
  public String getNewState(final Message message, final String state) {
    return STATE;
  }

  /**
   * Adds answer to paramter list.
   * @param message message
   * @param state state
   * @param list paramters
   * @return new list with the recipe added
   */
  @Override
  public List<String> getNewStateParams(final Message message, final String state,
      final List<String> list) {
    return Arrays.asList(message.getText());
  }
}
