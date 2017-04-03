node{
    currentBuild.result = "SUCCESS"
    try {
       stage 'Checkout'
       checkout scm

       stage 'Test'
            env.NODE_ENV = "test"
            print "Environment will be : ${env.NODE_ENV}"

        stage 'Build Docker'
            sh './dockerBuild.sh'

       stage 'Deploy'

            echo 'Push to Repo'
            sh './dockerPushToRepo.sh'

            echo 'ssh to web server and tell it to pull new image'
            sh 'ssh deploy@xxxxx.xxxxx.com running/xxxxxxx/dockerRun.sh'

       stage 'Cleanup'

            echo 'prune and cleanup'
            sh 'npm prune'
            sh 'rm node_modules -rf'
        }
    catch (err) {

        currentBuild.result = "FAILURE"
        throw err
    }

}
