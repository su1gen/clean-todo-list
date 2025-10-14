#!/bin/bash

set -e

docker-compose -f ./docker-compose.yml \
  --env-file .env \
  --project-name=todo-list \
  --profile local \
  up -d --remove-orphans