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

  /**
   * State.
   */
  public static final String STATE = "Add chef recipe description";

  /**
   * checks if the commandlet can be executed.
   * @param message input message
   * @param state current state
   * @return true if this came from {@link AddRecipeApplication}
   */
  @Override
  public boolean canHandleCommand(final Message message, final String state) {
    return state.equals(AddRecipeApplication.STATE);
  }

  /**
   * Asks user for cookbook name.
   * @param message input message
   * @param state current state
   * @param parameters current paramters
   * @param morseBot morsebot
   */
  @Override
  public void handleCommand(final Message message, final String state,
      final List<String> parameters, final MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Cookbook Name");
  }

  /**
   * STATE.
   * @param message current message
   * @param command current command
   * @return STATE
   */
  @Override
  public String getNewState(final Message message, final String command) {
    return STATE;
  }

  /**
   * adds the recipe description to the return list.
   * @param message input message
   * @param state current state
   * @param parameters current paramters
   * @return current parameters + recipe description provided
   */
  @Override
  public List<String> getNewStateParams(final Message message, final String state,
      final List<String> parameters) {
    parameters.add(message.getText());
    return parameters;
  }
}
