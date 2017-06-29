package com.marcarndt.morse.command;

import static org.junit.Assert.*;

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
  @Mock
  MorseBot morseBot;
  @Mock
  User user;
  @Mock
  Chat chat;
  @Mock
  SSHService sshService;


  @InjectMocks
  ChefFileCommand chefFileCommand;

  @Test
  public void getRole() throws Exception {
    assertEquals("Trusted",chefFileCommand.getRole());
  }

  @Test
  public void performCommand() throws Exception {
    List<String> files = Arrays.asList("file1","file2","file3");
    Mockito.when(sshService.getFileDescriptions()).thenReturn(files);
    String response = chefFileCommand.performCommand(morseBot,user,chat,null);
    Mockito.verify(morseBot).sendReplyKeyboardMessage(user,chat,"Select file",files);
    assertEquals("ChefFile",response);
  }

  @Test
  public void getCommandIdentifier() throws Exception {
    assertEquals("chefFile",chefFileCommand.getCommandIdentifier());
  }

  @Test
  public void getDescription() throws Exception {
    assertEquals("Download a predefined file from a Chef node",chefFileCommand.getDescription());
  }

}