package com.marcarndt.morse.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.dto.Node;
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
public class ChefNodeBotTest {

  @Mock
  MorseBot morseBot;
  @Mock
  User user;
  @Mock
  Chat chat;
  @Mock
  ChefService chefService;
  @InjectMocks
  ChefNodeBot chefNodeBot;

  @Test
  public void getRole() throws Exception {
    assertEquals("Trusted", chefNodeBot.getRole());
  }

  @Test
  public void performCommand() throws Exception {
    Node node = new Node("Test","Test-Env","Test-platform","Test-IP");
    Mockito.when(chefService.getNode("test")).thenReturn(node);
    Mockito.when(chat.getId()).thenReturn(1234l);
    String resposne = chefNodeBot.performCommand(morseBot, user, chat, "test");
    Mockito.verify(morseBot).sendMessage("Test - Test-Env - Test-platform","1234");
    assertNull(resposne);
  }

  @Test
  public void getCommandIdentifier() throws Exception {
    assertEquals("chef", chefNodeBot.getCommandIdentifier());
  }

  @Test
  public void getDescription() throws Exception {
    assertEquals("provides details on a chef node. to use run /chef (node name)",
        chefNodeBot.getDescription());
  }

}