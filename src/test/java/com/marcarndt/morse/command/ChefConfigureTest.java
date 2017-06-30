package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.UserService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/06/26.
 */
@RunWith(PowerMockRunner.class)
public class ChefConfigureTest {


  /**
   * Class under test
   */
  @InjectMocks
  private transient ChefConfigure chefConfigure;
  /**
   * Morsebot mock
   */
  @Mock
  private transient MorseBot morseBot;
  /**
   * User mock
   */
  @Mock
  private transient User user;
  /**
   * Chat mock
   */
  @Mock
  private transient Chat chat;


  /**
   * Gets command identifier.
   *
   * @throws Exception the exception
   */
  @Test
  public void getCommandIdentifier() {
    Assert.assertEquals("chefconfig", chefConfigure.getCommandIdentifier());
  }

  /**
   * Gets description.
   *
   * @throws Exception the exception
   */
  @Test
  public void getDescription() {
    Assert.assertEquals("Configure the Chef Server", chefConfigure.getDescription());
  }

  /**
   * Gets role.
   *
   * @throws Exception the exception
   */
  @Test
  public void getRole() {
    Assert.assertEquals(UserService.ADMIN, chefConfigure.getRole());
  }

  /**
   * Perform command.
   *
   * @throws Exception the exception
   */
  @Test
  public void performCommand() {
    final String result = chefConfigure.performCommand(morseBot, user, chat, null);
    Mockito.verify(morseBot)
        .sendReplyKeyboardMessage(user, chat, "Select option", "Server URL", "UpdateChefUser",
            "AskForChefKey Path",
            "Organization");
    Assert.assertEquals("ChefConfig", result);

  }

}