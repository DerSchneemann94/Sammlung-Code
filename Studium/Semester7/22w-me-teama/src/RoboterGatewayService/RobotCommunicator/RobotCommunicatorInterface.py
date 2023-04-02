from abc import ABC, abstractmethod


class RobotCommunicatorInterface(ABC):

    @abstractmethod
    def sendCommandToRobot(self, command):
        raise NotImplementedError
