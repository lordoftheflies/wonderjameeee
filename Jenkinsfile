pipeline {
  agent any
  stages {
    stage('') {
      steps {
        git(url: 'https://github.com/lordoftheflies/wonderjam.git', branch: 'master', changelog: true, poll: true, credentialsId: '40df72eb-96f1-4907-917e-f49834418037')
        emailext(subject: 'nem jó a házid', body: '1.', attachLog: true)
      }
    }
  }
}