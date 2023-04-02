import asyncio
import mqttools
import time


server = "193.197.231.210"
broker_port = 1883


async def publisher(message: str):
    async with mqttools.Client(server, broker_port) as client:
        client.publish(mqttools.Message("thoughtcar", message.encode("utf-8")))


while (True):
    asyncio.run(publisher("TestTest123"))
    asyncio.run(publisher("vorwärts"))
    time.sleep(1)
    asyncio.run(publisher("rückwärts"))
    time.sleep(1)
    asyncio.run(publisher("anhalten"))
    time.sleep(1)
    asyncio.run(publisher("links"))
    time.sleep(1)
    asyncio.run(publisher("rechts"))
    time.sleep(1)
    asyncio.run(publisher("stop"))
