package org.example.chat.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {

        // Получение имени клиента
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваше имя: ");
        String name = scanner.nextLine();

        // Создание сокета и клиента
        InetAddress address = InetAddress.getLocalHost();
        Socket socket = new Socket(address, 4300);
        Client client = new Client(socket, name);

        // Вывод в консоль информации о параметрах подключения
        InetAddress inetAddress = socket.getInetAddress();
        System.out.println("InetAddress: " + inetAddress);
        String remoteIp = inetAddress.getHostAddress();
        System.out.println("Remote IP: " + remoteIp);
        System.out.println("LocalPort: " + socket.getLocalPort());

        // Создание потока-слушателя входящих сообщений от сервера
        client.listenForMessage();

        // Использование основного потока 'main' для отправки сообщений на сервер
        client.sendMessage();
    }
}
