package GUI;

import Methods.*;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI {

    public JFrame frame;
    private int width = 780;
    private int height = 800;
    private Font font = new Font("Proxima Nova", Font.PLAIN, 16);

    public GUI() {
        frame = new JFrame();
        setupGUI();
    }

    public void setupGUI() {

        // Setup basic frame attributes
        frame.setSize(width, height);
        frame.setTitle("API Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Options for HTTP Method
        String[] httpOptions = {"GET", "POST"};
        JComboBox<String> jComboBox = new JComboBox<>(httpOptions);
        jComboBox.setLocation(10, 10);
        jComboBox.setSize(200, 32);
        frame.add(jComboBox);

        // Text that says "Link: " right before text area to insert link
        JLabel link = new JLabel("URL: ");
        link.setLocation(230, 17);
        link.setSize(200, 16);
        link.setFont(font);
        frame.add(link);

        // Text area for user to insert link
        JTextArea linkText = new JTextArea();
        linkText.setLocation(280, 16);
        linkText.setSize(400, 17);
        frame.add(linkText);

        JButton sendRequest = new JButton("Send");
        sendRequest.setSize(75, 35);
        sendRequest.setLocation(690, 10);
        frame.add(sendRequest);

        JLabel responseText = new JLabel("Response:");
        responseText.setLocation(15, 335);
        responseText.setSize(200, 16);
        responseText.setFont(font);
        frame.add(responseText);

        JTextArea response = new JTextArea();
        response.setEditable(false);
        response.setLineWrap(true);
        JScrollPane responseScroll = new JScrollPane(response, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        responseScroll.setBounds(15, 355, 350, 400);
        frame.add(responseScroll);

        JButton copyResponse = new JButton("Copy Text");
        copyResponse.setSize(75, 35);
        copyResponse.setLocation(293, 323);
        frame.add(copyResponse);

        JLabel headersText = new JLabel("Headers:");
        headersText.setLocation(400, 335);
        headersText.setSize(200, 16);
        headersText.setFont(font);
        frame.add(headersText);

        JEditorPane headers = new JEditorPane("text/html", "");
        headers.setEditable(false);
        JScrollPane headersScroll = new JScrollPane(headers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        headersScroll.setBounds(400, 355, 350, 400);
        frame.add(headersScroll);

        JButton copyHeaders = new JButton("Copy Text");
        copyHeaders.setSize(75, 35);
        copyHeaders.setLocation(678, 323);
        frame.add(copyHeaders);

        JLabel res = new JLabel("Response Code: ");
        res.setLocation(15, 290);
        res.setSize(140, 16);
        res.setFont(font);
        frame.add(res);

        JLabel resResult = new JLabel();
        resResult.setLocation(145, 290);
        resResult.setSize(140, 16);
        resResult.setFont(font);
        frame.add(resResult);

        JLabel postText = new JLabel("JSON Content: ");
        postText.setLocation(15, 75);
        postText.setSize(140, 16);
        postText.setFont(font);
        frame.add(postText);

        JTextArea jsonContent = new JTextArea("Insert JSON text here when using the POST request.");
        jsonContent.setEditable(false);
        jsonContent.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(jsonContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(15, 95, 350, 170);
        frame.add(scroll);

        sendRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String option = jComboBox.getSelectedItem().toString();

                response.setText("");
                headers.setText("");

                if(option.equals("GET")) {

                    getUtility util = new getUtility();
                    String result;

                    try {
                       result = util.run(linkText.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                    int responseCode;
                    try {
                        responseCode = util.getResponseCode();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    if(responseCode < 299) {
                        response.setText(result);
                        headers.setText(util.htmlFormattedHeaders(util.headers));
                        resResult.setText(String.valueOf(responseCode));
                        resResult.setForeground(Color.GREEN);
                    }
                    else if(responseCode >= 300) {
                        resResult.setText(String.valueOf(responseCode));
                        resResult.setForeground(Color.RED);
                    }
                }

                if(option.equals("POST")) {

                    postUtility util = new postUtility();
                    int responseCode;
                    String result;

                    try {
                        result = util.run(linkText.getText(), jsonContent.getText());
                        responseCode = util.getResponseCode();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                    if(responseCode < 299) {
                        response.setText(result);
                        resResult.setText(String.valueOf(responseCode));
                        headers.setText(util.htmlFormattedHeaders(util.headers));
                        resResult.setForeground(Color.GREEN);
                    }
                    else if(responseCode >= 300) {
                        resResult.setText(String.valueOf(responseCode));
                        resResult.setForeground(Color.RED);
                    }


                }


            }
        });

        copyResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(response.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard ();
                clipboard.setContents(stringSelection, null);
            }
        });

        copyHeaders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(headers.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard ();
                clipboard.setContents(stringSelection, null);
            }
        });

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String currentChoice = jComboBox.getSelectedItem().toString();

                if(currentChoice.equals("GET")) {
                    jsonContent.setText("Insert JSON text here when using the POST request.");
                    jsonContent.setEditable(false);
                    response.setText("");
                    headers.setText("");
                    resResult.setText("");
                }

                if(currentChoice.equals("POST")) {
                    jsonContent.setText("");
                    jsonContent.setEditable(true);
                    response.setText("");
                    headers.setText("");
                    resResult.setText("");
                }

            }
        });

        frame.setVisible(true);
    }
}















