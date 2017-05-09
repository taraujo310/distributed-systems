# RMI Hello World Example

This example (I discover in my debug search) is very similar to [Oracle's Example][1]. So, there are 3 files following this hierarchy:

- RMIHelloWorld
  - src
    - HelloWorld
      - Client.java
      - IHello.java
      - Server.java

First file
----------

Because Java is a Statically Typed Language, it requires us to define an IDL (_Interactive Data Language_). The IDL is an interface for stub generation. But what is a stub?

When programming between processes (or even machines) there are a lot of concerns that make it complex like networking programming logic, memory management, etc. A stub hides it all and the programmer can invoke a method without have to worry about these concerns. So, for example, a client stub converts a method call into networking communication.

Stubs are generated using IDL and this is accomplished (in Java) by implementing an interface describing the methods that a client can invoke and a server has to implement.

In this example, this file is `IHello.java`. It describes the say() method that will return a string.

Second file - The Server
-----------

This is an Client/Server application. So we need the Client and Server classes. Now I'll explain the Server.

First: it implements IHello interface, so it has a method `say()` returning _"Hello World!"_.

Then there is the main method. When we execute the Server this will be the starter. It gets an Interface of Server and create a stub of IHello using this instance of the server. As said before, this will be a remote "object" with the ability to reference the real object throught the network.

Next, it binds the stub to a registry, before located, with an index _"Hello"_. After that it prints that the server is ready.

Third file - The Client
-----------

This is the simplest file. To connect to the server, the client need to find the registry that the stub was binded. After that it recovers the indexed stub object with name _"Hello"_.

Then, it just need to use the stub to invoke the remote methods.

How to Run these files?
-----------------------
You will need 3 windows of your terminal.

1º) Run the rmiregistry!

    rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false

- Using the argument "java.rmi.server.useCodebaseOnly=false" says to the registry to locate files outside the registry own classpath.

2º) Run the server!

	javac -d bin src/HelloWorld/IHello.java src/HelloWorld/Server.java src/HelloWorld/Client.java
    java -cp bin/ -Djava.rmi.server.codebase=file:/home/thiago/workspace/faculdade/SD/rmi/RMIHelloWorld/bin/ HelloWorld.Server

- The argument -cp introduces the bin folder to the classpath, i.e., it helps java to locate the user classes.
- The codebase argument tells to the JVM the place from where to load the user classes. (All tutorials uses the relative path to the folder but it doesn't work for me).
- It will print "Hello Server is ready!" and it hangs until the user kill the process.

3º) Run the client!

    java -cp bin/ HelloWorld.Client

- it uses -cp to add the bin folder to the classpath and execute the Client class.
- It will print "Hello Server says: Hello World!".

Troubleshooting
---------------

There are a lot of reasons to this execution to fail! Refer to the [Oracle's Documentation][2] to see the troubleshooting tips. I looked into SO, foruns, professors sites, etc. but this was the only source that helped me.

[1]:http://docs.oracle.com/javase/6/docs/technotes/guides/rmi/hello/hello-world.html
[2]:http://docs.oracle.com/javase/6/docs/technotes/guides/rmi/codebase.html
