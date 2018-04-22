all: test

# Run gradle test with information
test:
	./gradlew test --info

# Publish artifact
publish:
	./gradlew publish

