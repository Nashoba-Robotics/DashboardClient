1768ThreadingFramework
======================

A threading framework for an FRC robot written in Java.


  The framework is based on a client/server model. The client is the person implementing the framework. The server
is used to run actual function on the physical robot. The client sends in requests to the server, which holds the requests
in a message queue. The server reads messages from the queue and tells threads to wake up and run for a certain amount of
time. After this time, the thread checks if it still needs to be enabled. If it does, it continues another cycle. If it
does not, the thread goes back to sleep. 
  The threads that need to be created are registered by the client. Alternatively, the client may specify objects that
need to be threaded. 
