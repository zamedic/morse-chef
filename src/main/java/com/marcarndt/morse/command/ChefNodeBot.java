package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.dto.Node;
import com.marcarndt.morse.service.ChefService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/13.
 */
@Stateless
public class ChefNodeBot extends BaseCommand {

  @Inject
  ChefService chefService;

  @Override
  public String getRole() {
    return "Trusted";
  }

  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] arguments) {
    Node node = null;
    try {
      node = chefService.getNode(arguments[0]);
    } catch (MorseBotException e) {
      morseBot.sendMessage(e.getMessage(), chat.getId().toString());
    }
    morseBot
        .sendMessage(node.getName() + " - " + node.getEnvironment() + " - " + node.getPlatform(),
            chat.getId().toString());
    return null;
  }

  @Override
  public String getCommandIdentifier() {
    return "chef";
  }

  @Override
  public String getDescription() {
    return "provides details on a chef node";
  }
}
