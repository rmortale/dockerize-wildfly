#FROM registry.redhat.io/jboss-eap-7/eap74-openjdk11-openshift-rhel8:7.4.7
FROM quay.io/wildfly/wildfly:26.1.2.Final-jdk11

ADD ojdbc11.jar /tmp/
ADD config.cli /tmp/

USER jboss
RUN $JBOSS_HOME/bin/jboss-cli.sh --file="/tmp/config.cli"
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/current

ADD demo/target/demo.war $JBOSS_HOME/standalone/deployments/
#ADD helloworld.war $JBOSS_HOME/standalone/deployments/
