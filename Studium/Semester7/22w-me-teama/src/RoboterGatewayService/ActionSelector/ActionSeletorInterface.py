from abc import ABC, abstractmethod


class ActionSelectorInterface(ABC):

    @abstractmethod
    def selectActionAndCommand(self, commandReceivers):
        raise NotImplementedError
