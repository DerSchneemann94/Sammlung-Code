# How to install the dependencies

## You need to have an MQTT Server running

### Ubuntu instructions

- sudo apt-add-repository ppa:mosquitto-dev/mosquitto-ppa
- sudo apt-get update
- sudo apt-get install mosquitto
- sudo apt-get install mosquitto-clients
- sudo apt clean

Then start the MQTT Server

- sudo service mosquitto start

How to stop the MQTT Server

- sudo service mosquitto stop

After starting the MQTT Server the Server should be available under your hosts IP and the default port `1883`.

## Import an Alexa Skill

import the Alexa skill found in the src/alexa_skill folder into your Alexa Developer Console

Be sure to set your MQTT IP and Port in the Skill.

## BCI Setup

### Emotiv Epoc X headset
To fully utilize the capabilities of the headset, it is necessary to create an account with [Emotiv](https://www.emotiv.com/emotiv-launcher/) and purchase a license for their API. This will give you access to all of the features and functionality of the headset. Once you have done this, the next step is to download the Emotiv launcher app. This app will allow you to connect your headset to your device and start using it. It is worth noting that you can also use a simulation headset, which is a great option for testing and development purposes.
We need the client secret and the clientId to connect our code to the Emotiv app. The connection between the headset and the code is based on a socket connection. 

### Train BCI model

We will use the [EMOTIV BCI](https://www.emotiv.com/bci-guide/) software for our project. With this server we can use a raw model that we can train with ourselves.
We can train and save the commands like 

- push
- pull
- right
- left

### Run the server code

While the headset is connected, we need to start our server code. Install the requirements and then start the file ``train_feedback_live.py``. It connects to the MQTT server and sends a 
command to the server. The command is as follows:

``
{'action': 'neutral', 'power': 0.0, 'time': 1590736942.8479}
``

it will be sent to the topic ``thoughtcarBCI``. The mqtt server will listen to this topic and read the message. 

## SmartRobotCarV4.0_V1_20220303 setup

You can leave the Arduino code as it is.

### ESP32

Follow the instructions of the `ESP32 How to upload code.pdf` on how to set up your Arduino IDE to upload code to the ESP32

Then upload the Camera code in the `ESP32_CameraServer_AP_20220120/ESP32_CameraServer_AP_20220120.ino` project.

Attention: You can set up a WiFi Connection in the `CameraWebServer_AP.cpp` file. This way, you can connect a Raspberry and the Robo in a Network with Internet access, as the default WiFi of the Robot does not provide Internet access.

## To run the RobotGateway on a Raspberry Pi or on a Laptop:

on RaspberryPi 4 download Python 3.8 from source and compile it
as there is no python 3.8 package available on apt for arm64

you can tryout the instructions in the `python3.8_how_to.txt` to compile python from source.

use python 3.8

create an venv

- in the dependencies folder
  - pip install -r requirements.txt
- in src/RoboModule
  - pip install .
- in src/gestureClassifier
  - pip install .
- in src/bci
  - pip install .

Now you are ready to run the `RobotGateway.py` in the raspberry_scripts/Service folder.

Attention: Be sure that the "ELEGOO Robot" is available from your network.
You also need to specify the IP Address of the "ELEGOO Robot" in the `RobotCommunicatorImpl.py` file (`roboModule.initTcpSocket(f"{ROBO_IP_IN_HERE}")`).

You also have to specify the MQTT Server IP and Port int the following files:

- `ThoughtCommandReceiver.py`
- `VoiceCommandReceiver.py`
- `MqttPublisher.py`

You also need to make sure that your Raspberry Pi or Laptop has a Camera attached to receive Hand Gestures.
