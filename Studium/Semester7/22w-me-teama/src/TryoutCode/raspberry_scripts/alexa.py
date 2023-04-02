import asyncio
import mqttools

from RoboModule import roboModule
roboModule.initTcpSocket("192.168.2.154")


server = "193.197.231.210"
broker_port = 1883


async def subscriber():
    client = mqttools.Client(server, broker_port)

    await client.start()
    await client.subscribe("thoughtcar")

    while not roboModule.exit_SIGINT:
        message: mqttools.Message = await client.messages.get()

        if message is None:
            print("Broker connection lost")
            break

        if message.topic == 'thoughtcar':
            processThoughtCar(message.message.decode('utf-8'))
            continue

        print(f"Topic: {message.topic}")
        print(f"Message: {message.message.decode('utf-8')}")


def processThoughtCar(message):
    print('thoughtcar: '+message)

    for cmd in roboModule.cmds.keys():
        if message == cmd:
            roboModule.sendCMD(cmd)
            return
    print(f"unknown ThoughtCar Command: {message}")


def main():
    asyncio.run(subscriber())


if __name__ == '__main__':
    main()
