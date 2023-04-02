class ThoughtCommandTranslator:
    def __init__(self):
        self.translationDictionary = {
            'stop': 'stop', 'push': 'forward', 'pull': 'backwards', 'right': 'right',
            'left': 'left',
            'neutral': 'stop'
        }

    def translateInput(self, commandReceiverInput):
        return self.translationDictionary.get(commandReceiverInput)
