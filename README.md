# beertech.bancobeer
To run project, execcute in root directory:
docker-compose up

After that, go to http://localhost:15672, selecr Queues and after in conta-corrente.
To sendo message to queue:
{"tipo": "DEPOSITO", "valor": 1, "hash": 1}
{"tipo": "SAQUE", "valor": 1, "hash": 1}
{"tipo": "TRANSFERENCIA", "valor": 1, "origem": 1, "destino": 2}
