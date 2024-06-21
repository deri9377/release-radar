pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "$JAVA_HOME/bin:$PATH"
        DOCKER_IMAGE_TAG = 'release-radar:latest'
    }

    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh "./gradlew clean build"
            }
        }
        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    docker.build(DOCKER_IMAGE_TAG, '-f Dockerfile .')
                }
            }
        }
        stage('Deploy Docker Container') {
            steps {
                script {
                    // Run Docker container
                    docker.image(DOCKER_IMAGE_TAG).run('-d -p 8080:8080')
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline successfully completed!'
            // You can trigger notifications or further actions on success
        }
        failure {
            echo 'Pipeline failed!'
            // You can trigger notifications or rollback actions on failure
        }
    }
}