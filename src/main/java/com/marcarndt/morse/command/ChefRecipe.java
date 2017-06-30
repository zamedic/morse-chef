package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.UserService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;

/**
 * Created by arndt on 2017/06/29.
 */
public class ChefRecipe extends BaseCommand {

  /**
   * The constant ADD_A_COOKBOOK.
   */
  public static final String ADD_A_COOKBOOK = "Add a new cookbook";
  /**
   * The constant DELETE_A_COOKBOOK.
   */
  public static final String DELETE_A_COOKBOOK = "Delete a cookbook";

  /**
   * The constant CHEF_RECIPE_STATE.
   */
  public static final String CHEF_RECIPE_STATE = "Chef Recipe Admin";


  /**
   * Admin Role.
   * @return Admin
   */
  @Override
  public String getRole() {
    return UserService.ADMIN;
  }

  /**
   * Allows the user to add or delete a cookbook.
   * @param morseBot userbot
   * @param user user
   * @param chat chat
   * @param strings parameters, ignored by this command
   * @return Chef Recipe Admin
   */
  @Override
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat,
      final String[] strings) {
    morseBot.sendReplyKeyboardMessage(user, chat, "What would you like to do? ", ADD_A_COOKBOOK,
        DELETE_A_COOKBOOK);
    return CHEF_RECIPE_STATE;
  }

  /**
   * chefRecipe.
   * @return chefRecipe
   */
  @Override
  public String getCommandIdentifier() {
    return "chefRecipe";
  }

  /**
   * Description for help file.
   * @return String
   */
  @Override
  public String getDescription() {
    return "Manages Chef recipe associations to chef applications";
  }
}
