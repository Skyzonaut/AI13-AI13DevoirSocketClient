### Java Socket messenger client application

This application is the server side of a wider messaging application made of two executable jar and associated packages
- ServerSide
- ClientSide

The current application will create a Socket connection to the `adress:port` of the server.

The client if connected, will have to choose a pseudo that is not yet taken. If the pseudo is accepted, then only the client can start sending messages.

Every client connected to the same server will have access to the upcoming messages and will see the pseudo clearly.

The messages are broadcasted to all clients.

To stop the application, simply enter the command :

`exit` on the stdout.

> Be carefull of not sending AS A MESSAGE the word exit. Or else the server will take it as a disconnection request

### Launch
To launch the server, simply go to the main directory.
Then execute this command :

````bash
java -jar DevoirSocketClient.jar
````

The client will be connected on the default `localhost` on the 
`10810` port. 
> If the server is connected on the default port, then the client shall be launched as such

However, it is possible to select the port we want to launch the client on. But the server MUST be launched on the same port.

To do this, simply add it as a parameter to the launch command

````bash
java -jar DevoirSocketClient.jar <port>
````

### Exceptions
If the server carshed, the connection error will be catch and a message will be displayed to all connected clients.

If a client disconnect improperly (Ctrl-C / Ctrl-Q), the disconnection will be catch by the server and be displayed, on top of displaying the disconnection to others
