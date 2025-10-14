#!/bin/bash
set -e

docker-compose -f ./docker-compose.yml --env-file ./.env --project-name=todo-list --profile local down -v
docker-compose -f ./docker-compose.yml --env-file ./.env --project-name=todo-list --profile local rm -f