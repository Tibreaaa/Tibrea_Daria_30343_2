package lab2ex2;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ex2 {
    public static void main(String args[]) throws IOException {
        JFrame frame = new JFrame("(Self)Messaging App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);

        JTextField inputField = new JTextField("Something");
        inputField.setBounds(10, 400, 500, 30);
        frame.add(inputField);

        JTextArea outputArea = new JTextArea();
        outputArea.setBounds(10,100,580,300);
        frame.add(outputArea);

        JButton button = new JButton("Send");
        button.setBounds(500, 400, 80, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sender(outputArea).start();

            }
        });
        frame.add(button);

        frame.setLayout(null);
        frame.setVisible(true);

        new Reciever("Serve", inputField).start();
    }
}

class Sender extends Thread{

    JTextArea outputArea;
    public Sender(JTextArea outputArea) {
        this.outputArea=outputArea;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            InetAddress adress = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, adress, 4445);
            socket.send(packet);

            packet= new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData());
            outputArea.append("\n>"+received);

            socket.close();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class Reciever extends Thread{

    private DatagramSocket socket = null;
    private JTextField inputField;

    public Reciever(String name, JTextField inputField) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
        this.inputField = inputField;
    }

    public void run() {
        while(true) {
            try {
                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);
                String dString = null;
                dString = inputField.getText();
                buf = dString.getBytes();

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}