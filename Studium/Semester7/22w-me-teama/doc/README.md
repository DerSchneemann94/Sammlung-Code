# ThoughRobot Team A Dietenmeier Karami Pijarowski Rothenberger

## Setup the Project

### What you need

- A Laptop or a Raspberry Pi 4
  - with a camera attached to recognize hand gestures
- An Alexa
- An EMOTIV EPOC X (BCI Headset)
- The Elegoo Smart Robot Car Kit V4
- optional a dedicated MQTT Server

### setup

Reference the `ReadMe.md` in the `dependencies` folder on how to set up the project.

### run

when everything is set up, you can power up the Robot as well as the RobotGateway on your Raspberry Pi or Laptop.

Then you can send commands with the BCI Headset, Alexa, or Hand Gestures.


### Add additional reveivers

If an additional receiver / technology shall be included in the system it needs to have a corresponding receiver class implementing the ReceiverInteface. The newly created Receiver than needs to be added to the RobotGateway.py contructor simmilar to the existing Receivers. 
The constructor element priority of the Receiver determines the Receivers importance. With multiple inputs arriving at the same time, the system chooses the input from the receiver with the highest priority. 

### Add additional commands

If the User desires to add additional Actions, or Patternnames the configuration mapping in the ConfigurationService.py needs to be extended. Additional actions belong in the Actions-Map and accordingly new PatternNames into the PatternName-Map.
The interpretation of the individual commands happens in the RoboModul. The Module translates the command strings into instructions for the robot. In our case driving instructions. Therefore any additional movement command needs a new instruction set. 

### summary

The ThoughRobot explores alternative inputs for Robots.

In this project we implemented the following:

- Hand Gestures
- Voice Commands
- Thought Control

### Contributors

- Roman Dietenmeier 79709
- Ali Karami 80018
- Matthias Pijarowski 79673
- Domenic Rothenberger 78158
