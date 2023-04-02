import cv2
import mediapipe as mp
import numpy as np
from mediapipe.framework.formats import landmark_pb2

from utils.base_dir import BASE_DIR

mp_drawing = mp.solutions.drawing_utils
mp_drawing_styles = mp.solutions.drawing_styles
mp_hands = mp.solutions.hands

GESTURE_NAME = input("Gesture name: ")


def main():
    gesture_path = BASE_DIR / "data" / GESTURE_NAME
    gesture_path.mkdir(exist_ok=True, parents=True)
    DO_CAPTURE = False

    count = int(len(list(gesture_path.iterdir())) / 2)

    cap = cv2.VideoCapture(0)
    with mp_hands.Hands(
            model_complexity=0,
            min_detection_confidence=.8,
            min_tracking_confidence=.7
    ) as hands:
        while cap.isOpened():
            success, image = cap.read()
            if not success:
                print("not opened")
                continue
            image = cv2.flip(image, 1)
            image.flags.writeable = False
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            results = hands.process(image)

            image.flags.writeable = True
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
            if results.multi_hand_landmarks:
                hand_landmarks: landmark_pb2.NormalizedLandmarkList
                for i, hand_landmarks in enumerate(results.multi_hand_landmarks):
                    mp_drawing.draw_landmarks(
                        image,
                        hand_landmarks,
                        mp_hands.HAND_CONNECTIONS,
                        mp_drawing_styles.get_default_hand_landmarks_style(),
                        mp_drawing_styles.get_default_hand_connections_style()
                    )
                    if DO_CAPTURE:
                        landmark_array = np.zeros(42)
                        for i in range(21):
                            landmark_array[i * 2] = hand_landmarks.landmark[i].x
                            landmark_array[i * 2 + 1] = hand_landmarks.landmark[i].y

                        np.save(f"{gesture_path}/{count:05}.npy", landmark_array)
                        cv2.imwrite(f"{gesture_path}/{count:05}.png", image)
                        count += 1

            image = cv2.putText(image, f"Gesture Name: {GESTURE_NAME}", (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255),
                                2, cv2.LINE_AA)
            image = cv2.putText(image, f"Capture: {DO_CAPTURE}", (10, 50), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255),
                                2, cv2.LINE_AA)
            cv2.imshow("Hands", image)

            pressed_key = cv2.waitKey(5) & 0xFF
            if pressed_key == 99:
                DO_CAPTURE = not DO_CAPTURE
            elif pressed_key == 27:
                break
    cap.release()


if __name__ == '__main__':
    main()
