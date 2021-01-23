import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket clientSocket = null;
        Scanner scanner = new Scanner(System.in);


        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен ");
            clientSocket =serverSocket.accept();
            System.out.println("Подключен клиент : " +clientSocket.getRemoteSocketAddress());
            DataInputStream inputStream =new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());


            Thread threadR = new Thread(()->{
                try {
                    while (true){
                        outputStream.writeUTF(scanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            threadR.setDaemon(true);
            threadR.start();

            while (true){
                String str =inputStream.readUTF();
                if (str.equals("/close")){
                    System.out.println("Client disconnect ");
                    outputStream.writeUTF("/close");
                    break;
                }else {
                    System.out.println("Client :" +str);
            }

        }

    } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                clientSocket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

}

