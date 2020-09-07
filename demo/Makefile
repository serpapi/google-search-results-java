.PHONY: build

api_key=${API_KEY}

all: clean dep build run

clean:
	./gradlew clean
	
dep:
	$(MAKE) -C ../ build
	rm -rf libs/*.jar
	cp ../build/libs/* libs

build: clean
	./gradlew build

run:
	./gradlew run --args="$(api_key)"

# Upgrade gradle wrapper
upgrade_wrapper:
	gradle wrapper
