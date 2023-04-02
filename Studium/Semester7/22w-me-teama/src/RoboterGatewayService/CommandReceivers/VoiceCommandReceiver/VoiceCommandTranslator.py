class VoiceCommandTranslator:
    def __init__(self):
        self.translationDictionary = {
            'Anhalten': 'stop', 'Forwärts': 'forward', 'Rückwärts': 'backwards', 'Rechts': 'right',
            'Links': 'left',
            'vorwärts': 'forward',
            'rückwärts': 'backwards',
            'anhalten': 'stop',
            'links': 'left',
            'rechts': 'right',
            'stop': 'stop'
        }

    def translateInput(self, commandReceiverInput):
        return self.translationDictionary.get(commandReceiverInput)
