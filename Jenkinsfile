pipeline {
    agent none
    /*
    environment {
        BRANCH_LOWER=BRANCH_NAME.toLowerCase()
        VANITY_URL="${BRANCH_LOWER}.pharmanetenrolment-dqszvc-dev.pathfinder.gov.bc.ca"
        FRONTEND_ARGS="-p HTTP_PORT=8080 -p HTTP_SCHEMA=http TERMINATION_TYPE='Edge' -p REDIRECT_URL=http://${VANITY_URL} -p VANITY_URL=${VANITY_URL}"
        API_ARGS="-p ASPNETCORE_ENVIRONMENT=Development -p HTTP_PORT=8080 -p HTTP_SCHEMA=http -p VANITY_URL=${VANITY_URL} -p REDIRECT_URL=http://${VANITY_URL}"
    }*/
    options {
        disableResume()
    }
    stages {
        stage('Build Branch') {
            options {
                timeout(time: 90, unit: 'MINUTES')   // timeout on this stage
            }
            when { expression { ( GIT_BRANCH != 'master' ) } }
            agent { label 'master' }
            steps {
                echo "Building ..."
                sh ".github/cicd/player.sh build fig-validation-service dev"
            }
        }
        stage('Deploy Branch') {
            options {
                timeout(time: 10, unit: 'MINUTES')   // timeout on this stage
            }
            when { expression { ( GIT_BRANCH != 'master' ) } }
            agent { label 'master' }
            steps {
                echo "Deploy to dev..."
                sh ".github/cicd/player.sh deploy fig-validation-service dev"
            }
        }
        stage('SCM') {
            steps{
                git url: 'https://github.com/bcgov/jag-dps'
            }
        }
        stage('SonarQube Analysis and Reporting') {
            steps{
                def scannerHome = tool 'SonarScanner 4.0';
                withSonarQubeEnv('DPS Sonar Scan') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        stage('Quality Gate') {
            steps{
                timeout(time: 1, unit: 'HOURS') {
                    def qualityGate = waitForQualityGate()
                    if (qualityGate != 'OK') {
                        error "Pipeline aborted due to quality gate failure. YOU SHALL NOT PASS: ${qualityGate.status}"
                    }
                }
            }
        }
    }
}
