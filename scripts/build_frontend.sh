#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
FRONTEND_DIR="${REPO_DIR}/frontend"
DOCKERFILE_PATH="${FRONTEND_DIR}/Dockerfile"

if [ -z "$IMAGE_NAME" ]; then
    echo "[ERROR] Image name is not specified"
    exit 1
fi

if [ -z "$IMAGE_TAG" ]; then
    echo "[ERROR] IMAGE_TAG is not specified"
    exit 1
fi

echo "Building Journey Planner Frontend"

cd "${FRONTEND_DIR}" || exit 1

echo "- Building distribution packages:"
if [ -d ./build ]; then
    rm -rd ./build
fi
npm run build
cp .env ./build/.env

echo ""
echo "- Docker image : tag='${IMAGE_TAG}'"
docker build -f "${DOCKERFILE_PATH}" -t "${IMAGE_NAME}:${IMAGE_TAG}" .
