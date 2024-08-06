package org.example.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    //region Поля

    private Socket socket;
    private String name;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static ArrayList<ClientManager> clients = new ArrayList<>();

    //endregion


    //region Конструкторы

    public ClientManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clients.add(this);

            name = bufferedReader.readLine();
            System.out.println(name + " подключился к чату");
            broadcastMessage("Server: " + name + " подключился к чату");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    //endregion


    //region Методы

    /**
     * Метод закрытия всех потоков и подключения.
     * @param socket сокет для клиента
     * @param bufferedReader буфер на чтение данных
     * @param bufferedWriter буфер на запись данных
     */
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Удаление клиента из коллекции
        removeClient();
        try {
            // Завершить работу буфера на чтение данных
            if (bufferedReader != null) bufferedReader.close();

            // Завершить работу буфера на запись данных
            if (bufferedWriter != null) bufferedWriter.close();

            // Завершить работу c клиентским сокетом
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление клиента из коллекции
     */
    public void removeClient() {
        clients.remove(this);
        System.out.println(name + " покинул чат");
        broadcastMessage("Server: " + name + " покинул чат");
    }

    /**
     * Сообщение всем участникам чата, кроме отправителя сообщения
     * @param message сообщение клиента
     */
    private void broadcastMessage(String message) {
        for (ClientManager client : clients) {
            try {
                if (!client.name.equals(name) && message != null) {
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    /**
     * Метод создания потока на чтение сообщений от клиентов
     */
    @Override
    public void run() {
        String messageFromClient;
        while (!socket.isClosed()) {
            try {
                // Чтение данных
                messageFromClient = bufferedReader.readLine();
                // Отправка данных всем слушателям
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    //endregion
}
