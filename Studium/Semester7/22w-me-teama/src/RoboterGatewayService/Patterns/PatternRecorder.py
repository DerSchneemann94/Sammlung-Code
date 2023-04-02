import time
from enum import Enum
from Classifier.CommandIdentityChecker import CommandIdentityChecker

RecordingStates = Enum('RecordingStates', ['Init', 'RecordingStartReceived', 'PatternNameReceived', 'ActionReceived',
                                           'RecordingEndReceived'])


class PatternRecorder:

    # input signs: start, end, patternSign, actionSign
    def __init__(self, patternDao):
        self.patternDao = patternDao
        self.transitionStates = {
            RecordingStates.Init: {'start': RecordingStates.RecordingStartReceived, 'end': RecordingStates.Init,
                                   'patternSign': RecordingStates.Init,
                                   'actionSign': RecordingStates.Init},
            RecordingStates.RecordingStartReceived: {'start': RecordingStates.RecordingStartReceived,
                                                     'end': RecordingStates.Init,
                                                     'patternSign': RecordingStates.PatternNameReceived,
                                                     'actionSign': RecordingStates.RecordingStartReceived},
            RecordingStates.PatternNameReceived: {'start': RecordingStates.PatternNameReceived,
                                                  'end': RecordingStates.Init,
                                                  'patternSign': RecordingStates.PatternNameReceived,
                                                  'actionSign': RecordingStates.ActionReceived},
            RecordingStates.ActionReceived: {'start': RecordingStates.ActionReceived,
                                             'end': RecordingStates.RecordingEndReceived,
                                             'patternSign': RecordingStates.ActionReceived,
                                             'actionSign': RecordingStates.ActionReceived},
        }
        self.transitionSymbol = ''
        self.recordingState = RecordingStates.Init
        self.patternName = None
        self.patternList = []
        self.actionStartTime = None
        self.recordingAction = None
        self.isRecording = None

    def checkInput(self, action):
        self.transitionSymbol = ''
        if action == 'StartRecording':
            self.transitionSymbol = 'start'
        elif action == 'StopRecording':
            self.transitionSymbol = 'end'
        elif CommandIdentityChecker.isCommandInSection('PatternName', action):
            self.transitionSymbol = 'patternSign'
        elif CommandIdentityChecker.isCommandInSection('Actions', action):
            self.transitionSymbol = 'actionSign'
        self.recordingState = self.transitionStates[self.recordingState][self.transitionSymbol]
        return self.checkStateChange(action)

    def checkStateChange(self, action=None):
        if self.recordingState == RecordingStates.Init:
            self.initializeNewPatternParameters()
        elif self.recordingState == RecordingStates.RecordingStartReceived:
            print('State Entered - StartRecording')
            self.isRecording = True
        elif self.recordingState == RecordingStates.PatternNameReceived:
            print('State Entered - PatternNameReceived')
            self.UpdatePatternName(action)
        elif self.recordingState == RecordingStates.ActionReceived:
            print('State Entered - ActionReceived')
            self.recordAction(action)
        elif self.recordingState == RecordingStates.RecordingEndReceived:
            print('State Entered - RecordingEndReceived')
            self.endRecording(action)

    def UpdatePatternName(self, action):
        if CommandIdentityChecker.isCommandInSection('PatternName', action):
            self.patternName = action

    def initializeNewPatternParameters(self):
        self.patternName = None
        self.patternList = []
        self.actionStartTime = None
        self.recordingAction = None
        self.isRecording = None

    def recordAction(self, action):
        if self.transitionSymbol != 'actionSign':
            return action
        if self.recordingAction is None:
            self.recordingAction = action
            self.actionStartTime = time.time()
        elif self.recordingAction != action:
            self.createPatternAction(action)

    def createPatternAction(self, action):
        recordedTime = (time.time() - self.actionStartTime) * 1000
        self.patternList.append([self.recordingAction, recordedTime])
        self.recordingAction = action
        self.actionStartTime = time.time()

    def endRecording(self, action):
        if self.actionStartTime is not None:
            self.createPatternAction(action)
            self.patternDao.createPattern(self.patternName, self.patternList)
        self.recordingState = RecordingStates.Init
        self.checkStateChange()

    def isInInitState(self):
        return RecordingStates.Init == self.recordingState
