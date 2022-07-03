package GUI;

import Methods.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    public JFrame frame;
    private int width = 800;
    private int height = 600;
    private Font font = new Font("Proxima Nova", Font.PLAIN, 16);

    public GUI() { frame = new JFrame(); }

    public void setupGUI() {

        // Setup basic frame attributes
        frame.setSize(width, height);
        frame.setTitle("API Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Options for HTTP Method
        String[] httpOptions = {"GET"};
        JComboBox<String> jComboBox = new JComboBox<>(httpOptions);
        jComboBox.setLocation(10, 10);
        jComboBox.setSize(200, 32);
        frame.add(jComboBox);

        // Text that says "Link: " right before text area to insert link
        JLabel link = new JLabel("Link: ");
        link.setLocation(230, 17);
        link.setSize(200, 16);
        link.setFont(font);
        frame.add(link);

        // Text area for user to insert link
        JTextArea linkText = new JTextArea();
        linkText.setLocation(280, 16);
        linkText.setSize(400, 20);
        frame.add(linkText);

        JButton sendRequest = new JButton("Send");
        sendRequest.setSize(75, 35);
        sendRequest.setLocation(400, 100);
        frame.add(sendRequest);

        JTextArea response = new JTextArea();
        response.setEditable(false);
        response.setLocation(10, 200);
        response.setSize(300, 300);
        frame.add(response);

        sendRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String option = jComboBox.getSelectedItem().toString();

                if(option.equals("GET")) {
                    getUtility util = new getUtility();
                    util.retrieveResponse(linkText.getText());
                }


            }
        });

        frame.setVisible(true);
    }
}















