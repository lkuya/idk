package connection;

import com.Request;
import com.RequestType;
import com.Response;
import com.ResponseType;
import interfaces.FrontendController;
import interfaces.MainController;
import interfaces.StartController;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionController {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Thread thread;
    private FrontendController controller;
    private volatile boolean finished;

    public ConnectionController() {
        try {
            finished = false;

            socket = new Socket("localhost", 12345);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            thread = new Thread(new ReadThread());
            thread.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public ConnectionController(String domain, Integer port) {
        try {
            finished = false;
            socket = new Socket(domain, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            thread = new Thread(new ReadThread());
            thread.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void requestLogin(String username, String password) {

        Request request = new Request.Builder().type(RequestType.LOGIN).object(username + ";" + password).build();
        try {
            output.writeObject(request);
            output.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public synchronized void responseLogin(Response response) {
        StartController startCtrl = (StartController) controller;
        startCtrl.responseLogin(response);
    }

    public synchronized void responseGetAll(Response response) {
        MainController mainCtrl = (MainController) controller;
        mainCtrl.responseTotalScores(response);
    }


    class ReadThread implements Runnable {


        @Override
        public void run() {
            try {
                while (!finished) {
                    Response response = (Response) input.readObject();
                    if(response.type() == ResponseType.GETSCORES){
                        Platform.runLater(() -> responseGetAll(response));
                    }
                    else if(response.type() == ResponseType.LOGIN){
                        Platform.runLater(()->responseLogin(response));
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public FrontendController getController() {
        return controller;
    }

    public void setController(FrontendController controller) {
        this.controller = controller;
    }
}