export $(xargs < .env)

if [ x${REGISTRY_USERNAME} == x ]; then
  echo "Set the REGISTRY_USERNAME environment variable in the .env file";
  exit 1;
elif [ x${REGISTRY_PASSWORD} == x ]; then
  echo "Set the REGISTRY_PASSWORD environment variable in the .env file";
  exit 1;  
fi;

yum install -y httpd
mkdir -p auth
htpasswd -Bbn ${REGISTRY_USERNAME} ${REGISTRY_PASSWORD} > auth/htpasswd
