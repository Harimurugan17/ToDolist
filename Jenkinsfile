pipeline {
    agent any

    // tools {
    //    jdk 'jdk21' 
    // }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Assemble creates the JAR without running tests (tests are in the next stage)
                bat 'gradlew.bat clean assemble'
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
        success {
            archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: false
        }
    }
}
