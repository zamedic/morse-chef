package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.UserService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;

/**
 * Created by arndt on 2017/06/29.
 */
public class ChefRecipe extends BaseCommand {

  public static final String addCookbook = "Add a new cookbook";
  public static final String deleteCookbook = "Delete a cookbook";

  public static final String chefRecipeAdminState = "Chef Recipe Admin";


  @Override
  public String getRole() {
    return UserService.ADMIN;
  }

  @Override
  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] strings) {
    morseBot.sendReplyKeyboardMessage(user, chat, "What would you like to do? ", addCookbook,
        deleteCookbook);
    return chefRecipeAdminState;
  }

  @Override
  public String getCommandIdentifier() {
    return "chefRecipe";
  }

  @Override
  public String getDescription() {
    return "Manages Chef recipe associations to chef applications";
  }
}
