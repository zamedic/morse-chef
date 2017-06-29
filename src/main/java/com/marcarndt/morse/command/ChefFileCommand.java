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

  /**
   * The constant ChefFile.
   */
  public static String ChefFile = "ChefFile";

  /**
   * The Ssh service.
   */
  @Inject
  private SSHService sshService;

  /**
   * Trusted Role
   * @return Trusted
   */
  @Override
  public String getRole() {
    return "Trusted";
  }

  /**
   * Command allows a user to download a file from a chef node
   * @param morseBot morse bot
   * @param user user
   * @param chat chat
   * @param arguments arguments ignored byt he command
   * @return ChefFile state
   */
  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select file", sshService.getFileDescriptions());
    return ChefFile;
  }

  /**
   * chefFile
   * @return chefFile
   */
  @Override
  public String getCommandIdentifier() {
    return "chefFile";
  }

  /**
   * Command description for help
   * @return String
   */
  @Override
  public String getDescription() {
    return "Download a predefined file from a Chef node";
  }
}
