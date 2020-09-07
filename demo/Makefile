.PHONY: build

ifndef API_KEY
$(error "API_KEY must be defined")
else
$(info "found API_KEY variable")
endif

all: init clean dep build run

init:
	gradle wrapper
	chmod +x ./gradlew

clean:
	./gradlew clean
	
dep:
	$(MAKE) -C ../ build
	rm -rf libs/*.jar
	cp ../build/libs/* libs

build: clean
	@./gradlew build

run:
	@./gradlew run --args="$(API_KEY)"
