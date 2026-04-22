
function client() {
    local REMOTE_HOST="$1"

    if [[ -n "$REMOTE_HOST" ]];
    then
      export SERVER_HOST=$REMOTE_HOST
    fi

    local REMOTE_PORT="$2"

    if [[ -n "$REMOTE_PORT" ]];
    then
      export SERVER_PORT=$REMOTE_PORT
    fi

    local HEART_BEAT_TIMEOUT="$2"

    if [[ -n "$HEART_BEAT_TIMEOUT" ]];
    then
      export HEART_BEAT_TIMEOUT=$HEART_BEAT_TIMEOUT
    fi

    java -cp ./target/remote-screen-viewer-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.rsv.RemoteScreenClientApp
}

function server() {
    local SERVER_PORT="$1"

    if [[ -n "$SERVER_PORT" ]];
    then
      export UDP_PORT=$SERVER_PORT
    fi

    local REGISTRY_TYPE="$2"

    if [[ -n "$REGISTRY_TYPE" ]];
    then
      export REGISTRY_TYPE=$REGISTRY_TYPE
    fi

    if [[ -n "$REGISTRY_TYPE" &&  "$REGISTRY_TYPE" != "IN_MEMORY" ]];
    then
      case $REGISTRY_TYPE in
      "JSON_FILE")
        read -p "Enter json file name:" FILE_NAME
        if [[ "${FILE_NAME##*.}" == "$FILE_NAME" ]]; then
            FILE_NAME="${FILE_NAME}.json"
        fi
        ;;
      "XML_FILE")
        read -p "Enter xml file name:" FILE_NAME
        if [[ "${FILE_NAME##*.}" == "$FILE_NAME" ]]; then
            FILE_NAME="${FILE_NAME}.xml"
        fi
        ;;
      esac

      if [[ -n "$FILE_NAME" ]];
      then
        export REGISTRY_NAME=$FILE_NAME
      fi
    fi

    local HEART_BEAT_FREQ="$3"

    if [[ -n "$HEART_BEAT_FREQ" ]];
    then
      export HEART_BEAT_INTERVAL=$HEART_BEAT_FREQ
    fi

    java -cp ./target/remote-screen-viewer-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.rsv.RemoteScreenServerApp
}

"$@"