package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.UserService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;

import javax.ejb.Stateless;

/**
 * Created by arndt on 2017/06/26.
 */
@Stateless
public class ChefConfigure extends BaseCommand {

  /**
   * The constant SERVER.
   */
  public static final String SERVER = "Server URL";
  /**
   * The constant CHEFUSER.
   */
  public static final String CHEFUSER = "User";
  /**
   * The constant KEY.
   */
  public static final String KEY = "Key Path";
  /**
   * The constant ORG.
   */
  public static final String ORG = "Organization";

  /**
   * The constant CHEF_CONFIG_STATE.
   */
  public static final String CHEF_CONFIG_STATE = "ChefConfig";

  /**
   * getConfig.
   * @return chefconfig
   */
  public String getCommandIdentifier() {
    return "chefconfig";
  }

  /**
   * Command description for help.
   *
   * @return String
   */
  public String getDescription() {
    return "Configure the Chef Server";
  }

  /**
   * Admin Role.
   * @return admin
   */
  public String getRole() {
    return UserService.ADMIN;
  }

  /**
   * Allows the user to setup the chef environment the bot will connect too.
   * @param morseBot morse bot
   * @param user user
   * @param chat char
   * @param arguments additional arguments. Ignored by this command
   * @return configure state
   */
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat,
      final String... arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select option", SERVER, CHEFUSER, KEY, ORG);
    return CHEF_CONFIG_STATE;
  }
}
