
function connect() {
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

    java -cp ./target/remote-screen-viewer-1.0-SNAPSHOT.jar ua.edu.chmnu.ki.networks.rsv.RemoteScreenClientApp
}

"$@"