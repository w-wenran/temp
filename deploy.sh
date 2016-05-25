#!/bin/bash

echo "start deploy application..."

home_dir=$(pwd)

echo "home_dir:" $home_dir

mvn clean && mvn package && echo "package complated"

if [ -a "$home_dir"/oauthserver.war ];

	then 

		echo "upload war to deploy server... "
		read -p "enter upload server name:" upload_server
		if [ -z "$upload_server" ] || [ "$upload_server" == "localhost" ]
		then
			echo "empty input upload server"
			source ./start.sh
		else 
			echo "try upload war to server $upload_server..."
			scp ./oauthserver.war $upload_server:/opt/ezt/server.war
			echo "[OK] done..."
		fi

	else
		echo '[ERROR] server.war package not found'

fi
