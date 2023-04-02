import numpy as np
import torch
from torch import nn
from torch.utils.data import Dataset, DataLoader, random_split

import pytorch_trainer
from pytorch_trainer import callbacks
from pytorch_trainer import metrics

from utils.base_dir import BASE_DIR
from utils.class_mapping import NC
from utils.model_dec import ConvClassifier


class GestureDataset(Dataset):
    def __init__(self):
        super().__init__()
        self.X = []
        self.y = []
        self.initialize()

    def initialize(self):
        gestures = list(sorted((BASE_DIR / "data").iterdir()))
        count = 0
        for gesture in gestures:
            if gesture.is_dir():
                for file in gesture.glob("*.npy"):
                    self.X.append(file)
                    self.y.append(count)
                count += 1

    def __len__(self):
        return len(self.y)

    def __getitem__(self, idx):
        X = np.load(str(self.X[idx])).astype(np.float32)
        X = torch.from_numpy(X)
        y = torch.tensor(self.y[idx], dtype=torch.long)

        return X, y


def batch_collate(x):
    xs = [item[0].reshape(1, -1) for item in x]
    ys = [item[1] for item in x]

    return torch.stack(xs), torch.stack(ys)


def main():
    ds = GestureDataset()
    train_split, test_split = random_split(ds, [0.7, 0.3], generator=torch.Generator().manual_seed(42))
    train_dataloader = DataLoader(train_split, batch_size=32, shuffle=True, collate_fn=batch_collate)
    test_dataloader = DataLoader(test_split, batch_size=16, shuffle=True, collate_fn=batch_collate)

    model = ConvClassifier(NC)
    print(model)
    loss_fn = nn.CrossEntropyLoss()
    optim = torch.optim.Adam(model.parameters())

    trainer = pytorch_trainer.Trainer(model, loss_fn, optim, train_dataloader, test_dataloader, device="cpu",
                                      metrics=[metrics.Accuracy()],
                                      callbacks=[callbacks.TensorBoard(),
                                                 callbacks.EarlyStopping("test_accuracy", min_delta=0.001, patience=10, restore_weights=True)])
    trainer.train(100)
    torch.save(model.state_dict(), "best.pth")


if __name__ == '__main__':
    main()
