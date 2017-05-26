#-----------------------------------------------------------------------
# File    : makefile    (directory: acopt)
# Contents: build ant colony optimization demonstration program
# Author  : Christian Borgelt
# History : 18.11.2005 file created
#           13.05.2006 adapted to jikes (IBM Java compiler)
#-----------------------------------------------------------------------
all:        acopt

#-----------------------------------------------------------------------
# Pointgon Viewer
#-----------------------------------------------------------------------
acopt:      *.java ../util/Scanner.java
	cd ..; jikes +P acopt/ACODemo.java
#	cd ..; javac -Xlint acopt/ACODemo.java

#-----------------------------------------------------------------------
# Distribution Packages
#-----------------------------------------------------------------------
dist:
	cd ..; \
        zip -r -q acopt.zip acopt/*.java ../util/Scanner.java \
                  acopt/makefile acopt/run* acopt/runjar* acopt/doc

jar:
	cd ..; \
	echo "Main-Class: acopt.ACODemo" > manifest; \
	jar cfm acopt.jar manifest \
                acopt/*.class util/Scanner.class; \
	rm -f manifest

#-----------------------------------------------------------------------
# Clean up
#-----------------------------------------------------------------------
clean:
	rm -f *.class ../util/*.class
