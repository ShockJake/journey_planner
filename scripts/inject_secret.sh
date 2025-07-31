SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
REPO_DIR=${SCRIPT_DIR%/scripts}
BACKEND_DIR="${REPO_DIR}/backend"

SECRET_NAME=$1
SECRET_VALUE=$2

if [ -z "${SECRET_NAME}" ]; then
    echo "[ERROR] Secret name is not specified..."
    exit 1
fi

if [ -z "${SECRET_VALUE}" ]; then
    echo "[ERROR] Secret value is not specified..."
    exit 1
fi

sed -i -e "s/${SECRET_NAME}/${SECRET_VALUE}/g" "${BACKEND_DIR}/app/src/main/resources/properties.json"
