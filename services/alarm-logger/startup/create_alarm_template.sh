#!/bin/sh

es_host=localhost
es_port=9200

# Create the elastic template with the correct mapping for alarm state messages.
curl -XPUT http://${es_host}:${es_port}/_template/alarms_state_template -H 'Content-Type: application/json' -d'
{
  "index_patterns":["*_alarms_state*"],
  "mappings" : {  
    "alarm" : {
        "properties" : {
          "APPLICATION-ID" : {
            "type" : "text"
          },
          "config" : {
            "type" : "keyword"
          },
          "pv" : {
            "type" : "keyword"
          },
          "severity" : {
            "type" : "keyword"
          },
          "latch" : {
            "type" : "boolean"
          },
          "message" : {
            "type" : "text"
          },
          "value" : {
            "type" : "text"
          },
          "time" : {
            "type" : "date",
            "format" : "yyyy-MM-dd HH:mm:ss.SSS"
          },
          "message_time" : {
            "type" : "date",
            "format" : "yyyy-MM-dd HH:mm:ss.SSS"
          },
          "current_severity" : {
            "type" : "keyword"
          },
          "current_message" : {
            "type" : "text"
          },
          "mode" : {
            "type" : "keyword"
          }
        }
      }
  }
}
'

# Create the elastic template with the correct mapping for alarm command messages.
curl -XPUT http://${es_host}:${es_port}/_template/alarms_cmd_template -H 'Content-Type: application/json' -d'
{
  "index_patterns":["*_alarms_cmd*"],
  "mappings" : {  
    "alarm_cmd" : {
        "properties" : {
          "APPLICATION-ID" : {
            "type" : "text"
          },
          "config" : {
            "type" : "keyword"
          },
          "user" : {
            "type" : "keyword"
          },
          "host" : {
            "type" : "keyword"
          },
          "command" : {
            "type" : "keyword"
          },
          "message_time" : {
            "type" : "date",
            "format" : "yyyy-MM-dd HH:mm:ss.SSS"
          }
        }
      }
  }
}
'

echo "Alarm templates:"
curl -X GET "${es_host}:${es_port}/_template/*alarm*"

