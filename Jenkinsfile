pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'release-radar:latest'
    }

    stages {

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImage = docker.build("${DOCKER_IMAGE}", '-f Dockerfile .')
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Stop and remove any existing container with the same name
                    sh """
                    if [ \$(docker ps -aq -f name=release-radar-container) ]; then
                        docker stop release-radar-container
                        docker rm release-radar-container
                    fi
                    """

                    // Run the new Docker container
                    sh 'docker run -d --name release-radar-container -p 8000:8000 ${DOCKER_IMAGE}'
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment to local Docker completed successfully!'
        }
        failure {
            echo 'Build or deployment to local Docker failed!'
        }
    }
}
