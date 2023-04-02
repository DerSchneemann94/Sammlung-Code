from abc import ABC, abstractmethod


class PatternDaoInterface(ABC):

    @abstractmethod
    def getPattern(self, identifier):
        raise NotImplementedError

