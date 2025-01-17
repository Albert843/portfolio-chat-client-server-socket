## Этот проект - консольное приложение, которое представляет обычный чат, в рамках которого можно подключится к серверу, обрабатывающему запросы от клиентов.
 В проекте есть два модуля (приложения):
* _chat-server_ - это сервер чата, прослушивает входящие соединения и маршрутизирует сообщения между клиентами;
* _chat-client_ - это клиент чата, подключается к серверу (чату), отправляет сообщения в чат и получает сообщения от других участников чата.

Для сохранения конфигурации приложения в дереве проекта создана директория __'.config'__.
В ней находятся конфигурационные файлы приложения:
1. _'Chat-Demo.run.xml'_ - это compound-файл, в котором собраны (объединены) один сервер и три клиента.
2. _'Client-Demo-1.run.xml'_ - это файл настроек клиента 1.
3. _'Client-Demo-2.run.xml'_ - это файл настроек клиента 2.
4. _'Client-Demo-3.run.xml'_ - это файл настроек клиента 3.
5. _'Server-Demo.run.xml'_ - это файл настроек сервера.

Для работы приложения необходимо сначала запустить _Chat-Demo_, 
и он в свою очередь запустит один сервер и три клиента.
Клиенты могут отправлять и получать сообщения одновременно. 
Если клиент хочет покинуть чат, он закрывает свою сессию.
