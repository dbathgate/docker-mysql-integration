# docker-mysql-integration
Proof of concept for using Docker to create a MySQL host as an integration test dependency

# Usage

## Setup

#### TCP
Expose docker host via environment variable: `DOCKER_HOST=tcp://<docker host>:<docker port>`
```
export DOCKER_HOST=tcp://192.168.99.100:2375
```

#### UXIX Socket (Currently does not work with Docker beta version for OS X)
```
export DOCKER_HOST=unix:///var/run/docker.sock
```

## Run Integration Tests
```
./gradlew clean integrationTest
```
