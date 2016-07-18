# docker-mysql-integration
Proof of concept for using Docker to create a MySQL dependency as an integration test dependency

## Usage

## Setup

#### TCP
Expose docker host via environment variable: `tcp://<docker host>:<docker port>`
```
export DOCKER_HOST=tcp://192.168.99.100:2375
```

#### UXIX Socket
```
export DOCKER_HOST=unix:///var/run/docker.sock
```

## Run Integration Tests
```
./gradlew clean integrationTest
```
