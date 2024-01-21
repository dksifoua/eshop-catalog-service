.PHONY: postgres

postgres:
	@docker run --name postgres --env POSTGRES_PASSWORD=postgres -d postgres