#!/bin/bash
export cicd_path=".github/cicd"
export config_path="configurations"
source ${config_path}/project.conf
source ${cicd_path}/functions.sh
#source tests.bash

case "$1" in
    build)
        build $@
        echo "Extra params= ${@:4}"
        #> /dev/null 2>&1
        ;;
    deploy)
        deploy $@
        echo "Extra params= ${@:4}"
        #> /dev/null 2>&1
        ;;
    toolbelt)
        toolbelt $@
        echo "Extra params= ${@:4}"
        ;;
    scan)
        scan
        ;;
    zap)
        zap $@
        ;;
    occleanup)
        occleanup
        ;;
    nukenpave)
        nukenpave $@
        ;;
    pipeline_args)
        pipeline_args $@
        ;;
    functionTest)
        functionTest $@
        ;;
    *)
    echo "You\'re doing it wrong..."
esac
