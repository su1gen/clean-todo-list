To run project rename .env.example to .env in root folder
In todo-list-ui rename .env.example to .env.local before then run npm run dev

Run prod build, check ports
> docker compose -f ./docker-compose.prod.yml up --build

For seeding data run
> docker-compose -f .\docker-compose.prod.yml --profile seed up seed-data
