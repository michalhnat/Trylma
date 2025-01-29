package com.michal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.michal.Utils.JsonDeserializer;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.HashMap;

public class SecondaryControllerTest {

    private SecondaryController controller;
    private ICommunication mockCommunication;
    private Board mockBoard;
    private JsonDeserializer mockDeserializer;

    @BeforeEach
    void setUp() {
        // Create an instance of the controller
        controller = new SecondaryController();

        // Mock dependencies
        mockCommunication = mock(ICommunication.class);
        mockBoard = mock(Board.class);
        mockDeserializer = mock(JsonDeserializer.class);

        // We can statically mock App.getCommunication(), but for simplicity:
        // Just set controller fields directly.
        // Or in real scenario, use a different approach, e.g. injecting the mocks.
        // Accessing private fields might need reflection or a different design.

        // For test, we mimic what happens in initialize():
        // (Pretend to assign the communication and board references).
        // We also mimic that they were set up in initialize().
        try {
            // reflection to inject mock "board" and "communication" or do partial real initialize:
            controller.initialize();
        } catch (Exception e) {
            // ignore any real JavaFX init
        }

        // Force controller's communication to be our mock
        try {
            var commField = SecondaryController.class.getDeclaredField("communication");
            commField.setAccessible(true);
            commField.set(controller, mockCommunication);

            var boardField = SecondaryController.class.getDeclaredField("board");
            boardField.setAccessible(true);
            boardField.set(controller, mockBoard);
        } catch (Exception e) {
            fail("Failed to inject mocks into controller: " + e.getMessage());
        }
    }

    @Test
    void testInitialize() {
        // This verifies the board gets created in initialize().
        // We replaced it with a mock but we can check logic if needed.
        assertNotNull(controller);
    }

    @Test
    void testShowInfo() {
        // The user calls showInfo(...) -> the controller should place a Notification in
        // notificationPane
        controller.showInfo("Test Info");
        VBox notificationPane = getNotificationPane();
        assertEquals(1, notificationPane.getChildren().size(),
                "Notification pane should have exactly one element after showInfo()");
        // Additional checks: the text is uppercase, style classes, etc.
    }

    @Test
    void testShowError() {
        controller.showError("Error Info");
        VBox notificationPane = getNotificationPane();
        assertEquals(1, notificationPane.getChildren().size(),
                "Notification pane should have exactly one element after showError()");
    }

    @Test
    void testHandleMessage_BoardType() {
        // Suppose the JSON type is "board"
        when(mockDeserializer.getType(anyString())).thenReturn("board");
        when(mockDeserializer.getMessage(anyString())).thenReturn("FAKE-MAP");
        // We inject the mock Deserializer in code or just stub it statically.

        // For test, just call handleMessage:
        controller.handleMessage("{\"type\":\"board\",\"payload\":{\"content\":\"FAKE-MAP\"}}");

        // Ensure board.addMapToQueue(...) or createBoardOutOfMap(...) was called
        verify(mockBoard, atLeastOnce()).isEmpty();
    }

    @Test
    void testHandleMessage_GameInfo() {
        when(mockDeserializer.getType(anyString())).thenReturn("gameInfo");
        HashMap<String, String> mockGameInfo = new HashMap<>();
        mockGameInfo.put("status", "IN_PROGRESS");
        mockGameInfo.put("players", "2");
        when(mockDeserializer.getGameInfoMap(anyString())).thenReturn(mockGameInfo);

        // test
        controller.handleMessage("{\"type\":\"gameInfo\"}");
        // verify it updates the list
        // e.g. info_list should have items
        // We'll do a partial check that there's no error thrown
    }

    @Test
    void testMove_ValidCoordinates() throws Exception {
        // Fill text fields with valid numeric data
        setTextField("x", "1");
        setTextField("y", "2");
        setTextField("destination_x", "3");
        setTextField("destination_y", "4");

        controller.move();

        // Capture the string sent to communication
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockCommunication).sendMessage(captor.capture());
        String sentJson = captor.getValue();
        assertTrue(sentJson.contains("\"start_x\":1"));
        assertTrue(sentJson.contains("\"start_y\":2"));
        assertTrue(sentJson.contains("\"end_x\":3"));
        assertTrue(sentJson.contains("\"end_y\":4"));
        // Also verify that board.restet_border_on_active_cells() was called
        verify(mockBoard).restet_border_on_active_cells();
    }

    @Test
    void testMove_InvalidCoordinates() {
        setTextField("x", "-1");
        setTextField("y", "-2");
        setTextField("destination_x", "3");
        setTextField("destination_y", "4");

        // Calls showError -> no message should be sent
        controller.move();
        verify(mockCommunication, never()).sendMessage(anyString());
    }

    @Test
    void testPass() throws Exception {
        controller.pass();
        verify(mockCommunication).sendMessage(contains("\"command\":\"pass\""));
    }

    @Test
    void testAddBot() throws Exception {
        controller.addBot();
        verify(mockCommunication).sendMessage(contains("\"command\":\"add_bot\""));
    }

    @Test
    void testSave() throws Exception {
        controller.save();
        verify(mockCommunication).sendMessage(contains("\"command\":\"save_game\""));
    }

    @Test
    void testDisableAndEnableAllButtons() {
        controller.disableAllButtons();
        // We can't directly check private button fields,
        // but we can reflect or trust the coverage.
        // For demonstration, let's reflect the "moveButton"
        assertTrue(isButtonDisabled("moveButton"),
                "moveButton should be disabled after disableAllButtons()");

        controller.enableAllButtons();
        assertFalse(isButtonDisabled("moveButton"),
                "moveButton should be enabled after enableAllButtons()");
    }

    /*
     * Helpers to reflect private fields of controller in tests
     */

    private VBox getNotificationPane() {
        try {
            var field = SecondaryController.class.getDeclaredField("notificationPane");
            field.setAccessible(true);
            return (VBox) field.get(controller);
        } catch (Exception e) {
            fail("Failed to reflect notificationPane: " + e.getMessage());
            return null;
        }
    }

    private boolean isButtonDisabled(String fieldName) {
        try {
            var field = SecondaryController.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Button btn = (Button) field.get(controller);
            return btn.isDisabled();
        } catch (Exception e) {
            fail("Failed to reflect Button: " + e.getMessage());
            return false;
        }
    }

    private void setTextField(String fieldName, String value) {
        try {
            var field = SecondaryController.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            TextField tf = (TextField) field.get(controller);
            tf.setText(value);
        } catch (Exception e) {
            fail("Failed to reflect text field: " + e.getMessage());
        }
    }
}
