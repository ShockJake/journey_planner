#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
FRONTEND_DIR="${REPO_DIR}/frontend"
DOCKERFILE_PATH="${FRONTEND_DIR}/Dockerfile"

echo "Building Journey Planner Frontend"

cd "${FRONTEND_DIR}" || exit 1

echo "- Building distribution packages:"
npm run build
cp env_vars.env ./build/.env

echo ""
echo "- Docker image"
docker build -f "${DOCKERFILE_PATH}" -t journey-planner-frontend:latest .
