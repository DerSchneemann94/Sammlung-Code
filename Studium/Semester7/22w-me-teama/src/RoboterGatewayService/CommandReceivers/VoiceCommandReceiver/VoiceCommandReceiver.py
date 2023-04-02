import asyncio
import mqttools
from CommandReceivers.CommandReceiverInterface import CommandReceiverInterface
from CommandReceivers.Models.Command import Command
from CommandReceivers.VoiceCommandReceiver.VoiceCommandTranslator import VoiceCommandTranslator


class VoiceCommandReceiver(CommandReceiverInterface):
    serverIp = "193.197.231.210"
    broker_port = 1883

    def __init__(self, priority):
        super().__init__(priority=priority)
        self.voiceCommandTranslator = VoiceCommandTranslator()

    async def local(self):
        client = mqttools.Client(
            VoiceCommandReceiver.serverIp, VoiceCommandReceiver.broker_port)
        await client.start()
        await client.subscribe('thoughtcar', 0)
        while True:
            mqttMessage = await client.messages.get()
            message = mqttMessage.message.decode('utf-8')
            if mqttMessage is None:
                print("Broker Connection Lost")
            message = self.voiceCommandTranslator.translateInput(message)
            self.command = Command(
                source=self.type, action=message, priority=self.priority)

    def threadFunction(self):
        asyncio.run(self.local())
