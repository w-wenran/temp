#!/bin/bash

echo "start deploy application..."

home_dir=$(pwd)

echo "home_dir:" $home_dir

START_LOGO="Tomcat started"

if [ -a "$home_dir"/oauthserver.war ]

	then 
		( java -jar ./oauthserver.war --server.port=8989 >oauthserver.log 2>&1 &)
		log_line=1
		b_go=true
		echo "watch server start..."
		while ($b_go)
		do 
			new_line=$( wc -l oauthserver.log | awk '{ print $1 }')
			if [ $log_line -ne $new_line ] && [ $new_line -ne "0" ]
				then
					log_info=$(sed -n "${log_line},${new_line}p" oauthserver.log)
					log_line=$new_line
					[[ $log_info =~ "$START_LOGO" ]] && echo $START_LOGO && b_go=false
					[[ $log_info =~ "Exception" ]] && echo -e "\n[ERROR]start failed!! more details read log file" && exit -1
					echo -n "..."

			fi
		done
		echo "[OK] done..."
		echo "[DONE] server port 8989"
		

	else
		echo '[ERROR] server.war package not found'

fi
