ARG IMAGE
FROM $IMAGE
 
RUN addgroup -g 1000 tmpusrgp;
RUN adduser -u 1000 -G tmpusrgp -h /home/tmpusr -D tmpusr;
RUN mkdir /opt/build/
 
WORKDIR /opt/build/
 
COPY --chown=tmpusr:tmpusrgp ./build/PROJECT_NAME.txt /opt/build/
COPY --chown=tmpusr:tmpusrgp ./build/PROJECT_VERSION.txt /opt/build/
COPY --chown=tmpusr:tmpusrgp ./build/libs/*.jar /opt/build/
USER tmpusr
 
CMD ["/bin/sh", "/opt/build/init.sh"]
