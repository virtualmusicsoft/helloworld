set -exuo pipefail
mvn sonar:sonar \
  -Dsonar.projectKey=my-project-sonar-key \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=$(cat sonar_password) \
  -Dsonar.qualitygate.wait=true \
  -Dsonar.qualitygate.timeout=400