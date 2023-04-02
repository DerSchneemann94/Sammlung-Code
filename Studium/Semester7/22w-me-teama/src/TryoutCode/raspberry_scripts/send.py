import signal
import socket
import threading
import time

exit_SIGINT = False


def SIGINT_handler(signum, frame):
    global exit_SIGINT
    print(f"SIGINT {signum} {frame}")
    exit_SIGINT = True


signal.signal(signal.SIGINT, SIGINT_handler)

tcp_socket = None


def heartbeatEcho():
    global tcp_socket
    print(f"tcp_socket {tcp_socket}")
    while not exit_SIGINT:
        data = tcp_socket.recv(1024)
        if data == b'{Heartbeat}':
            tcp_socket.send(data)
            continue
        print(f"RECV DATA: {data}")


HOST = "192.168.2.217"
PORT = 100

cmds = [b'{"N": 1 ,"D1": 1 ,"D2": 50, "D3": 1}', b'{"N": 1 ,"D1": 1 ,"D2": 0, "D3": 1}',
        b'{"N": 1 ,"D1": 2 ,"D2": 50,"D3":1}', b'{"N": 1 ,"D1": 2 ,"D2": 0,"D3":1}']

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.connect((HOST, PORT))
download_thread = threading.Thread(target=heartbeatEcho, name="Downloader")
download_thread.start()
# download_thread.terminate()
count = 0
while not exit_SIGINT:
    print(cmds[count % len(cmds)])
    tcp_socket.send(cmds[count % len(cmds)])
    time.sleep(3)
    count += 1

download_thread.join()
print('ende')
