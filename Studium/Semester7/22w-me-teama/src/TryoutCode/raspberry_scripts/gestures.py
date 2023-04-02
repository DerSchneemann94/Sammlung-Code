from src.gesture_classifier import GestureClassifier
import cv2
from RoboModule import roboModule


if __name__ == '__main__':
    gestureClassifier = GestureClassifier()
    roboModule.initTcpSocket("192.168.2.217")
    cap = cv2.VideoCapture(0)
    while cap.isOpened() and not roboModule.exit_SIGINT:
        success, image = cap.read()
        if not success:
            print("Ignoring empty camera frame.")
            continue
        cmd = gestureClassifier.process(image)
        if not cmd:
            print(".", end="", flush=True)
            continue
        print(f"cmd {cmd} -> {roboModule.cmds[cmd]}")
        roboModule.sendCMD(cmd)
    cap.release()
