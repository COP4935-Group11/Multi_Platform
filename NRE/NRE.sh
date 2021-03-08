#!/bin/bash

#cd NRE

fileLog=${PWD}/logs
INSTALL_DIR="/usr/local/bin"
PROJECT_FOLDER=${PWD}

sudo apt update 
sudo apt-get install -y curl unzip xvfb libxi6 libgconf-2-4
sudo apt-get install default-jdk jq firefox x11-utils -y



#DOWNLOADING AND INSTALLING CHROMEDRIVER AND GECKODRIVER
a=$(uname -m) &&
rm -r /${PWD}/chromedriver/
mkdir /${PWD}/chromedriver/ &&
rm -r /${PWD}/geckodriver/
mkdir /${PWD}/geckodriver/ &&
wget -O /${PWD}/chromedriver/LATEST_RELEASE https://chromedriver.storage.googleapis.com/LATEST_RELEASE &&
latestChrome=$(cat /${PWD}/chromedriver/LATEST_RELEASE) &&
json=$(curl -s https://api.github.com/repos/mozilla/geckodriver/releases/latest) &&
if [ $a == i686 ]; then b=32; elif [ $a == x86_64 ]; then b=64; fi &&
if [ $(uname) == "Darwin" ]; then
    urlChrome='https://chromedriver.storage.googleapis.com/'$latestChrome'/chromedriver_mac'$b'.zip' &&
    echo "$(echo "$json" | jq -r '.assets[].browser_download_url | select(contains("mac"))')" > /${PWD}/geckodriver/LATEST_RELEASE;
elif [ $(uname) == "Linux" ]; then
    urlChrome='https://chromedriver.storage.googleapis.com/'$latestChrome'/chromedriver_linux'$b'.zip' &&
    echo "$(echo "$json" | jq -r '.assets[].browser_download_url | select(contains("linux64"))')" > /${PWD}/geckodriver/LATEST_RELEASE &&
urlGecko=$(head -n 1 /${PWD}/geckodriver/LATEST_RELEASE);
elif [ $(uname) == "Windows" ]; then
    urlChrome='https://chromedriver.storage.googleapis.com/'$latestChrome'/chromedriver_win32.zip' &&
    echo "$(echo "$json" | jq -r '.assets[].browser_download_url | select(contains("win64"))')" > /${PWD}/geckodriver/LATEST_RELEASE;
else
    echo "can't determine OS"
    exit 1
fi &&
 wget -O /${PWD}/chromedriver/chromedriver.zip "$urlChrome" &&
sudo unzip -o /${PWD}/chromedriver/chromedriver.zip chromedriver -d "$INSTALL_DIR" &&

echo "$urlGecko"

cd /${PWD}/geckodriver/ &&
curl -s -L "$urlGecko" | tar -xz &&
chmod +x geckodriver
sudo mv -f geckodriver "$INSTALL_DIR"
cd .. 
sudo rm -R /${PWD}/chromedriver &&
sudo rm -R /${PWD}/geckodriver
##################################################################


#ADDING 'nre' TO USER'S PATH
dir=/usr/local/bin/;

sudo cat << EOF > ${dir}nre 
#!/bin/bash
cd ${PWD}
java -cp ./bin:./lib/*:./lib/Groovy/*:./lib/Cucumber/*:./lib/JUnit/* com.console.Main
EOF
sudo chmod +x ${dir}nre;
#export PATH=$PATH:/usr/bin/NRE
#####################################################################









