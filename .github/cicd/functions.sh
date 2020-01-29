export BRANCH_LOWER=`echo "${BRANCH_NAME}" | awk '{print tolower($0)}'`

# Takes in variables required for the project and each container for build.
function build() {
    source ${config_path}/$2.conf
    echo "Building $2 (${APP_NAME}) to $PROJECT_PREFIX-$3..."
    buildPresent=$(oc get bc/"$APP_NAME-$BRANCH_LOWER" --ignore-not-found=true)
    if [ -z "${buildPresent}" ];
    then
        MODE="apply"
    else
        MODE="create"
    fi;
    if [ "${BRANCH_LOWER}" == "develop" ] || [ "${BRANCH_LOWER}" == "master" ];
    then
        echo "oc process -f ./${TEMPLATE_DIRECTORY}/${BUILD_CONFIG_TEMPLATE} -p NAME=${APP_NAME} -p VERSION=${BUILD_NUMBER} -p SOURCE_CONTEXT_DIR=${SOURCE_CONTEXT_DIR} -p SOURCE_REPOSITORY_URL=${GIT_URL} -p SOURCE_REPOSITORY_REF=${BRANCH_NAME} -p OC_PREFIX=$PROJECT_PREFIX -p OC_APP=$3 ${@:4} | oc ${MODE} -f - --namespace=$PROJECT_PREFIX-$3"
        oc process -f ./"${TEMPLATE_DIRECTORY}/${BUILD_CONFIG_TEMPLATE}" \
        -p NAME="${APP_NAME}" \
        -p VERSION="${BUILD_NUMBER}" \
        -p SOURCE_CONTEXT_DIR="${SOURCE_CONTEXT_DIR}" \
        -p SOURCE_REPOSITORY_URL="${GIT_URL}" \
        -p SOURCE_REPOSITORY_REF="${BRANCH_NAME}" \
        -p OC_PREFIX="$PROJECT_PREFIX" \
        -p OC_APP="$3" ${@:4} --output="yaml" | oc "${MODE}" -f - --namespace="$PROJECT_PREFIX-$3" --overwrite=true --all
    else
        echo "oc process -f ./${TEMPLATE_DIRECTORY}/${BUILD_CONFIG_TEMPLATE} -p NAME=${APP_NAME} -p VERSION=${BUILD_NUMBER} -p SOURCE_CONTEXT_DIR=${SOURCE_CONTEXT_DIR} -p SOURCE_REPOSITORY_URL=${GIT_URL} -p SOURCE_REPOSITORY_REF=${BRANCH_NAME} -p OC_PREFIX=$PROJECT_PREFIX -p OC_APP=$3 ${@:4} | oc ${MODE} -f - --namespace=$PROJECT_PREFIX-$3"
        oc process -f ./"${TEMPLATE_DIRECTORY}/${BUILD_CONFIG_TEMPLATE}" \
        -p NAME="${APP_NAME}" \
        -p VERSION="${BUILD_NUMBER}" \
        -p SOURCE_CONTEXT_DIR="${SOURCE_CONTEXT_DIR}" \
        -p SOURCE_REPOSITORY_URL="${GIT_URL}" \
        -p SOURCE_REPOSITORY_REF="${CHANGE_BRANCH}" \
        -p OC_PREFIX="$PROJECT_PREFIX" \
        -p OC_APP="$3" ${@:4} --output="yaml" | oc "${MODE}" -f - --namespace="$PROJECT_PREFIX-$3" --overwrite=true --all
    fi;
    if [ "$BUILD_REQUIRED" == true ];
    then
        echo "Building oc start-build $APP_NAME -n $PROJECT_PREFIX-$3 --wait --follow ..."
        oc start-build "$APP_NAME" -n "$PROJECT_PREFIX-$3" --wait --follow
    else
        echo "Deployment should be automatic..."
    fi
}

# Deploy for same project and app variables.
function deploy() {
    source ${config_path}/$2.conf
    echo "Deploying $2 (${APP_NAME}) to $3 ..."
    deployPresent=$(oc get dc/"${APP_NAME}-${BRANCH_LOWER}" --ignore-not-found=true)
    if [ -z "${deployPresent}" ];
    then
        MODE="apply"
    else
        MODE="create"
    fi;
    #echo "Interpreted:"
    if [ "${BRANCH_LOWER}" == "develop" ] || [ "${BRANCH_LOWER}" == "master" ];
    then
        oc process -f ./"${TEMPLATE_DIRECTORY}/${DEPLOY_CONFIG_TEMPLATE}" \
        -p NAME="${APP_NAME}" \
        -p VERSION="${BUILD_NUMBER}" \
        -p SOURCE_CONTEXT_DIR="${SOURCE_CONTEXT_DIR}" \
        -p SOURCE_REPOSITORY_URL="${GIT_URL}" \
        -p SOURCE_REPOSITORY_REF="${BRANCH_NAME}" \
        -p OC_PREFIX="$PROJECT_PREFIX" \
        -p OC_APP="$3" ${@:4} --output="yaml" | oc "${MODE}" -f - --namespace="$PROJECT_PREFIX-$3" --output="yaml" --overwrite=true --all
    else
        oc process -f ./"${TEMPLATE_DIRECTORY}/${DEPLOY_CONFIG_TEMPLATE}" \
        -p NAME="${APP_NAME}" \
        -p VERSION="${BUILD_NUMBER}" \
        -p SOURCE_CONTEXT_DIR="${SOURCE_CONTEXT_DIR}" \
        -p SOURCE_REPOSITORY_URL="${GIT_URL}" \
        -p SOURCE_REPOSITORY_REF="${CHANGE_BRANCH}" \
        -p OC_PREFIX="$PROJECT_PREFIX" \
        -p OC_APP="$3" ${@:4} --output="yaml" | oc "${MODE}" -f - --namespace="$PROJECT_PREFIX-$3" --output="yaml" --overwrite=true --all
    fi;
}

# For tools, instrumentation and other stuff in the tools space.
function toolbelt() {
    source ${config_path}/$2.conf
    #OC_APP=tools
    buildPresent=$(oc get bc/"$APP_NAME" --ignore-not-found=true)
    if [ -z "${buildPresent}" ];
    then
        MODE="apply"
    else
        MODE="create"
    fi;
    oc process -f ./"${TEMPLATE_DIRECTORY}/$BUILD_CONFIG_TEMPLATE" \
        -p SOURCE_REPOSITORY_URL="${GIT_URL}" \
        -p SOURCE_REPOSITORY_REF="${CHANGE_BRANCH}" \
        -p OC_PREFIX="$PROJECT_PREFIX" \
        -p OC_APP="$3" ${PIPELINE_ARGS} ${@:4} --output="yaml" | oc $MODE -f - --namespace="$PROJECT_PREFIX-$3"
    oc process -f "${TEMPLATE_DIRECTORY}/$DEPLOY_CONFIG_TEMPLATE" \
        -p SOURCE_REPOSITORY_URL="${GIT_URL}" \
        -p SOURCE_REPOSITORY_REF="${CHANGE_BRANCH}" \
        -p OC_PREFIX="$PROJECT_PREFIX" \
        -p OC_APP="$3" ${PIPELINE_ARGS} ${@:4} --output="yaml" | oc $MODE -f - --namespace="$PROJECT_PREFIX-$3"
    if [ "$BUILD_REQUIRED" == true ];
    then
        echo "Building oc start-build $APP_NAME -n $PROJECT_PREFIX-${OC_APP} --wait --follow ..."
        oc start-build $APP_NAME -n $PROJECT_PREFIX-$3 --wait --follow
    else
        echo "Deployment should be automatic..."
    fi
}

# OpenShift sometimes doesn't handle it well if the template already exists.  This mitigates some of that

function determineMode() {
    buildPresent=$(oc get "$3"/"$2-${BRANCH_LOWER}" --ignore-not-found=true)
    if [ -z "${buildPresent}" ];
    then MODE="apply"
    else MODE="create"
    fi;
}

# For cleanup of branched PRs.
function occleanup() {
    OPEN_PR_ARRAY=()
    LIVE_BRANCH_ARRAY=()
    ORPHANS=()
    curl -o openPRs.txt "https://api.github.com/repos/${PROJECT_OWNER}/${PROJECT_NAME}/pulls?status=open&sort=number"
    declare -p OPEN_PR_ARRAY=( $(grep '"number"' openPRs.txt | column -t | sed 's|[:,]||g' | awk '{print $3}') )
    declare -p LIVE_BRANCH_ARRAY=( $(oc get route -n $PROJECT_PREFIX-dev | awk '{print $2}' | grep -P "(\-pr\-\d+)" | sed 's/[^0-9]*//g' | sort -un) )
    ORPHANS=$(echo ${OPEN_PR_ARRAY[@]} ${LIVE_BRANCH_ARRAY[@]} | tr ' ' '\n' | sort | uniq -u)
    for i in $ORPHANS
    do
        cleanOcArtifacts $i
    done
}

# For cleanup as well.
function cleanOcArtifacts() {
    declare -p ALL_BRANCH_ARTIFACTS=( $(oc get all,pvc,secrets,route -n $PROJECT_PREFIX-dev | grep -i "\-$2" | awk '{print $2}' | grep -P "(\-pr\-\d+)") )
    for a in "${ALL_BRANCH_ARTIFACTS[@]}"
    do
        oc delete -n $PROJECT_PREFIX-dev $a
    done
}

# To clean an entire app (not based on PR) like databases.  For use before migration strategy is implemented.
function nukenpave() {
    source ${config_path}/$2.conf
    declare -p TARGET_ARTIFACTS=($(oc get all,pvc,route -n $PROJECT_PREFIX-$3 | grep -i "$APP_NAME" | awk '{print $2}' | grep -Ev "(\-pr\-)") )
    for target in "${TARGET_ARTIFACTS[@]}"
    do
        oc delete -n $PROJECT_PREFIX-$3 $target
    done
        build $@
        deploy $@
}
# For testing function arguments
function functionTest() {
    echo "1=$2"
    echo "2=$3"
    echo "Trailing = ${@:4}"
    echo "All = $@"
}
