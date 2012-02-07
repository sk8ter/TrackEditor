package com.softwerk.trackeditor.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.overlay.GeoXmlLoadCallback;
import com.google.gwt.maps.client.overlay.GeoXmlOverlay;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class TrackEditor implements EntryPoint {
    private static String KML_DEMO_URI = "http://gmaps-samples.googlecode.com/svn/trunk/ggeoxml/cta.kml";
    private final DecoratorPanel decorator = new DecoratorPanel();
    private MapWidget map;
    private GeoXmlOverlay geoXml = null;
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    private VerticalPanel mainPanel = new VerticalPanel();

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    public void onModuleLoad() {
        Button button = new Button("Button");
        buttonPanel.add(button);
        mainPanel.add(buttonPanel);
        decorator.add(mainPanel);

        Maps.loadMapsApi("AIzaSyDhEb7O5rSjmuHneI6TH8FVx6vdcjqzlQQ", "2", false, new Runnable() {
            public void run() {
                buildMap();

                GeoXmlOverlay.load(KML_DEMO_URI, new GeoXmlLoadCallback() {
                    @Override
                    public void onFailure(String s, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(String s, GeoXmlOverlay geoXmlOverlay) {
                        if (geoXml != null) {
                            return;
                        }
                        geoXml = geoXmlOverlay;
                        map.addOverlay(geoXml);
                    }
                });
            }
        });

        RootPanel.get("content").add(decorator);
//        oldInit();
    }

    private void buildMap() {
        map = new MapWidget();
        map.setSize("700px", "500px");
        map.setUIToDefault();
        mainPanel.add(map);
        map.addStyleName("mapStyle");
    }

//    private void oldInit() {
//        final Button sendButton = new Button("Send");
//        final TextBox nameField = new TextBox();
//        nameField.setText("GWT User");
//        final Label errorLabel = new Label();
//
//        // We can add style names to widgets
//        sendButton.addStyleName("sendButton");
//
//        // Add the nameField and sendButton to the RootPanel
//        // Use RootPanel.get() to get the entire body element
//        RootPanel.get("nameFieldContainer").add(nameField);
//        RootPanel.get("sendButtonContainer").add(sendButton);
//        RootPanel.get("errorLabelContainer").add(errorLabel);
//
//        // Focus the cursor on the name field when the app loads
//        nameField.setFocus(true);
//        nameField.selectAll();
//
//        // Create the popup dialog box
//        final DialogBox dialogBox = new DialogBox();
//        dialogBox.setText("Remote Procedure Call");
//        dialogBox.setAnimationEnabled(true);
//        final Button closeButton = new Button("Close");
//        // We can set the id of a widget by accessing its Element
//        closeButton.getElement().setId("closeButton");
//        final Label textToServerLabel = new Label();
//        final HTML serverResponseLabel = new HTML();
//        VerticalPanel dialogVPanel = new VerticalPanel();
//        dialogVPanel.addStyleName("dialogVPanel");
//        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//        dialogVPanel.add(textToServerLabel);
//        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//        dialogVPanel.add(serverResponseLabel);
//        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//        dialogVPanel.add(closeButton);
//        dialogBox.setWidget(dialogVPanel);
//
//        // Add a handler to close the DialogBox
//        closeButton.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//                dialogBox.hide();
//                sendButton.setEnabled(true);
//                sendButton.setFocus(true);
//            }
//        });
//
//        // Create a handler for the sendButton and nameField
//        class MyHandler implements ClickHandler, KeyUpHandler {
//            /**
//             * Fired when the user clicks on the sendButton.
//             */
//            public void onClick(ClickEvent event) {
//                sendNameToServer();
//            }
//
//            /**
//             * Fired when the user types in the nameField.
//             */
//            public void onKeyUp(KeyUpEvent event) {
//                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//                    sendNameToServer();
//                }
//            }
//
//            /**
//             * Send the name from the nameField to the server and wait for a response.
//             */
//            private void sendNameToServer() {
//                // First, we validate the input.
//                errorLabel.setText("");
//                String textToServer = nameField.getText();
//                if (!FieldVerifier.isValidName(textToServer)) {
//                    errorLabel.setText("Please enter at least four characters");
//                    return;
//                }
//
//                // Then, we send the input to the server.
//                sendButton.setEnabled(false);
//                textToServerLabel.setText(textToServer);
//                serverResponseLabel.setText("");
//                greetingService.greetServer(textToServer, new AsyncCallback<String>() {
//                    public void onFailure(Throwable caught) {
//                        // Show the RPC error message to the user
//                        dialogBox.setText("Remote Procedure Call - Failure");
//                        serverResponseLabel.addStyleName("serverResponseLabelError");
//                        serverResponseLabel.setHTML(SERVER_ERROR);
//                        dialogBox.center();
//                        closeButton.setFocus(true);
//                    }
//
//                    public void onSuccess(String result) {
//                        dialogBox.setText("Remote Procedure Call");
//                        serverResponseLabel.removeStyleName("serverResponseLabelError");
//                        serverResponseLabel.setHTML(result);
//                        dialogBox.center();
//                        closeButton.setFocus(true);
//                    }
//                });
//            }
//        }
//
//        // Add a handler to send the name to the server
//        MyHandler handler = new MyHandler();
//        sendButton.addClickHandler(handler);
//        nameField.addKeyUpHandler(handler);
//    }
}
