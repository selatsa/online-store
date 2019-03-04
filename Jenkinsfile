node {
    stage('checkout') {
        checkout scm
    }

    docker.image('jhipster/jhipster:v5.8.1').inside('-u jhipster -e GRADLE_USER_HOME=.gradle') {
        stage('check java') {
            bat "java -version"
        }

        stage('clean') {
            bat "gradlew.bat clean --no-daemon"
        }

        stage('npm install') {
            bat "gradlew.bat npm_install -PnodeInstall --no-daemon"
        }

        stage('backend tests') {
            try {
                bat "gradlew.bat test -PnodeInstall --no-daemon"
            } catch(err) {
                throw err
            } finally {
                junit '**/build/**/TEST-*.xml'
            }
        }

        stage('frontend tests') {
            try {
                bat "gradlew.bat npm_run_test -PnodeInstall --no-daemon"
            } catch(err) {
                throw err
            } finally {
                junit '**/build/test-results/TESTS-*.xml'
            }
        }

        stage('packaging') {
            bat "gradlew.bat bootWar -x test -Pprod -PnodeInstall --no-daemon"
            archiveArtifacts artifacts: '**/build/libs/*.war', fingerprint: true
        }

    }
}
