function wbdatabase() {
  case $1 in
  start)
    if test -f "wbdatabase_pid.txt"
    then
      echo "WB Database is already running"
    else
      if [ "$2" = "-noconfig" ] | [ "$2" = "-nc" ]
      then
        echo "Skipping configuration"
        echo "Starting WB Database"
        nohup java -jar database.jar > wbdatabase.log 2>&1 &
        echo $! > wbdatabase_pid.txt
        echo "WB Database is now online!"
      elif [ -z "$2" ]
      then
        echo "Pulling latest config"
        cd wb-database/
        git pull
        cd ..
        echo "Starting WB Database"
        nohup java -jar database.jar > wbdatabase.log 2>&1 &
        echo $! > wbdatabase_pid.txt
        echo "WB Database is now online!"
      fi
    fi
  ;;
  stop)
    if test -f "wbdatabase_pid.txt"
    then
      echo "Stopping WB Database"
      kill -9 `cat wbdatabase_pid.txt`
      rm wbdatabase_pid.txt
    else
      echo "WB Database is not already running"
    fi
  ;;
  restart)
    if test -f "wbdatabase_pid.txt"
    then
      echo "Stopping WB Database"
      kill -9 `cat wbdatabase_pid.txt`
      rm wbdatabase_pid.txt
      if [ "$2" = "-noconfig" ] | [ "$2" = "-nc" ]
      then
        echo "Skipping configuration"
        echo "Starting WB Database"
        nohup java -jar database.jar > wbdatabase.log 2>&1 &
        echo $! > wbdatabase_pid.txt
        echo "WB Database is now online!"
      elif [ -z "$2" ]
      then
        echo "Pulling latest config"
        cd wb-database/
        git pull
        cd ..
        echo "Starting WB Database"
        nohup java -jar database.jar > wbdatabase.log 2>&1 &
        echo $! > wbdatabase_pid.txt
        echo "WB Database is now online!"
      fi
    else
      echo "WB Database is not already running"
    fi
  ;;
  status)
    if test -f "wbdatabase_pid.txt"
    then
      echo "WB Database is online!"
    else
      echo "WB Database is offline!"
    fi
  ;;
esac
}