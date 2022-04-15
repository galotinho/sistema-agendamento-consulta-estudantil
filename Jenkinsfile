pipeline {
    agent any 
    tools {
        maven 'Maven 3.8.4'
        jdk 'OpenJdk11'
    } 
    stages {
        stage ('Build') {
            steps {    
                sh ' mvn clean install -DskipTests'
            }
        }
        stage ('Imagem docker') {
            steps {
                sh 'docker build . -t cae:${BUILD_NUMBER}'
            }
        }
        stage ('Run docker') {
            steps {
                sh ' docker stop cae || true' 
                sh ' docker rm cae || true'
                sh ' docker container run --network host -d --name cae cae:${BUILD_NUMBER}'
            }
        }        
    }
    
}
