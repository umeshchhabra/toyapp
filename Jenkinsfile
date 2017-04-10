node {
  checkout scm
  env.PATH = "${tool 'm3'}/bin:${env.PATH}"
  
  stage('Build Application') 
  {
    sh 'mvn clean package -DskipTests'
  }
  
  stage('Prepare Database') 
  {
    // Start database container here
    sh 'docker login -u $USER -p $DOCPASS'
    //docker.pull("umeshchhabra/workingdbtoyapp:$DB")
    
    //start db container
    sh 'docker run --name toyappdb -d -p 9160:9160 -p 9042:9042 -p 7199:7199 -p 7001:7001 -p 7000:7000 umeshchhabra/workingdbtoyapp:$DB'
    
    //get ip address of Database container
    sh "DB=`docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' toyappdb`"
  }
  
  stage('Prepare Wildfly Image') 
  {
    sh 'docker login -u $USER -p $DOCPASS'
    docker.build("umeshchhabra/wildflycluster:${env.BUILD_NUMBER}")
        
    //start wildfly cluster
    sh 'docker run -d --name toyAppA -h toyAppA -p 8080  -p 7770:9990 --link toyappdb' "umeshchhabra/wildflycluster:${env.BUILD_NUMBER}"
    sh 'docker run -d --name toyAppB -h toyAppB -p 8080  -p 7770:9990 --link toyappdb' "umeshchhabra/wildflycluster:${env.BUILD_NUMBER}"
    sh 'docker run -d --name toyAppC -h toyAppC -p 8080  -p 7770:9990 --link toyappdb' "umeshchhabra/wildflycluster:${env.BUILD_NUMBER}"
  }
    
  stage('Prepare Nginx Image') 
  {
    sh 'docker login -u $USER -p $DOCPASS'
    docker.build("umeshchhabra/workinglbtoyapp:$LB")
    
    //start Nginx LB
    sh 'docker run -d --name nginx -p 80:80 --link toyAppA:toyAppA --link toyAppB:toyAppB --link toyAppC:toyAppC umeshchhabra/workinglbtoyapp:$LB'
  }

  stage('Run Unit Tests') 
  {
    try {
            sh "mvn test"
            docker.build("umeshchhabra/wildflycluster:${env.BUILD_NUMBER}").push()
        } 
    catch (error) 
    {

    } 
    finally 
    {
      junit '**/target/surefire-reports/*.xml'
    }
  }
}
