from ActionSelector.ActionSeletorInterface import ActionSelectorInterface
from CommandReceivers.Models.Command import Command
from config.ConfigurationService import ConfigurationService


class ActionSelector(ActionSelectorInterface):
    def __init__(self, patternExecutor):
        super().__init__()
        self.patternExecutor = patternExecutor

    def selectActionAndCommand(self, commandReceivers):
        command = None
        action = None
        if self.patternExecutor.isExecutingPattern():
            action = self.patternExecutor.getPatternTask()
            command = Command(action=action, priority=ConfigurationService.getValue('Configuration', 'PatternPriority'),
                              source='Pattern')
        for commandReceiver in commandReceivers:
            if commandReceiver.command is None or commandReceiver.command.action is None:
                continue
            if commandReceiver.command.action == 'stop':
                action = 'stop'
                command = commandReceiver.command
            elif (command is None or commandReceiver.command.priority >= command.priority) and not self.patternExecutor.isExecutingPattern():
                action = commandReceiver.command.action
                command = commandReceiver.command
            commandReceiver.command = None
        return action, command
