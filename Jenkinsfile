pipeline {
    agent any

    environment {
        GRADLE_HOME = tool name: 'Gradle', type: 'gradle'
        PATH = "${GRADLE_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = 'release-radar:latest'
        DOCKER_REGISTRY = 'my-docker-registry' // Change this to your Docker registry if you use one
        DOCKER_CREDENTIALS_ID = 'docker-credentials-id' // Change this to your Docker credentials ID in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo/your-project.git'
            }
        }

        stage('Build') {
            steps {
                sh 'gradle clean build'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImage = docker.build("${DOCKER_IMAGE}", '-f Dockerfile .')
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry("https://${DOCKER_REGISTRY}", "${DOCKER_CREDENTIALS_ID}") {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploying the Docker container
                    // Assuming you have a script or command to deploy the Docker container
                    sh './deploy.sh' // Modify this according to your deployment process
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build and deployment completed successfully!'
        }
        failure {
            echo 'Build or deployment failed!'
        }
    }
}
