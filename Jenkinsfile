pipeline {
  agent any

  tools {
    maven 'Maven3'
    jdk 'Java8'
  }

  stages {
    stage('build') {
      steps {
        sh 'mvn clean install -DskipTests'
      }
    }

    stage('test') {
      steps {
        sh 'mvn test'
      }
    }
  }
}