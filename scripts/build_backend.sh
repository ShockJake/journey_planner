#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
BACKEND_DIR="${REPO_DIR}/backend"
DOCKERFILE_PATH="${BACKEND_DIR}/Dockerfile"
IMAGE_NAME=$1
IMAGE_TAG=$2

if [ -z "$IMAGE_NAME" ]; then
    echo "[ERROR] Image name is not specified as first parameter of the scritp..."
    exit 1
fi

if [ -z "$IMAGE_TAG" ]; then
    echo "[ERROR] IMAGE_TAG is not specified as second parameter of the scritp..."
    exit 1
fi

echo "Building Journey Planner Backend"

cd "${BACKEND_DIR}" || exit 1

echo -n "- Injecting secrets:"
sed -i -e "s/GEO_API/${GEO_API_KEY}/g" "./app/src/main/resources/properties.json"
echo " done"

echo "- Jar:"
gradle clean bootJar

echo ""
echo "- Docker image : tag='${IMAGE_TAG}'"
docker build -f "${DOCKERFILE_PATH}" -t "${IMAGE_NAME}:${IMAGE_TAG}" .
