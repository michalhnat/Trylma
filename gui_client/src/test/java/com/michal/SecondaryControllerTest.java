// not working

/**
 * package com.michal;
 * 
 * import static org.junit.jupiter.api.Assertions.*; import static org.mockito.ArgumentMatchers.*;
 * import static org.mockito.Mockito.*;
 * 
 * import java.lang.reflect.Field; import java.lang.reflect.InvocationTargetException; import
 * java.lang.reflect.Method; import java.util.HashMap;
 * 
 * import com.michal.Exceptions.FailedSendingMessageToServer; import
 * com.michal.Utils.JsonDeserializer; import javafx.scene.control.Button; import
 * javafx.scene.control.TextField; import javafx.scene.layout.VBox; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; import
 * org.mockito.ArgumentCaptor; import org.mockito.Mock; import org.mockito.MockitoAnnotations;
 * 
 * public class SecondaryControllerTest {
 * 
 * private SecondaryController controller;
 * 
 * @Mock private ICommunication mockCommunication;
 * 
 * @Mock private Board mockBoard;
 * 
 * @Mock private JsonDeserializer mockDeserializer;
 * 
 * @BeforeEach void setUp() throws Exception { MockitoAnnotations.openMocks(this); controller = new
 *             SecondaryController();
 * 
 *             injectMock("communication", mockCommunication); injectMock("board", mockBoard);
 *             injectMock("deserializer", mockDeserializer);
 * 
 *             Field instanceField = SecondaryController.class.getDeclaredField("instance");
 *             instanceField.setAccessible(true); instanceField.set(null, controller);
 * 
 *             initializeTextField("x", new TextField()); initializeTextField("y", new TextField());
 *             initializeTextField("destination_x", new TextField());
 *             initializeTextField("destination_y", new TextField()); initializeButton("moveButton",
 *             new Button()); initializeButton("passButton", new Button());
 *             initializeButton("addBotButton", new Button()); initializeButton("saveButton", new
 *             Button()); }
 * 
 *             private void injectMock(String fieldName, Object mock) { try { Field field =
 *             SecondaryController.class.getDeclaredField(fieldName); field.setAccessible(true);
 *             field.set(controller, mock); } catch (Exception e) { fail("Field injection failed for
 *             " + fieldName + ": " + e.getMessage()); } }
 * 
 *             private void initializeTextField(String fieldName, TextField tf) { try { Field field
 *             = SecondaryController.class.getDeclaredField(fieldName); field.setAccessible(true);
 *             field.set(controller, tf); } catch (Exception e) { fail("Failed to initialize " +
 *             fieldName + ": " + e.getMessage()); } }
 * 
 *             private void initializeButton(String fieldName, Button btn) { try { Field field =
 *             SecondaryController.class.getDeclaredField(fieldName); field.setAccessible(true);
 *             field.set(controller, btn); } catch (Exception e) { fail("Failed to initialize " +
 *             fieldName + ": " + e.getMessage()); } }
 * 
 * @Test void testShowInfo() { controller.showInfo("Test message"); VBox notifications =
 *       getField("notificationPane", VBox.class); assertEquals(1,
 *       notifications.getChildren().size()); }
 * 
 * @Test void testHandleMessage_BoardType() {
 *       when(mockDeserializer.getType(anyString())).thenReturn("board");
 *       when(mockDeserializer.getMessage(anyString())).thenReturn("test_map");
 * 
 *       controller.handleMessage("{}"); verify(mockBoard).createBoardOutOfMap("test_map"); }
 * 
 * @Test void testValidMove() throws Exception { setTextField("x", "2"); setTextField("y", "3");
 *       setTextField("destination_x", "4"); setTextField("destination_y", "5");
 * 
 *       invokePrivateMethod("move");
 * 
 *       ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
 *       verify(mockCommunication).sendMessage(captor.capture());
 *       assertTrue(captor.getValue().contains("\"start_x\":2")); }
 * 
 * @Test void testInvalidMove() throws FailedSendingMessageToServer { setTextField("x", "abc");
 *       setTextField("y", "def");
 * 
 *       assertThrows(NumberFormatException.class, () -> invokePrivateMethod("move"));
 *       verify(mockCommunication, never()).sendMessage(anyString()); }
 * 
 * @Test void testPassCommand() throws Exception { invokePrivateMethod("pass");
 *       verify(mockCommunication).sendMessage(contains("\"command\":\"pass\"")); }
 * 
 *       // Helper methods private <T> T getField(String name, Class<T> type) { try { Field field =
 *       SecondaryController.class.getDeclaredField(name); field.setAccessible(true); return
 *       type.cast(field.get(controller)); } catch (Exception e) { fail("Failed to get field " +
 *       name); return null; } }
 * 
 *       private void setTextField(String name, String value) { try { Field field =
 *       SecondaryController.class.getDeclaredField(name); field.setAccessible(true); ((TextField)
 *       field.get(controller)).setText(value); } catch (Exception e) { fail("Failed to set field "
 *       + name); } }
 * 
 *       private void invokePrivateMethod(String methodName) throws Exception { try { Method method
 *       = SecondaryController.class.getDeclaredMethod(methodName); method.setAccessible(true);
 *       method.invoke(controller); } catch (InvocationTargetException e) { if (e.getCause()
 *       instanceof Exception) { throw (Exception) e.getCause(); } throw e; } } }
 **/
