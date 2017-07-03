package com.marcarndt.morse.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.marcarndt.morse.data.ChefDetails;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/07/03.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({File.class,ChefService.class,PrintWriter.class})
public class ChefServiceTest {

  @Mock
  MongoService mongoService;
  @Mock
  Datastore datastore;
  @Mock
  Query query;
  @Mock
  File file;
  @Mock
  OutputStreamWriter outputStreamWriter;
  @Mock
  FileOutputStream fileOutputStream;

  @InjectMocks
  ChefService chefService;

  @Test
  public void setup() {
    ChefDetails chefDetails = new ChefDetails();
    chefDetails.setKeyPath("-----BEGIN RSA PRIVATE KEY-----\n"
        + "MIIBOwIBAAJBAJv8ZpB5hEK7qxP9K3v43hUS5fGT4waKe7ix4Z4mu5UBv+cw7WSF\n"
        + "At0Vaag0sAbsPzU8Hhsrj/qPABvfB8asUwcCAwEAAQJAG0r3ezH35WFG1tGGaUOr\n"
        + "QA61cyaII53ZdgCR1IU8bx7AUevmkFtBf+aqMWusWVOWJvGu2r5VpHVAIl8nF6DS\n"
        + "kQIhAMjEJ3zVYa2/Mo4ey+iU9J9Vd+WoyXDQD4EEtwmyG1PpAiEAxuZlvhDIbbce\n"
        + "7o5BvOhnCZ2N7kYb1ZC57g3F+cbJyW8CIQCbsDGHBto2qJyFxbAO7uQ8Y0UVHa0J\n"
        + "BO/g900SAcJbcQIgRtEljIShOB8pDjrsQPxmI1BLhnjD1EhRSubwhDw5AFUCIQCN\n"
        + "A24pDtdOHydwtSB5+zFqFLfmVZplQM/g5kb4so70Yw==\n"
        + "-----END RSA PRIVATE KEY-----\n");
    when(mongoService.getDatastore()).thenReturn(datastore);
    when(datastore.createQuery(ChefDetails.class)).thenReturn(query);
    when(query.count()).thenReturn(1L);
    when(query.get()).thenReturn(chefDetails);
    try {
      whenNew(File.class).withArguments("chef.pem").thenReturn(file);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    when(file.exists()).thenReturn(false);
    try {
      whenNew(FileOutputStream.class).withArguments(file).thenReturn(fileOutputStream);
      whenNew(OutputStreamWriter.class).withArguments(fileOutputStream, StandardCharsets.UTF_8).thenReturn(outputStreamWriter);
    } catch (Exception e) {
      fail(e.getMessage());
    }

    chefService.setup();

    try {
      verify(outputStreamWriter).write("-----BEGIN RSA PRIVATE KEY-----\n"
          + "MIIBOwIBAAJBAJv8ZpB5hEK7qxP9K3v43hUS5fGT4waKe7ix4Z4mu5UBv+cw7WSF\n"
          + "At0Vaag0sAbsPzU8Hhsrj/qPABvfB8asUwcCAwEAAQJAG0r3ezH35WFG1tGGaUOr\n"
          + "QA61cyaII53ZdgCR1IU8bx7AUevmkFtBf+aqMWusWVOWJvGu2r5VpHVAIl8nF6DS\n"
          + "kQIhAMjEJ3zVYa2/Mo4ey+iU9J9Vd+WoyXDQD4EEtwmyG1PpAiEAxuZlvhDIbbce\n"
          + "7o5BvOhnCZ2N7kYb1ZC57g3F+cbJyW8CIQCbsDGHBto2qJyFxbAO7uQ8Y0UVHa0J\n"
          + "BO/g900SAcJbcQIgRtEljIShOB8pDjrsQPxmI1BLhnjD1EhRSubwhDw5AFUCIQCN\n"
          + "A24pDtdOHydwtSB5+zFqFLfmVZplQM/g5kb4so70Yw==\n"
          + "-----END RSA PRIVATE KEY-----\n");
    } catch (IOException e) {
      fail(e.getMessage());
    }

    try {
      verify(outputStreamWriter).close();
    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void getSearchUrl() throws Exception {
  }

  @Test
  public void recipeSearch() throws Exception {
  }

  @Test
  public void getNode() throws Exception {
  }

  @Test
  public void updateKey() throws Exception {
  }

  @Test
  public void updateOrg() throws Exception {
  }

  @Test
  public void updateServer() throws Exception {
  }

  @Test
  public void updateUser() throws Exception {
  }

}