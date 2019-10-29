# MQTT Flink Connector

A small apache flink mqtt connector which connects to a 
mqtt broker and processes the incoming message stream.

The MQTT broker is expected on uri `tcp://127.0.0.1:1883` and 
messages will be received from topic `/foo`, 
also the client connects currently only with QOS `0` to the broker.
Acknowledgment is currently not implemented.
