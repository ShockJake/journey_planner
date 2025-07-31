SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
export IMAGE_TAG=1234
export IMAGE_NAME=journey-planner-backend

"${SCRIPT_DIR}"/build_backend.sh

export IMAGE_NAME=journey-planner-frontend
"${SCRIPT_DIR}"/build_frontend.sh

unset IMAGE_TAG
unset IMAGE_NAME
