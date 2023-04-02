from abc import ABC, abstractmethod
import threading


class CommandReceiverInterface(ABC):

    def __init__(self, priority):
        self.priority = priority
        self.command = None
        self.type = self.__class__.__name__.split("CommandReceiver")[0]
        self.thread = threading.Thread(target=self.threadFunction)
        self.thread.start()

    @abstractmethod
    def threadFunction(self):
        raise NotImplementedError

