import asyncio
import mqttools


server = "193.197.231.210"
broker_port = 1883


async def subscriber():
    client = mqttools.Client(server, broker_port)

    await client.start()
    await client.subscribe("thoughtcar")
    await client.subscribe("#")

    while True:
        message: mqttools.Message = await client.messages.get()

        if message is None:
            print("Broker connection lost")
            break

        print(f"Topic: {message.topic}")
        print(f"Message: {message.message.decode('utf-8')}")


asyncio.run(subscriber())
