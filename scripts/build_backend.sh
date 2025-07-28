#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
BACKEND_DIR="${REPO_DIR}/backend"
DOCKERFILE_PATH="${BACKEND_DIR}/Dockerfile"

echo "Building Journey Planner Backend"

cd "${BACKEND_DIR}" || exit 1

echo "- Jar:"
gradle bootJar

echo ""
echo "- Docker image"
docker build -f "${DOCKERFILE_PATH}" -t journey-planner-backend:latest .
