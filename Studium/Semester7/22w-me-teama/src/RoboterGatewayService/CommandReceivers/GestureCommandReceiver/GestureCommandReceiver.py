import cv2
from src.gesture_classifier import GestureClassifier
from CommandReceivers.CommandReceiverInterface import CommandReceiverInterface
from CommandReceivers.GestureCommandReceiver.GestureCommandTranslator import GestureCommandTranslator
from CommandReceivers.Models.Command import Command


class GestureCommandReceiver(CommandReceiverInterface):
    def __init__(self, priority):
        self.gestureClassifier = GestureClassifier()
        self.gestureCommandTranslator = GestureCommandTranslator()
        super().__init__(priority)

    def threadFunction(self):
        cap = cv2.VideoCapture(0)
        while cap.isOpened():
            success, image = cap.read()
            if not success:
                print("Ignoring empty camera frame.")
                continue
            cmd = self.gestureClassifier.process(image)
            if not cmd:
                continue
            action = self.gestureCommandTranslator.translateInput(cmd)
            self.command = Command(
                source=self.type, action=action, priority=self.priority)
