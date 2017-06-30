package com.marcarndt.morse.command;

import static org.junit.Assert.assertEquals;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.SSHService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import java.util.Arrays;
import java.util.List;
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
public class ChefFileCommandTest {

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
   * The Ssh service.
   */
  @Mock
  private transient SSHService sshService;


  /**
   * The Chef file command.
   */
  @InjectMocks
  private transient ChefFileCommand chefFileCommand;

  /**
   * Gets role.
   *
   * @throws Exception the exception
   */
  @Test
  public void getRole() {
    assertEquals("Role should be trusted", "Trusted", chefFileCommand.getRole());
  }

  /**
   * Perform command.
   *
   * @throws Exception the exception
   */
  @Test
  public void performCommand() {
    final List<String> files = Arrays.asList("file1", "file2", "file3");
    Mockito.when(sshService.getFileDescriptions()).thenReturn(files);
    final String response = chefFileCommand.performCommand(morseBot, user, chat, null);
    Mockito.verify(morseBot).sendReplyKeyboardMessage(user, chat, "Select file", files);
    assertEquals("response should be chefFile", "chefFile", response);
  }

  /**
   * Gets command identifier.
   *
   * @throws Exception the exception
   */
  @Test
  public void getCommandIdentifier() {
    assertEquals("command should be chefFile", "chefFile", chefFileCommand.getCommandIdentifier());
  }

  /**
   * Gets description.
   *
   * @throws Exception the exception
   */
  @Test
  public void getDescription() {
    assertEquals("description should be as expected", "Download a predefined file from a Chef node",
        chefFileCommand.getDescription());
  }

}