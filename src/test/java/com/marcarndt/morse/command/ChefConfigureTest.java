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

  @InjectMocks
  private transient ChefConfigure chefConfigure;
  @Mock
  private transient MorseBot morseBot;
  @Mock
  private transient User user;
  @Mock
  private transient Chat chat;


  /**
   * Gets command identifier.
   *
   * @throws Exception the exception
   */
  @Test
  public void getCommandIdentifier() throws Exception {
    Assert.assertEquals("chefConfig", chefConfigure.getCommandIdentifier());
  }

  /**
   * Gets description.
   *
   * @throws Exception the exception
   */
  @Test
  public void getDescription() throws Exception {
    Assert.assertEquals("Configure the Chef Server", chefConfigure.getDescription());
  }

  /**
   * Gets role.
   *
   * @throws Exception the exception
   */
  @Test
  public void getRole() throws Exception {
    Assert.assertEquals(UserService.ADMIN, chefConfigure.getRole());
  }

  /**
   * Perform command.
   *
   * @throws Exception the exception
   */
  @Test
  public void performCommand() throws Exception {
    String result = chefConfigure.performCommand(morseBot, user, chat, null);
    Mockito.verify(morseBot)
        .sendReplyKeyboardMessage(user, chat, "Select option", "Server URL", "User", "Key Path",
            "Organization");
    Assert.assertEquals("ChefConfig", result);

  }

}