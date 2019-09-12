# sikuli_e2e

-- How to build jar
mvn clean compile assembly:single

-- hoq to run
java -cp evaluations-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.hexacta.sikuli.linkedin.LinkedInApp -DfirstLevel=false -DsecondLevel=true -Dlocations="Gran Buenos Aires, Argentina"