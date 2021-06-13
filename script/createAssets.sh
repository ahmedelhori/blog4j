#!/bin/sh

# Author:       Ahmed Elhori <dev@elhori.com>
# License:      GNU GPLv3
# Description:  Create asset.zip

# ENV
set -e
NAME="$(basename ${0})"
base_path="$(pwd)/$(dirname ${0})"
SOURCE_PATH="${base_path}/../src/main/resources/static"
DIST_PATH="${base_path}/../src/main/resources/asset"

check_dependency(){
	dep_missing=0
	for x in "$@"; do
		if ! which "$x" >/dev/null 2>&1; then
			echo "$NAME - Error: $x not installed" >&2
			dep_missing=1
		fi
	done

	if [ "$dep_missing" -ne '0' ]; then
		exit 1
	fi
}

# Prepare
prepare(){
	if [ ! -d "${SOURCE_PATH}" ]; then
		echo "${NAME} - Error: source path not found: \"${SOURCE_PATH}\"" >&2
		exit 1
	fi

	[ -d "${DIST_PATH}" ] || mkdir "${DIST_PATH}"

}

# Create Zip
run(){
	cd "$SOURCE_PATH"
	zip -r ../asset/assets.zip *
	cd -
}

main(){
	check_dependency 'zip'
	prepare "$@"
	run "$@"
}

main "$@"
