#!/bin/sh
echo "Commiting to Github"
read -p 'Enter a commit message: ' commit_message
git add .
git commit -m "$commit_message"
git push
echo "Packaging using Maven"
mvn -Dmaven.test.skip=true package
echo "Deploying to BK1031 Server"
scp ./target/wb-database-0.0.1-SNAPSHOT.jar BharatK@24.4.73.109:~/database.jar
echo "Deployment complete"
echo "Restarting server"
ssh BharatK@24.4.73.109 'source .wbdatabase.sh; wbdatabase start'