up:
	docker-compose up -d --build

logs:
	docker-compose logs -f

stop:
	docker-compose stop

down:
	docker-compose down

test:
	find ./ -name build | xargs rm -rf && ktlint
	./gradlew test clean
