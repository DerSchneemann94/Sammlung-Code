class Command:

    def __init__(self, action, priority, source):
        self.action = action
        self.priority = priority
        self.source = source

    def __str__(self):
        return self.source + "command: " + self.action + "\n" + "priority: " + str(self.priority)
