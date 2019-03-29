.PHONY: build

api_key="must be defined"

all: dep build

dep:
	$(MAKE) -C ../ build
	cp ../build/libs/* libs

build:
	./gradlew build

run:
	./gradlew run --args="$(api_key)"

# Upgrade gradle wrapper
upgrade_wrapper:
	gradle wrapper
