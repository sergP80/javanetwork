#!/bin/bash

function smtp-runner() {
 java -cp ./target/tutorial-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.mail.smtp.SmtpApplication
}

function imap-runner() {
 java -cp ./target/tutorial-1.0-SNAPSHOT-jar-with-dependencies.jar ua.edu.chmnu.ki.networks.mail.imap.ImapReceiverApp
}

"$@"
