
function client() {
    local REMOTE_URL="$1"

    if [[ -n "$REMOTE_URL" ]];
    then
      export REMOTE_URL=$REMOTE_URL
    fi

    java -cp ./target/remote-mouse-control-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.mouse.client.RemoteMouseControlClientApp
}

function server() {
    local CONTROL_PORT="$1"

    if [[ -n "$CONTROL_PORT" ]];
    then
      export CONTROL_PORT=$CONTROL_PORT
    fi

    java -cp ./target/remote-mouse-control-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.mouse.server.MouseControlServerApp

}

"$@"