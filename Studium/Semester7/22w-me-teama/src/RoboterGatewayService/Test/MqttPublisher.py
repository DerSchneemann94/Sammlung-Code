import asyncio
from argparse import ArgumentParser

import mqttools

serverIp = "193.197.231.210"
broker_port = 1883


async def publisher(message: str):
    async with mqttools.Client(serverIp, broker_port) as client:
        client.publish(mqttools.Message("thoughtcar", message.encode("utf-8")))


if __name__ == "__main__":
    parser = ArgumentParser()
    parser.add_argument("msg", type=str)
    args = parser.parse_args()
    asyncio.run(publisher(args.msg))
