pipeline {
    agent any

    stages {
        stage("Git Checkout") {
            steps {
                git url: 'https://github.com/gowthamchi/basic-nodeproject.git', branch: 'main'
            }
        }

        stage("Docker Build") {
            steps {
                sh 'docker build -t nodeimage .'
            }
        }

        stage("Docker Push") {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerlogin',
                    usernameVariable: 'USERNAME',
                    passwordVariable: 'PASSWORD'
                )]) {
                    sh '''
                    echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin
                    docker tag nodeimage gowtham1198/nodeimage:latest
                    docker push gowtham1198/nodeimage:latest
                    '''
                }
            }
        }

        stage("Docker Run") {
            steps {
                sh 'docker run -d -p 3000:3000 gowtham1198/nodeimage:latest'
            }
        }
    }
}
