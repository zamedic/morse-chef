package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;

/**
 * Created by arndt on 2017/06/29.
 */
public class ChefApplication extends BaseCommand {

  @Override
  public String getRole() {
    return "Trusted";
  }

  @Override
  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] strings) {
    return null;
  }

  @Override
  public String getCommandIdentifier() {
    return "chefApplication";
  }

  @Override
  public String getDescription() {
    return "Configured Recipes for Chef";
  }
}
