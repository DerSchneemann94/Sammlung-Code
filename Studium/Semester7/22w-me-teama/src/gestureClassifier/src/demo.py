import cv2
import torch
import mediapipe as mp
from pathlib import Path

from utils.model_dec import ConvClassifier
from utils.class_mapping import GESTURE_MAP, NC
from utils.preprocess import preprocess_landmarks

mp_drawing = mp.solutions.drawing_utils
mp_drawing_styles = mp.solutions.drawing_styles
mp_hands = mp.solutions.hands

model = ConvClassifier(NC, return_proba=True)
model.load_state_dict(torch.load(Path(__file__).parent / "model" / "conv_clf.pth"))
model.eval()


def main():
    cap = cv2.VideoCapture(0)
    with mp_hands.Hands(
            model_complexity=0,
            min_detection_confidence=0.75,
            min_tracking_confidence=0.5) as hands:
        while cap.isOpened():
            success, image = cap.read()
            if not success:
                print("Ignoring empty camera frame.")
                # If loading a video, use 'break' instead of 'continue'.
                continue

            # To improve performance, optionally mark the image as not writeable to
            # pass by reference.
            image.flags.writeable = False
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            results = hands.process(image)

            # Draw the hand annotations on the image.
            image.flags.writeable = True
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
            if results.multi_hand_landmarks:
                for hand_landmarks in results.multi_hand_landmarks:
                    mp_drawing.draw_landmarks(
                        image,
                        hand_landmarks,
                        mp_hands.HAND_CONNECTIONS,
                        mp_drawing_styles.get_default_hand_landmarks_style(),
                        mp_drawing_styles.get_default_hand_connections_style())
                    X = preprocess_landmarks(hand_landmarks)
                    with torch.no_grad():
                        pred = model(X)
                    image = cv2.putText(image, GESTURE_MAP.get(pred.argmax().item()), (50, 50),
                                        cv2.FONT_HERSHEY_SIMPLEX, 1,
                                        (255, 0, 0), 2, cv2.LINE_AA)
                    image = cv2.putText(image, str(pred), (50, 100),
                                        cv2.FONT_HERSHEY_SIMPLEX, .4,
                                        (255, 0, 0), 2, cv2.LINE_AA)

            # Flip the image horizontally for a selfie-view display.
            cv2.imshow('Gesture classification', image)
            if cv2.waitKey(5) & 0xFF == 27:
                break
    cap.release()


if __name__ == '__main__':
    main()
