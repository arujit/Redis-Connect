#!/bin/bash

envsubst < ./connect-properties/connect-standalone.properties.template > ./connect-properties/connect-standalone.properties
envsubst < ./connect-properties/redis-connect.conf.template > ./connect-properties/redis-connect.conf
envsubst < ./newrelic/newrelic.yml.template > ./newrelic/newrelic.yml

CMD=${1:-"exit 0"}
if [[ "xxx$CMD" == "xxx" ]]
then
    echo "Starting Connect..."
    exec /opt/kafka_2.11-${KAFKA_VERSION}/bin/connect-standalone.sh ./connect-properties/connect-standalone.properties ./connect-properties/redis-connect.conf
else
    /bin/bash -c "$*"
fi
