package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.SSHService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class ExecuteCommand extends BaseCommand {

  /**
   * The Ssh service.
   */
  @Inject
  private transient SSHService sshService;

  /**
   * chefExecute.
   * @return chefExecute
   */
  @Override
  public String getCommandIdentifier() {
    return "chefExecute";
  }

  /**
   * Message for help.
   * @return String
   */
  @Override
  public String getDescription() {
    return "Execute a predefined SSH Command on a chef node";
  }

  /**
   * Trusted.
   * @return Trusted.
   */
  @Override
  public String getRole() {
    return "Trusted";
  }

  /**
   * Allows the user to select a command to execute.
   * @param morseBot morse bot
   * @param user user
   * @param chat chat
   * @param arguments ignored for this opperation
   * @return TODO
   */
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat,
      final String... arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select command", sshService.getCommandNames());
    //TODO
    return null;
  }
}
