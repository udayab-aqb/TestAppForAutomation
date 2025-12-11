pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven3'
    }

    stages {

        stage('Check Git') {
            steps {
                    sh 'git --version'
            }                                       
        }

        stage('Checkout') {
            steps {
                git branch: 'develop10Dec2025',
                    url: 'https://github.com/udayab-aqb/TestAppForAutomation.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**/*.log', allowEmptyArchive: true
        }
    }
}