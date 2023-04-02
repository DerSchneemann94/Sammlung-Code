from config.ConfigurationService import ConfigurationService


class CommandIdentityChecker:

    @staticmethod
    def isCommandInSection(section, command):
        actions = ConfigurationService.getSection(section).keys()
        isInSection = False
        for action in actions:
            if command == action:
                isInSection = True
        return isInSection
