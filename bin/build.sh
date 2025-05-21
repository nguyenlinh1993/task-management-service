#!/bin/bash
docker rm -f task-management-service
yes | docker image prune
docker build -t task-management-service -f Dockerfile .
