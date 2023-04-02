import time
from Timer.Scheduler import Scheduler


class PatternExecutor:
    patternScanningRate = 50

    def __init__(self, patternDao):
        self.__executingPattern = False
        self.__patternScheduler = None
        self.__pattern = None
        self.__patternDao = patternDao
        self.__lastUpdateTime = None

    def isExecutingPattern(self):
        return self.__executingPattern

    def isPatternAvailable(self, identifier):
        pattern = self.__patternDao.getPattern(identifier)
        if pattern is not None:
            return True
        else:
            return False

    def startPattern(self, identifier):
        pattern = self.__patternDao.getPattern(identifier)
        self.__pattern = pattern
        self.__executingPattern = True
        self.__lastUpdateTime = time.time()
        self.__patternScheduler = Scheduler(PatternExecutor.patternScanningRate,
                                            self.updatePatternExecution)

    def stopPattern(self):
        if self.isExecutingPattern():
            self.__patternScheduler.stop()
            self.__executingPattern = False

    def pausePattern(self):
        self.__patternScheduler.pause()

    def resumePattern(self):
        self.__lastUpdateTime = time.time()
        self.__executingPattern = False
        self.__patternScheduler.resume()

    def getPatternTask(self):
        return self.__pattern.getPatternTask()

    def updatePatternExecution(self):
        if not self.__executingPattern:
            self.__pattern = None
            return
        timeDiff = (time.time() - self.__lastUpdateTime) * 1000
        self.__lastUpdateTime = time.time()
        setNextOperation = self.__pattern.updateOperationTime(timeDiff)
        if setNextOperation:
            if self.__pattern.isPatternDone():
                self.stopPattern()
            else:
                self.__pattern.setNextOperation()
