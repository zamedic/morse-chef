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
   * The constant STATE.
   */
  public static final String STATE = "STATE";

  /**
   * The Ssh service.
   */
  @Inject
  private transient SSHService sshService;

  /**
   * Trusted Role.
   * @return Trusted
   */
  @Override
  public String getRole() {
    return "Trusted";
  }

  /**
   * Command allows a user to download a file from a chef node.
   * @param morseBot morse bot
   * @param user user
   * @param chat chat
   * @param arguments arguments ignored byt he command
   * @return STATE state
   */
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat,
      final String... arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select file", sshService.getFileDescriptions());
    return STATE;
  }

  /**
   * STATE.
   * @return STATE
   */
  @Override
  public String getCommandIdentifier() {
    return "STATE";
  }

  /**
   * Command description for help.
   * @return String
   */
  @Override
  public String getDescription() {
    return "Download a predefined file from a Chef node";
  }
}
