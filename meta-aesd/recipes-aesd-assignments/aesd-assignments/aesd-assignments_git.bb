# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Fetch the Assignment 3/6 source repository
SRC_URI = "git://github.com/cu-ecen-aeld/assignment-3-hafsashaukat.git;protocol=https;nobranch=1"

PV = "1.0+git${SRCPV}"

# Assignment 6 Part 1 commit
SRCREV = "7be2879b1f698bd82a56f79a8e509cd77c36bb02"

# aesdsocket uses pthreads
TARGET_LDFLAGS += "-pthread"

# Automatically install and manage the aesdsocket startup script
inherit update-rc.d

INITSCRIPT_NAME = "aesdsocket-start-stop"
INITSCRIPT_PARAMS = "defaults 99"

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"

do_configure () {
    :
}

do_compile () {
    oe_runmake -C ${S}/server \
        CC="${CC}" \
        CFLAGS="${CFLAGS} ${LDFLAGS}"
}

do_install () {
    # Install aesdsocket executable
    install -d ${D}${bindir}
    install -m 0755 ${S}/server/aesdsocket ${D}${bindir}/aesdsocket

    # Install startup/shutdown script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/server/aesdsocket-start-stop \
        ${D}${sysconfdir}/init.d/aesdsocket-start-stop
}
