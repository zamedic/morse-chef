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

  public static final String server = "Server URL";
  public static final String chefuser = "User";
  public static final String key = "Key Path";
  public static final String org = "Organization";

  public static final String chefConfigState = "ChefConfig";


  public String getCommandIdentifier() {
    return "chefConfig";
  }

  public String getDescription() {
    return "Configure the Chef Server";
  }

  public String getRole() {
    return UserService.ADMIN;
  }

  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] arguments) {

    morseBot.sendReplyKeyboardMessage(user,chat, "Select option", server, chefuser, key, org);
    return chefConfigState;
  }
}
