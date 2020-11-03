
if [ $# -eq 0 ]
  then
    echo "No arguments supplied. You must supply the target profile. E.g. \"./deploy.sh dev\""
    exit -1
fi

case "$1" in
"dev")
    server=apps-dev.infofauna.ch
    ;;
"test")
    server=apps-test.infofauna.ch
    ;;
"prod")
    server=
    ;;
*)
    echo "The target profile must be one of 'dev', 'test' or 'prod'"
    exit -1
esac

echo Deploying to server : $server

rsync -a --delete distro/jeci-static-website/ app@$server:/var/www/html
