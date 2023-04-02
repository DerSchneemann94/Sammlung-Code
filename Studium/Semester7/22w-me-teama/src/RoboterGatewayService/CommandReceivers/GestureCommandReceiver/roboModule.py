import signal
import socket
import threading
import time
import sys
exit_SIGINT = False


def SIGINT_handler(signum, frame):
    global exit_SIGINT
    print(f"SIGINT {signum} {frame}")
    exit_SIGINT = True
    time.sleep(1)
    sys.exit()


signal.signal(signal.SIGINT, SIGINT_handler)

_cmds = {
    "left": b'{"N": 3 ,"D1": 1 ,"D2": 75}',
    "right": b'{"N": 3 ,"D1": 2 ,"D2": 75}',
    "forward": b'{"N": 3 ,"D1": 3 ,"D2": 75}',
    "backward": b'{"N": 3 ,"D1": 4 ,"D2": 75}',
    "stop": b'{"N": 100 }'
}

cmds = {
    "point_left": _cmds['left'],
    "point_right": _cmds['right'],
    "stop": _cmds['stop'],
    "vorwärts": _cmds['forward'],
    "links": _cmds['left'],
    "rechts": _cmds['right'],
    "rückwärts": _cmds['backward']
}

tcp_socket = None


def heartbeatEcho():
    global tcp_socket
    print(f"tcp_socket {tcp_socket}")
    while not exit_SIGINT:
        data = tcp_socket.recv(1024)
        # print(data)
        if data == b'{Heartbeat}':
            tcp_socket.send(data)
            continue
        print(f"RECV DATA: {data}")


def initTcpSocket(HOST="192.168.4.1", PORT=100):
    global tcp_socket
    tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp_socket.connect((HOST, PORT))

    download_thread = threading.Thread(target=heartbeatEcho, name="Downloader")
    download_thread.start()


def sendCMD(cmd="stop"):
    global tcp_socket
    tcp_socket.send(cmds[cmd])
