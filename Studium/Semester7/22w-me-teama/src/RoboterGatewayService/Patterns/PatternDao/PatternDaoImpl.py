import copy
import csv
import os
import sys
from Patterns.Pattern import Pattern
from Patterns.PatternDao.PatternDaoInterface import PatternDaoInterface


class PatternDaoImpl(PatternDaoInterface):

    def __init__(self):
        self.patternsDictionary = {}
        self.getPatternsFromFile()

    def getPatternsFromFile(self):
        with open(os.path.join(os.path.join(sys.path[0], "Patterns/PatternDao/patterns.csv"))) as csvFile:
            csvContent = csv.reader(csvFile, delimiter=",")
            for pattern in csvContent:
                patternDictEntry = self.createPatternDictEntry(pattern)
                self.patternsDictionary[patternDictEntry[0]
                                        ] = patternDictEntry[1]

    def createPatternDictEntry(self, commands):
        element = 0
        patternName = ""
        commandList = []
        for command in commands:
            if element == 0:
                patternName = command
                element = 1
            else:
                action, time = command.split("-")
                commandList.append([action, float(time)])
        pattern = Pattern(commandList)
        return [patternName, pattern]

    def getPattern(self, identifier):
        pattern = self.patternsDictionary.get(identifier)
        return copy.deepcopy(pattern)

    def createPattern(self, name, actionList):
        with open(os.path.join(sys.path[0], "Patterns/PatternDao/patterns.csv"), "w") as csvFile:
            csvRow = name
            for patternAction in actionList:
                entry = patternAction[0] + '-' + str(patternAction[1])
                csvRow = csvRow + ',' + entry
            csvFile.write(csvRow)
        self.getPatternsFromFile()
