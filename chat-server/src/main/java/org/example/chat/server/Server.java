package org.example.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    //region Поля

    /**
     * Серверный сокет
     */
    private final ServerSocket serverSocket;

    //endregion


    //region Конструкторы

    /**
     * Инициализация серверного сокета
     * @param serverSocket серверный сокет
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

    }

    //endregion


    //region Методы

    /**
     * Метод работы сервера
     */
    public void runServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Подключен новый клиент!");
                ClientManager clientManager = new ClientManager(socket);
                new Thread(clientManager).start();
            }
        } catch (IOException e) {
            closeSocket();
        }
    }

    /**
     * Закрытие серверного сокета, в случае возникновения мсключения
     */
    public void closeSocket() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //endregion
}
