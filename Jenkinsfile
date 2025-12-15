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
                    // Debug info
                    echo "Checking Python environment..."
                    sh 'python3 --version'
                    sh 'python3 -m pip --version'
                    
                    // Install dependencies
                    echo "Installing dependencies..."
                    sh 'python3 -m pip install pyyaml'
                    sh 'python3 -m pip list'
                    
                    // Generate report
                    echo "Generating coverage report..."
                    sh 'python3 generate_coverage_report.py test-coverage-config.yml TEST_COVERAGE_REPORT.md src/test/java'
                    
                    // Verify report generation
                    sh '''
                        if [ -f "TEST_COVERAGE_REPORT.md" ]; then
                            echo "✅ Report generated successfully"
                            ls -l TEST_COVERAGE_REPORT.md
                            cat TEST_COVERAGE_REPORT.md | head -n 10
                        else
                            echo "❌ Report NOT generated"
                            exit 1
                        fi
                    '''
                    
                    // Archive artifact
                    archiveArtifacts artifacts: 'TEST_COVERAGE_REPORT.md', allowEmptyArchive: true
                    
                    // Commit and push back to repo
                    withCredentials([usernamePassword(credentialsId: 'github-creds', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                        sh '''
                            echo "Configuring git..."
                            git config user.email "jenkins-bot@example.com"
                            git config user.name "Jenkins Bot"
                            git remote set-url origin https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/udayab-aqb/TestAppForAutomation.git
                            
                            echo "Checking git status..."
                            git status
                            
                            git add TEST_COVERAGE_REPORT.md
                            
                            echo "Checking git status after add..."
                            git status
                            
                            if ! git diff-index --quiet HEAD; then
                                echo "Changes detected, committing..."
                                git commit -m "chore: update test coverage report [skip ci]"
                                
                                echo "Pushing changes..."
                                git push origin HEAD:develop10Dec2025
                                echo "✅ Push complete"
                            else
                                echo "No changes to coverage report"
                            fi
                        '''
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