.PHONY: clean, postgres, test

clean:
	@echo "⏱️Cleaning up..."
	@if docker container ls | grep -q postgres; then \
		docker stop postgres; \
		docker container rm postgres; \
		docker image rm postgres; \
	fi
	@./gradlew clean
	@echo "✅Cleaning up...done!\n"

postgres:
	@docker run --name postgres \
		--publish 5432:5432 \
		--env POSTGRES_USER=postgres \
		--env POSTGRES_PASSWORD=postgres \
		--env POSTGRES_DB=eshop-product-catalog \
		--detach postgres

test:
	@./gradlew test