set -exuo pipefail
SONAR_HOST_URL="http://localhost:9000"
SONAR_ADMIN_USERNAME="admin"
SONAR_ADMIN_PASSWORD="sonar"
SONAR_INITIAL_ADMIN_PASSWORD="admin"
PROJECT_KEY="my-project-sonar-key"
PROJECT_NAME="my-project"
TOKEN_NAME="SONAR_TOKEN"

curl -v -u "${SONAR_ADMIN_USERNAME}:${SONAR_INITIAL_ADMIN_PASSWORD}" -X POST "${SONAR_HOST_URL}/api/users/change_password?login=${SONAR_ADMIN_USERNAME}&previousPassword=${SONAR_INITIAL_ADMIN_PASSWORD}&password=${SONAR_ADMIN_PASSWORD}"

curl -v -u "${SONAR_ADMIN_USERNAME}:${SONAR_ADMIN_PASSWORD}" -X POST "${SONAR_HOST_URL}/api/projects/create?name=${PROJECT_NAME}&project=${PROJECT_KEY}"

TOKEN=$(curl -u "${SONAR_ADMIN_USERNAME}:${SONAR_ADMIN_PASSWORD}" -X POST "${SONAR_HOST_URL}/api/user_tokens/generate" -d "name=${TOKEN_NAME}" | jq -r '.token')
printf "%s" "$TOKEN" > sonar_password
echo "Generated token: $TOKEN"