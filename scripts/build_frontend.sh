#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
FRONTEND_DIR="${REPO_DIR}/frontend"
DOCKERFILE_PATH="${FRONTEND_DIR}/Dockerfile"
IMAGE_TAG=$1

if [ -z "$IMAGE_TAG" ]; then
    echo "[ERROR] IMAGE_TAG is not specified as first parameter of the scritp..."
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
docker build -f "${DOCKERFILE_PATH}" -t journey-planner-frontend:"${IMAGE_TAG}" .
