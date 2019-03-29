.PHONY: build

all: clean test

# clean
clean:
	./gradlew clean

# Run gradle test with information
test:
	./gradlew test --info

build: clean
	./gradlew build -x test 

# Create a release using GitHub
release:
	./gradlew distZip
	@echo "drag drop file"
	open build/libs/
	open build/distributions/
	open -a "Google Chrome" https://github.com/serpapi/google-search-results-java/releases
