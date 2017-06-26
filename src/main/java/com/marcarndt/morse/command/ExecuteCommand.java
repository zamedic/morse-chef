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

  @Inject
  SSHService sshService;

  @Override
  public String getCommandIdentifier() {
    return "chefExecute";
  }

  @Override
  public String getDescription() {
    return "Execute a predefined SSH Command on a chef node";
  }

  @Override
  public String getRole() {
    return "Trusted";
  }

  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select command", sshService.getCommandNames());
    return null;
  }
}
