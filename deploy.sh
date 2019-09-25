#!/bin/sh
echo "Commiting to Github"
read -p 'Enter a commit message: ' commit_message
git add .
git commit -m "$commit_message"
git push
echo "Packaging using Maven"
mvn -Dmaven.test.skip=true package
echo "Deploying to mywb.vcs.net"
scp ./target/wb-database-0.0.1-SNAPSHOT.jar supadmin@mywb.vcs.net:~/database.jar
echo "Deployment complete"
echo "Restarting server"
ssh supadmin@mywb.vcs.net 'sudo wbdatabase restart'