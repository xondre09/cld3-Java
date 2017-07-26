PROJECT(cld3)
include(FindProtobuf)

find_package(Protobuf REQUIRED)
find_package(JNI)

set (PARENT_SCOPE ${PROJECT_SOURCE_DIR})

cmake_minimum_required(VERSION 3.7)

set(CMAKE_CXX_FLAGS "-std=gnu++11 -fPIC")
set(CMAKE_INSTALL_PREFIX ${PROJECT_SOURCE_DIR})

set(LIBCLD3 src/libcld3/src)

set(TMP ${CMAKE_CURRENT_BINARY_DIR})
file(MAKE_DIRECTORY ${PROJECT_SOURCE_DIR}/src/cld_3/protos)
set(CMAKE_CURRENT_BINARY_DIR ${PROJECT_SOURCE_DIR}/src/cld_3/protos)

protobuf_generate_cpp(PROTO_SRCS PROTO_HDRS
    ${LIBCLD3}/feature_extractor.proto
    ${LIBCLD3}/sentence.proto
    ${LIBCLD3}/task_spec.proto
)

set(CMAKE_CURRENT_BINARY_DIR ${TMP})

INCLUDE_DIRECTORIES (
    src
    ${LIBCLD3}
    ${JNI_INCLUDE_DIRS}
)

ADD_LIBRARY(
    cld3 STATIC
    ${LIBCLD3}/base.cc
    ${LIBCLD3}/base.h
    ${LIBCLD3}/casts.h
    ${LIBCLD3}/embedding_feature_extractor.cc
    ${LIBCLD3}/embedding_feature_extractor.h
    ${LIBCLD3}/embedding_network.cc
    ${LIBCLD3}/embedding_network.h
    ${LIBCLD3}/embedding_network_params.h
    ${LIBCLD3}/feature_extractor.cc
    ${LIBCLD3}/feature_extractor.h
    ${LIBCLD3}/feature_types.cc
    ${LIBCLD3}/feature_types.h
    ${LIBCLD3}/float16.h
    ${LIBCLD3}/fml_parser.cc
    ${LIBCLD3}/fml_parser.h
    ${LIBCLD3}/language_identifier_features.cc
    ${LIBCLD3}/language_identifier_features.h
    ${LIBCLD3}/lang_id_nn_params.cc
    ${LIBCLD3}/lang_id_nn_params.h
    ${LIBCLD3}/nnet_language_identifier.cc
    ${LIBCLD3}/nnet_language_identifier.h
    ${LIBCLD3}/registry.cc
    ${LIBCLD3}/registry.h
    ${LIBCLD3}/relevant_script_feature.cc
    ${LIBCLD3}/relevant_script_feature.h
    ${LIBCLD3}/script_detector.h
    ${LIBCLD3}/sentence_features.cc
    ${LIBCLD3}/sentence_features.h
    ${LIBCLD3}/simple_adder.h
    ${LIBCLD3}/script_span/fixunicodevalue.cc
    ${LIBCLD3}/script_span/fixunicodevalue.h
    ${LIBCLD3}/script_span/generated_entities.cc
    ${LIBCLD3}/script_span/generated_ulscript.cc
    ${LIBCLD3}/script_span/generated_ulscript.h
    ${LIBCLD3}/script_span/getonescriptspan.cc
    ${LIBCLD3}/script_span/getonescriptspan.h
    ${LIBCLD3}/script_span/integral_types.h
    ${LIBCLD3}/script_span/offsetmap.cc
    ${LIBCLD3}/script_span/offsetmap.h
    ${LIBCLD3}/script_span/port.h
    ${LIBCLD3}/script_span/stringpiece.h
    ${LIBCLD3}/script_span/text_processing.cc
    ${LIBCLD3}/script_span/text_processing.h
    ${LIBCLD3}/script_span/utf8acceptinterchange.h
    ${LIBCLD3}/script_span/utf8prop_lettermarkscriptnum.h
    ${LIBCLD3}/script_span/utf8repl_lettermarklower.h
    ${LIBCLD3}/script_span/utf8scannot_lettermarkspecial.h
    ${LIBCLD3}/script_span/utf8statetable.cc
    ${LIBCLD3}/script_span/utf8statetable.h
    ${LIBCLD3}/task_context.cc
    ${LIBCLD3}/task_context.h
    ${LIBCLD3}/task_context_params.cc
    ${LIBCLD3}/task_context_params.h
    ${LIBCLD3}/unicodetext.cc
    ${LIBCLD3}/unicodetext.h
    ${LIBCLD3}/utils.cc
    ${LIBCLD3}/utils.h
    ${LIBCLD3}/workspace.cc
    ${LIBCLD3}/workspace.h
    ${PROTO_SRCS}
    ${PROTO_HDRS}
)

TARGET_LINK_LIBRARIES (
    cld3
    ${PROTOBUF_LIBRARIES}
)

ADD_LIBRARY(
    forcld3 SHARED
    src/NNetLanguageIdentifierWrapper.cc
    )

TARGET_LINK_LIBRARIES (
    forcld3
    cld3
)

# ---

# ADD_EXECUTABLE(
#     language_identifier_main
#     ${LIBCLD3}/language_identifier_main.cc
# )

# TARGET_LINK_LIBRARIES (
#     language_identifier_main
#     cld3
# )

ENABLE_TESTING()

# ADD_EXECUTABLE (
#     getonescriptspan_test
#     ${LIBCLD3}/script_span/getonescriptspan_test.cc
# )
# ADD_TEST(getonescriptspan_test  getonescriptspan_test)
# TARGET_LINK_LIBRARIES (getonescriptspan_test cld3)

#ADD_EXECUTABLE (
#    language_identifier_features_test
#    ${LIBCLD3}/language_identifier_features_test.cc
#)
#ADD_TEST(language_identifier_features_test  language_identifier_features_test)
#TARGET_LINK_LIBRARIES (language_identifier_features_test cld3)

ADD_EXECUTABLE (
    nnet_lang_id_test
    ${LIBCLD3}/nnet_lang_id_test.cc
    ${LIBCLD3}/nnet_lang_id_test_data.cc
)
ADD_TEST(nnet_lang_id_test nnet_lang_id_test)
TARGET_LINK_LIBRARIES (nnet_lang_id_test cld3)
# ----

INSTALL (
    TARGETS forcld3
    LIBRARY DESTINATION lib
)
