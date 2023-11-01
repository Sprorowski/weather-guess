# Base Image for GraalVM for AARCH64/ARM64 Architectures
# This is MUST for running the app on AWS Graviton Instances
FROM ghcr.io/graalvm/graalvm-ce:ol8-java17@sha256:25a7e2308e3425e59886fb6b7e1371389447773e2a0e82aa9e623cc9caf05321

# Unpacks the GraalVM Artifact into the root '/api-0.1.0/' into the Docker image
ADD build/distributions/weather.sprorowski.io-1.0.0.tar /
WORKDIR /weather.sprorowski.io-1.0.0

# Datadog Trace Agent for Java (required for instrumenting with GraalVM)
# Make sure to set the JAVA_OPTS accordingly to include said .jar
# e.g.: JAVA_OPTS =-javaagent:dd-java-agent.jar
ADD https://dtdg.co/latest-java-tracer dd-java-agent.jar

# Fires up the application
CMD ["bin/api"]
