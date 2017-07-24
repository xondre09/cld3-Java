OBJDIR=build
SRCDIR=src
LIBDIR=lib
CLASSDIR=$(OBJDIR)

JC=javac
JFLAGS=-Djava.library.path="${CURDIR}/${LIBDIR}"
JVM=java

CLASSES=LanguageIdentifier.class \
        Example.class

MAIN=Example

main: protos cld3 ${OBJDIR}/Makefile
	@make --no-print-directory -C ${OBJDIR}/ install

cld3: ${SRCDIR}/libcld3/

protos: ${SRCDIR}/cld_3/protos

${SRCDIR}/libcld3/:
	git submodule init
	git submodule update

${SRCDIR}/cld_3/protos:
	@mkdir -p ${SRCDIR}/cld_3/protos

${OBJDIR}/Makefile:
	@mkdir -p ${OBJDIR}
	@cd ${OBJDIR} && cmake ..

classes: $(addprefix $(CLASSDIR)/, $(CLASSES))

test:
	-@make -s -k -C ${OBJDIR}/ test 2> /dev/null || true

run: classes
	$(JVM) $(JFLAGS) -classpath $(CLASSDIR) $(MAIN)

clean:
	@make --no-print-directory -C ${OBJDIR}/ clean

.PHONY: run clean

# --

$(CLASSDIR)/%.class: $(SRCDIR)/%.java
	$(JC) -cp $(SRCDIR) -d $(CLASSDIR) $<
