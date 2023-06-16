#
#    Copyright 2016-2023 the original author or authors.
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /jpetstore
#COPY . .
COPY .mvn/ .mvn
COPY mvnw mvnw.cmd pom.xml owasp-dependency-check-suppression.xml license.txt manifest.yml ./
COPY ./src ./src
COPY ./images ./images
COPY ./target ./target
RUN ./mvnw clean package -DskipTests=true
CMD [ "java", "-jar", "target/mybatis-spring-boot-jpetstore-2.0.0-SNAPSHOT.jar"]
# RUN ./mvnw clean spring-boot:run
# CMD ["./mvnw", "clean", "spring-boot:run"]
EXPOSE 8080
