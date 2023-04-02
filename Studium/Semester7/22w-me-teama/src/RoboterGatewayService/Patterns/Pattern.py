class Pattern:
    def __init__(self, patternList):
        self.__pattern = patternList
        self.__index = 0

    def setNextOperation(self):
        self.__index += 1

    def updateOperationTime(self, interval):
        self.__pattern[self.__index][1] -= interval
        print("action: " + str(self.__pattern[self.__index][0]) + " - remaining Time:  " + str(self.__pattern[self.__index][1]))
        return self.__pattern[self.__index][1] <= 0

    def getPatternTask(self):
        return self.__pattern[self.__index][0]

    def isPatternDone(self):
        return self.__index == len(self.__pattern) - 1
