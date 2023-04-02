from torch import nn


class ConvClassifier(nn.Module):
    def __init__(self, n_classes, return_proba=False):
        super().__init__()

        self.conv1 = nn.Sequential(
            nn.Conv1d(1, 16, 3, padding=1),
            nn.ReLU()
        )

        self.conv2 = nn.Sequential(
            nn.Conv1d(16, 64, 3, padding=1),
            nn.ReLU()
        )

        self.conv3 = nn.Sequential(
            nn.Conv1d(64, 128, 3, padding=1),
        )

        self.fc1 = nn.Sequential(
            nn.Linear(5376, 256),
            nn.ReLU()
        )

        self.flatten = nn.Flatten()

        self.output = nn.Sequential(
            nn.Linear(256, n_classes),
        )

        if return_proba:
            self.output.append(nn.Softmax(1))

    def forward(self, x):
        x = self.conv1(x)
        x = self.conv2(x)
        x = self.conv3(x)
        x = self.flatten(x)
        x = self.fc1(x)
        x = self.output(x)

        return x
