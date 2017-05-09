javac ./HelloWorld/*
rmiregistry &
java HelloWorld.Server &
sleep 3
java HelloWorld.Client
tail -f
