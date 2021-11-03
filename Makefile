.PHONY: build

all: clean test

# clean
clean:
	mvn clean

# Run tests
test:
	mvn surefire:test

build: clean
	mvn install

oobt: build
	$(MAKE) -C demo all

# Create a release using GitHub
release: build
	@echo "drag drop file"
	open build/libs/
	open build/distributions/
	open -a "Google Chrome" https://github.com/serpapi/google-search-results-java/releases
