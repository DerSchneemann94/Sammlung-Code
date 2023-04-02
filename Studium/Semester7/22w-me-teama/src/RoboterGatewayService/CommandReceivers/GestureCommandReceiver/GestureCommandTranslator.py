class GestureCommandTranslator:
    def __init__(self):
        self.translationDictionary = {
            'stop': 'stop', 'point_up': 'forward', 'point_down': 'backwards', 'point_right': 'right',
            'point_left': 'left', 'stone': 'PatternOne', 'metal_sign': 'StartRecording', 'ok_sign': 'StopRecording'}

    def translateInput(self, commandReceiverInput):
        return self.translationDictionary.get(commandReceiverInput)
