package Server.Control;

import RemoteDesktop.View.MainForm;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
*
* @author dangth&dongnh
*/

public class ServerControl {

    private ServerSocket server;
    private MainForm view;
    private ArrayList<Socket> clients;

    public ServerControl(MainForm view) {
        try {
            server = new ServerSocket(view.getYourPort());
            clients = new ArrayList<Socket>();
            this.view = view;
            System.out.println("Server started...");
            new ServerRun().start();
            new UPdateNumberClientConnect().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CloseServer() {
        try {
            server.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void DisconnectAllClients() {
        for (Socket s : clients) {
            try {
                s.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        clients.clear();
        clients = new ArrayList<Socket>();
    }

    class UPdateNumberClientConnect extends Thread {

        @Override
        public void run() {
            while (!server.isClosed()) {
                for (Socket s : clients) {
                    if (s.isClosed()) {
                        clients.clear();
                        clients = new ArrayList<Socket>();
                    }
                }
                System.out.println("Number Client:" + clients.size());
            }
        }
    }

    class ServerRun extends Thread {

        @Override
        public void run() {
            while (view.isVisible() && !server.isClosed()) {
                Socket client;
                try {
                    client = server.accept();
                    System.out.println("FR SERVER:A request connect comming...");
                    if (clients.size() == 0) {
                        System.out.println("FR SERVER:accept a connect");
                        clients.add(client);
                        new ClientHandler(client).start();
                    } else {
                        System.out.println("FR SERVER:Refuse a connect");
                        client.close();
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    class ClientHandler extends Thread {

        private Socket incoming;

        public ClientHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(
                        incoming.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(
                        incoming.getInputStream());
                String passfrcl = (String) ois.readObject();
                System.out.println("FR SERVER:pass fr clent is" + passfrcl);
                if (!passfrcl.equals(view.getYourPass())) {
                    System.out.println("FR SERVER:Wrong pass");
                    oos.writeObject("NO");
                    incoming.close();
                    clients.remove(clients.size() - 1);
                } else {
                    oos.writeObject("OK");
                    Object o = ois.readObject();
                    Dimension dimension = new Dimension(0, 0);
                    if (o instanceof Dimension) {
                        dimension = (Dimension) o;
                    }
                    GetScreen getscreen = new GetScreen(oos, dimension, ServerControl.this);
                    getscreen.start();
                    DoAction doaction = new DoAction(ois, dimension, view);
                    doaction.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
