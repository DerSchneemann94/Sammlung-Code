import copy
import signal

from CommandReceivers.GestureCommandReceiver.GestureCommandReceiver import GestureCommandReceiver
from CommandReceivers.VoiceCommandReceiver.VoiceCommandReceiver import VoiceCommandReceiver
from ActionSelector.ActionSelector import ActionSelector
from CommandReceivers.Models.Command import Command
from CommandReceivers.ThoughtCommandReceiver.ThoughtCommandReceiver import ThoughtCommandReceiver
from Classifier.CommandIdentityChecker import CommandIdentityChecker
from Patterns.PatternDao.PatternDaoImpl import PatternDaoImpl
from Patterns.PatternExecutor import PatternExecutor
from Patterns.PatternRecorder import PatternRecorder
from config.ConfigurationService import ConfigurationService
from Timer.Scheduler import Scheduler
from RobotCommunicator.RobotCommunicatorImpl import RobotCommunicatorImpl


class RobotGateway:
    CommandRefreshTimeInMs = 200

    def __init__(self):
        self.commandBasedOnStateAndInput = None
        self.actionBasedOnInput = None
        self.commandIdentifier = CommandIdentityChecker()
        self.patternDao = PatternDaoImpl()
        self.patternExecutor = PatternExecutor(self.patternDao)
        self.commandReceivers = [GestureCommandReceiver(
            3), VoiceCommandReceiver(2), ThoughtCommandReceiver(1)]
        self.robotCommunicator = RobotCommunicatorImpl()
        self.actionSelector = ActionSelector(self.patternExecutor)
        self.patternRecorder = PatternRecorder(self.patternDao)
        self.timer = Scheduler(
            RobotGateway.CommandRefreshTimeInMs, self.mainLoop)

    def mainLoop(self):
        self.chooseCommandFromReceivers()
        self.adjustRobotState()
        self.checkPatternRecorder()
        command = self.checkCommandSentToRobot()
        self.sendCommandToRobot(command)

    def chooseCommandFromReceivers(self):
        self.actionBasedOnInput = None
        self.commandBasedOnStateAndInput = None
        result = self.actionSelector.selectActionAndCommand(
            self.commandReceivers)
        self.actionBasedOnInput = result[0]
        self.commandBasedOnStateAndInput = result[1]

    def adjustRobotState(self):
        patternPriority = ConfigurationService.getValue(
            'Configuration', 'PatternPriority')
        if self.actionBasedOnInput == 'stop' and self.commandBasedOnStateAndInput is not None and self.commandBasedOnStateAndInput.source != 'Pattern':
            self.patternExecutor.stopPattern()
        elif CommandIdentityChecker.isCommandInSection('PatternName',
                                                       self.actionBasedOnInput) and self.patternRecorder.isInInitState():
            if not self.patternExecutor.isExecutingPattern() and self.patternExecutor.isPatternAvailable(
                    self.actionBasedOnInput):
                self.patternExecutor.startPattern(self.actionBasedOnInput)
                action = self.patternExecutor.getPatternTask()
                self.commandBasedOnStateAndInput = Command(
                    action=action, priority=patternPriority, source='Pattern')

    def checkPatternRecorder(self):
        if self.commandBasedOnStateAndInput is not None and not self.patternExecutor.isExecutingPattern():
            self.patternRecorder.checkInput(self.actionBasedOnInput)

    def checkCommandSentToRobot(self):
        command = self.commandBasedOnStateAndInput
        if command is not None:
            command = self.adjustCommandIfNecessary(command)
        return command

    def sendCommandToRobot(self, command):
        self.robotCommunicator.sendCommandToRobot(command)

    def adjustCommandIfNecessary(self, command):
        action = command.action
        if CommandIdentityChecker.isCommandInSection('PatternName', action) or CommandIdentityChecker.isCommandInSection(
                'PatternOperations', action):
            return None
        command.action = ConfigurationService.getValue('Actions', action)
        return command


def SIGINT_handler(signum, frame):
    print(f"SIGINT {signum} {frame}")
    quit()


signal.signal(signal.SIGINT, SIGINT_handler)

if __name__ == "__main__":
    RobotGateway()
