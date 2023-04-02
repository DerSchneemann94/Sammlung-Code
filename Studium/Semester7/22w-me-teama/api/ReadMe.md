We did not create an API ourselves but we used different and already existing communication Protocols:

- for Alexa and the BCI Commands we hosted a MQTT Server

  - the Alexa and BCI Headset could then publish MQTT messages
  - which the Raspberry Pi could receive and mediate to the Robot

- the Robots ESP32 Micro controller had its own communication protocol which runs over a normal TCP connection which can be referenced in the `Communication protocol for Smart Robot Car.pdf`
