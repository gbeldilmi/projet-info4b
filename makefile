#------------------------------------------------------------------------------#
# Project name & files                                                         #
#------------------------------------------------------------------------------#
PROJECT_NAME        := corewar
JAVA_FILES          := $(shell find $(PROJECT_NAME)/ -type f -name '*.java')
CLASS_FILES         := $(shell find $(PROJECT_NAME)/ -type f -name '*.class')
SERVER_ENTRY_POINT  := $(PROJECT_NAME).server.Main
CLIENT_ENTRY_POINT  := $(PROJECT_NAME).client.Main

#------------------------------------------------------------------------------#
# Commands                                                                     #
#------------------------------------------------------------------------------#
.PHONY : all, clean, run-client, run-server
all :
	javac $(JAVA_FILES)
clean :
	rm -v $(CLASS_FILES)
run-client : all
	java $(CLIENT_ENTRY_POINT)
run-server : all
	java $(SERVER_ENTRY_POINT)
