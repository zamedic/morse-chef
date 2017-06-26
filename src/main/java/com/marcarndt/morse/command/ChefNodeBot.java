package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.dto.Node;
import com.marcarndt.morse.service.ChefService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/13.
 */
@Stateless
public class ChefNodeBot extends BaseCommand {

  private static final Logger LOG = Logger.getLogger(ChefNodeBot.class.getName());

  @Inject
  ChefService chefService;

  @Override
  public String getRole() {
    return "Trusted";
  }

  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] arguments) {
    Node node = null;
    try {
      if (arguments.length != 1) {
        throw new MorseBotException("To use this command, please use /chef (node name)");
      }

      LOG.info("Searching for node " + arguments[0]);
      node = chefService.getNode(arguments[0]);
      LOG.info("Found Node: " + node.toString());
      morseBot
          .sendMessage(node.getName() + " - " + node.getEnvironment() + " - " + node.getPlatform(),
              chat.getId().toString());
    } catch (MorseBotException e) {
      morseBot.sendMessage(e.getMessage(), chat.getId().toString());
    }
    return null;
  }

  @Override
  public String getCommandIdentifier() {
    return "chef";
  }

  @Override
  public String getDescription() {
    return "provides details on a chef node. to use run /chef <node name>";
  }
}
