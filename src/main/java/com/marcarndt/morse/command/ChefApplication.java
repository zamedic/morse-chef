package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;

/**
 * Created by arndt on 2017/06/29.
 */
public class ChefApplication extends BaseCommand {

  /**
   * Trusted Role.
   *
   * @return Trusted
   */
  @Override
  public String getRole() {
    return "Trusted";
  }

  /**
   * TODO.
   * @param morseBot morse bot
   * @param user user
   * @param chat chat
   * @param strings chat arguments
   * @return New state
   */
  @Override
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat,
      final String[] strings) {
    return null;
    //TODO
  }

  /**
   * chefapplication.
   *
   * @return chefapplication
   */
  @Override
  public String getCommandIdentifier() {
    return "chefapplication";
  }

  /**
   * Description.
   *
   * @return description
   */
  @Override
  public String getDescription() {
    return "Configured Recipes for Chef";
  }
}
