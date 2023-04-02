import sys
from pathlib import Path

from RoboModule import roboModule
from RobotCommunicator.RobotCommunicatorInterface import RobotCommunicatorInterface

sys.path.append(str(Path().cwd().parent))
sys.path.append(str(Path().cwd()))


class RobotCommunicatorImpl(RobotCommunicatorInterface):

    def __init__(self):
        super().__init__()
        try:
            roboModule.initTcpSocket("192.168.2.154")
        except:
            try:
                roboModule.initTcpSocket("192.168.33.162")
            except:
                roboModule.initTcpSocket("192.168.66.162")

    def sendCommandToRobot(self, command):
        if command is None:
            return
        roboModule.sendCMD(command.action)
