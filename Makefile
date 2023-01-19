
SRCS=$(shell find . -name \*.java)
CLSS=$(SRCS:.java=.class)

$(CLSS): $(SRCS)
	javac -d ./Classes $^

run:
	java -cp ./Classes/ Main.Main

clean:
	find . -name \*.class | xargs rm
