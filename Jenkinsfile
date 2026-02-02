pipeline {
    agent any

    tools {
        // Assuming 'jdk17' is configured in Jenkins Global Tool Configuration
        // If not, you might need to configure it or rely on the agent's environment
        jdk 'jdk17' 
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // On Windows, use gradlew.bat
                bat 'gradlew.bat clean build'
            }
        }

        stage('Test') {
            steps {
                bat 'gradlew.bat test'
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
