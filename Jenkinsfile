pipeline {
    agent any
    tools {
        nodejs 'NodeInstaller'
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('ss')
    }
    stages {
        stage('Checkout GIT') {
            steps {
                echo "Getting Project from Git"
                checkout scm
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'echo "Skipping unit tests"'
            }
        }

        stage('SonarQube analysis') {
            steps {
                script {
                    withSonarQubeEnv('devops') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar -Dsonar.login=squ_3ea4356b9bfaf618b8528e69537987a92e1119a6'
                    }
                }
            }
        }

        stage('Build & Push Docker Image (Backend)') {
            steps {
                script {
                    def imageName = "nourhenechalghoumi/devops_project"
                    // Skip the "mvn clean install" step
                    sh 'echo "Skipping mvn clean install"'
                    sh "docker build -t $imageName ."
                    sh "docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW"
                    sh "docker push $imageName"
                }
            }
        }

        stage('Send Email Notification') {
            steps {
                script {
                    def contenuReadMe = readFile('README.md') // Read README.md from the 'main' branch

                    def subject = 'New Project Commit - TEST'
                    def body = "A new commit has been made to the repository..\n\n${contenuReadMe}"
                    def to = 'test.devops697@gmail.com'

                    mail(
                        subject: subject,
                        body: body,
                        to: to
                    )
                }
            }
        }
    }
}

