#!/bin/bash

if [ $(uname) == "Darwin" ]; then
dir=/usr/local/bin/;
#sudo cp ${PWD}/NRE.sh ${dir}nre
#sudo chmod a+x ${dir}nre;
else
dir=/usr/bin/;
#sudo cp ${PWD}/NRE ${dir}nre &&
#sudo chmod +x ${dir}nre;
fi

sudo cat << EOF > ${dir}nre 
#!/bin/bash
cd ${PWD}
java -cp ./bin:./lib/*:./lib/Groovy/*:./lib/Cucumber/*:./lib/JUnit/* com.console.Main
EOF
sudo chmod +x ${dir}nre;
#export PATH=$PATH:/usr/bin/NRE
