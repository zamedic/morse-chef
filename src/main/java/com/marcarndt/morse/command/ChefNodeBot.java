package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.dto.Node;
import com.marcarndt.morse.service.ChefService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/13.
 */
@Stateless
public class ChefNodeBot extends BaseCommand {

  /**
   * Logger.
   */
  private static final Logger LOG = Logger.getLogger(ChefNodeBot.class.getName());

  /**
   * The Chef service.
   */
  @Inject
  private transient ChefService chefService;

  /**
   * Trusted.
   *
   * @return Trusted
   */
  @Override
  public String getRole() {
    return "Trusted";
  }

  /**
   * Retreives basic information for a chef node.
   * @param morseBot Morse bot
   * @param user User
   * @param chat Chat
   * @param arguments first argument is the chef node
   * @return Null
   */
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat, final String... arguments) {
    Node node = null;
    try {
      if (arguments.length != 1) {
        throw new MorseBotException("To use this command, please use /chef (node name)");
      }

      if (LOG.isLoggable(Level.INFO)) {
        LOG.info("Searching for node " + arguments[0]);//NOPMD
      }
      node = chefService.getNode(arguments[0]);
      if (LOG.isLoggable(Level.INFO)) {
        LOG.info("Found Node: " + node.toString());//NOPMD
      }
      morseBot
          .sendMessage(node.getName() + " - " + node.getEnvironment() + " - " + node.getPlatform(),
              chat.getId().toString());
    } catch (MorseBotException e) {
      morseBot.sendMessage(e.getMessage(), chat.getId().toString());
    }
    return null;
  }

  /**
   * chef
   *
   * @return chef
   */
  @Override
  public String getCommandIdentifier() {
    return "chef";
  }

  /**
   * Description for the help file
   *
   * @return String
   */
  @Override
  public String getDescription() {
    return "provides details on a chef node. to use run /chef (node name)";
  }
}
