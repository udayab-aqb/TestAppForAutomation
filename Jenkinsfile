pipeline {
    agent any

    tools {
        jdk 'JDK 21'
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
                checkout([$class: 'GitSCM', branches: [[name: '*/develop10Dec2025']],
                 userRemoteConfigs: [[url: 'https://github.com/udayab-aqb/TestAppForAutomation.git',
                 credentialsId: 'github-creds']]
                 ])
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

        stage('Generate Coverage Report') {
            steps {
                script {
                    // Install dependencies
                    sh 'python3 -m pip install pyyaml'
                    
                    // Generate report
                    sh 'python3 generate_coverage_report.py test-coverage-config.yml TEST_COVERAGE_REPORT.md src/test/java'
                    
                    // Archive artifact
                    archiveArtifacts artifacts: 'TEST_COVERAGE_REPORT.md', allowEmptyArchive: true
                    
                    // Commit and push back to repo
                    withCredentials([usernamePassword(credentialsId: 'github-creds', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                        sh ''
                            git config user.email "jenkins-bot@example.com"
                            git config user.name "Jenkins Bot"
                            git remote set-url origin https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/udayab-aqb/TestAppForAutomation.git
                            
                            git add TEST_COVERAGE_REPORT.md
                            if ! git diff-index --quiet HEAD; then
                                git commit -m "chore: update test coverage report [skip ci]"
                                git push origin HEAD:develop10Dec2025
                            else
                                echo "No changes to coverage report"
                            fi
                        ''
                    }
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