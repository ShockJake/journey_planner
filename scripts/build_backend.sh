#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
BACKEND_DIR="${REPO_DIR}/backend"
DOCKERFILE_PATH="${BACKEND_DIR}/Dockerfile"
IMAGE_TAG=$1

if [ -z "$IMAGE_TAG" ]; then
    echo "[ERROR] IMAGE_TAG is not specified as first parameter of the scritp..."
    exit 1
fi

echo "Building Journey Planner Backend"

cd "${BACKEND_DIR}" || exit 1

echo "- Jar:"
gradle bootJar

echo ""
echo "- Docker image : tag='${IMAGE_TAG}'"
docker build -f "${DOCKERFILE_PATH}" -t journey-planner-backend:"${IMAGE_TAG}" .
