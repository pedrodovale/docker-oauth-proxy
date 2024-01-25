# Use a base image with curl installed
FROM registry.access.redhat.com/ubi9 AS ubi-micro-build
RUN mkdir -p /mnt/rootfs
RUN dnf install --installroot /mnt/rootfs curl --releasever 9 --setopt install_weak_deps=false --nodocs -y && \
    dnf --installroot /mnt/rootfs clean all && \
    rpm --root /mnt/rootfs -e --nodeps setup

FROM quay.io/keycloak/keycloak:23.0.4
COPY --from=ubi-micro-build /mnt/rootfs /

# HEALTHCHECK command
HEALTHCHECK --interval=3s --timeout=5s --retries=20 \
  CMD curl --head -fsS http://localhost:8080/health/ready || exit 1

CMD ["start-dev", "--health-enabled=true", "--import-realm", "--log=console,file"]