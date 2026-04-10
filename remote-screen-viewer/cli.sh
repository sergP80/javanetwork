
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

    java -cp ./target/remote-screen-viewer-1.0-SNAPSHOT.jar ua.edu.chmnu.ki.networks.rsv.RemoteScreenClientApp
}

function server() {
    local SERVER_PORT="$1"

    if [[ -n "$SERVER_PORT" ]];
    then
      export UDP_PORT=$SERVER_PORT
    fi

    local HEART_BEAT_FREQ="$2"

    if [[ -n "$HEART_BEAT_FREQ" ]];
    then
      export HEART_BEAT_INTERVAL=$HEART_BEAT_FREQ
    fi

    java -cp ./target/remote-screen-viewer-1.0-SNAPSHOT.jar ua.edu.chmnu.ki.networks.rsv.RemoteScreenServerApp
}

"$@"