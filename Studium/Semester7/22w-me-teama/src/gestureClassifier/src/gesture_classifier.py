import io
from pathlib import Path

import cv2
import mediapipe as mp
import numpy as np
import torch

from .utils.class_mapping import GESTURE_MAP, NC
from .utils.model_dec import ConvClassifier as GestureClassifierModel
from .utils.preprocess import preprocess_landmarks, preprocess_bytes


class GestureClassifier:
    def __init__(self, detection_threshold=0):
        self.detection_threshold = detection_threshold
        self.hands = mp.solutions.hands.Hands(model_complexity=0, min_detection_confidence=.7,
                                              min_tracking_confidence=.5)
        self.model = GestureClassifierModel(NC, return_proba=True)
        self.model.load_state_dict(torch.load(Path(__file__).parent / "model" / "conv_clf.pth"))
        self.model.eval()

    def process(self, input_):
        """
        Process image and classify gesture
        Args:
            input_: Can be bytes io of the image which is then converted to a np array.
            If it is np.ndarray it is expected to be the image in BGR mode

        Returns:
            name of gesture as in class_mapping if a hand is detected, None otherwise
        """
        image = None
        if isinstance(input_, io.BytesIO):
            image = preprocess_bytes(input_)
        if isinstance(input_, np.ndarray):
            image = cv2.cvtColor(input_, cv2.COLOR_BGR2RGB)
        if image is None:
            return None

        results = self.hands.process(image)
        pred = None
        if results.multi_hand_landmarks:
            for hand_landmarks in results.multi_hand_landmarks:
                X = preprocess_landmarks(hand_landmarks)
                with torch.no_grad():
                    pred = self.model(X)

        if pred is None:
            return None
        pred_class = pred.argmax().item()
        if pred[0][pred_class].item() > self.detection_threshold:
            return GESTURE_MAP.get(pred.argmax().item(), None)


if __name__ == '__main__':
    clf = GestureClassifier()
