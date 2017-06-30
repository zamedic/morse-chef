package com.marcarndt.morse.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.dto.ChefNode;
import com.marcarndt.morse.service.ChefService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/06/29.
 */
@RunWith(PowerMockRunner.class)
public class ChefChefNodeBotTest {

  /**
   * The Morse bot.
   */
  @Mock
  private transient MorseBot morseBot;
  /**
   * The User.
   */
  @Mock
  private transient User user;
  /**
   * The Chat.
   */
  @Mock
  private transient Chat chat;
  /**
   * The Chef service.
   */
  @Mock
  private transient ChefService chefService;
  /**
   * The Chef node bot.
   */
  @InjectMocks
  private transient ChefNodeBot chefNodeBot;

  /**
   * Gets role.
   *
   * @throws Exception the exception
   */
  @Test
  public void getRole() {
    assertEquals("Role should be trusted","Trusted", chefNodeBot.getRole());
  }

  /**
   * Perform command.
   *
   * @throws Exception the exception
   */
  @Test
  public void performCommand() {
    final ChefNode chefNode = new ChefNode("Test","Test-Env","Test-platform","Test-IP");
    try {
      Mockito.when(chefService.getNode("test")).thenReturn(chefNode);
    } catch (MorseBotException e) {
      fail(e.getMessage());
    }
    Mockito.when(chat.getId()).thenReturn(1234L);
    final String resposne = chefNodeBot.performCommand(morseBot, user, chat, "test");
    Mockito.verify(morseBot).sendMessage("Test - Test-Env - Test-platform","1234");
    assertNull("Response should be null",resposne);
  }

  /**
   * Gets command identifier.
   *
   * @throws Exception the exception
   */
  @Test
  public void getCommandIdentifier()  {
    assertEquals("comamnd should be chef","chef", chefNodeBot.getCommandIdentifier());
  }

  /**
   * Gets description.
   *
   * @throws Exception the exception
   */
  @Test
  public void getDescription()  {
    assertEquals("Description should be expected value","provides details on a chef node. to use run /chef (node name)",
        chefNodeBot.getDescription());
  }

}