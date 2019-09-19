FROM tomcat:latest

RUN bash -c 'rm -rf /usr/local/tomcat/webapps/* \
            && pwd'

ADD /target/compass.war  /usr/local/tomcat/webapps/ROOT.war

ENV CATALINA_OPTS '-Xdebug  -Xrunjdwp:transport=dt_socket,address=5001,server=y,suspend=n'

EXPOSE 8080
EXPOSE 5001

