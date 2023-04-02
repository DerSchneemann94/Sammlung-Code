import threading
import time
import traceback


class Scheduler:

    def __init__(self, interval, function):
        self.interval = interval
        self.function = function
        self.pause = False
        self.run = True
        self.thread = threading.Thread(target=self.repeat)
        self.thread.start()



    def repeat(self):
        interval = self.interval / 1000
        next_time = time.time() + interval
        while self.run:
            if not self.pause:
                time.sleep(max(0, next_time - time.time()))
                try:
                    self.function()
                except Exception:
                    traceback.print_exc()
                next_time += (time.time() - next_time) // interval * interval + interval

    def stop(self):
        self.run = False

    def pause(self):
        self.pause = True

    def resume(self):
        self.pause = False
