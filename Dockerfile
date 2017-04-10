FROM jboss/wildfly

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss
# Expose the ports we're interested in
EXPOSE 8080

# Add your application to the deployment folder
ADD target/ECommToyApp-1.war /opt/jboss/wildfly/standalone/deployments/


# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]


