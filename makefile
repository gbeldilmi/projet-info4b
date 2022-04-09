#------------------------------------------------------------------------------#
# Project name & files                                                         #
#------------------------------------------------------------------------------#
PROJECT_NAME        := corewar
JAVA_FILES          := $(shell find $(PROJECT_NAME)/ -type f -name '*.java')
SERVER_ENTRY_POINT  := $(PROJECT_NAME).server.Main
CLIENT_ENTRY_POINT  := $(PROJECT_NAME).client.Main

#------------------------------------------------------------------------------#
# Commands                                                                     #
#------------------------------------------------------------------------------#
.PHONY : all, jar, clean, run-client, run-server
all :
	javac $(JAVA_FILES)
jar : all
	jar cfv $(PROJECT_NAME).jar $(PROJECT_NAME)/*/*.class $(PROJECT_NAME)/*/*/*.class
clean :
	rm -v $(PROJECT_NAME)/*/*.class $(PROJECT_NAME)/*/*/*.class
run-client : all
	java $(CLIENT_ENTRY_POINT)
run-server : all
	java $(SERVER_ENTRY_POINT)
