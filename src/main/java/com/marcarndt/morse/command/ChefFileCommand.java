package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.SSHService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/11.
 */
@Stateless
public class ChefFileCommand extends BaseCommand {

  public static String ChefFile = "ChefFile";

  @Inject
  SSHService sshService;


  @Override
  public String getRole() {
    return "String";
  }

  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select File", sshService.getFileDescriptions());
    return ChefFile;
  }


  @Override
  public String getCommandIdentifier() {
    return "chefFile";
  }

  @Override
  public String getDescription() {
    return "Download a predefined file from a Chef node";
  }
}
