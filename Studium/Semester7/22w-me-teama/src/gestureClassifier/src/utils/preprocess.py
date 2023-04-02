import cv2
import numpy as np
import torch


def preprocess_landmarks(landmarks):
    landmark_tensor = torch.empty(42)
    for i in range(21):
        landmark_tensor[i * 2] = landmarks.landmark[i].x
        landmark_tensor[i * 2 + 1] = landmarks.landmark[i].y
    return landmark_tensor.reshape(1, 1, -1)


def preprocess_bytes(data):
    np_arr = np.frombuffer(data.getvalue(), np.uint8)
    image = cv2.imdecode(np_arr, cv2.IMREAD_COLOR)
    return image
