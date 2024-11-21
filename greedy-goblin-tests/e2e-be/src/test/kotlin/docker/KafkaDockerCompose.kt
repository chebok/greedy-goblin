package docker

import fixture.docker.AbstractDockerCompose

object KafkaDockerCompose : AbstractDockerCompose(
    "kafka_1",
    9091,
    "docker-compose-kafka.yml",
)
