import configparser


class ConfigurationService:
    config = {
        'Configuration': {'PatternPriority': 4},
        'PatternOperations': {'StartRecording': '', 'StopRecording': ''},
        'PatternName': {'PatternOne': ''},
        'Actions': {'stop': 'stop', 'forward': 'point_up', 'backwards': 'point_down', 'right': 'point_right', 'left': 'point_left'}
    }

    @staticmethod
    def getValue(section, name):
        return ConfigurationService.config[section][name]

    @staticmethod
    def getSection(section):
        return ConfigurationService.config[section]

    @staticmethod
    def getSections():
        return ConfigurationService.config.keys()
