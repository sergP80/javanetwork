#!/bin/bash

function downloader() {
 java -cp ./target/network-programming-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.url.download.DownloaderDemo "$@"
}

function smtp-sender() {
 java -cp ./target/network-programming-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.mail.smtp.SmtpApplication
}

function imap-listener() {
 java -cp ./target/network-programming-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.mail.imap.ImapReceiverApp
}

"$@"
