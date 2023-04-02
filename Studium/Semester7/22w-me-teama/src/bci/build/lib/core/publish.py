import mqttools
import time


server = "193.197.231.210"
broker_port = 1883


async def publisher(message: str):
    async with mqttools.Client(server, broker_port) as client:
        client.publish(mqttools.Message("thoughtcarBCI", message.encode("utf-8")))
