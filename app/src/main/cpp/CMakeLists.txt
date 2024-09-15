cmake_minimum_required(VERSION 3.22.1)
project(MyApplicatỉon)

set(AES_KEY "AES_KEY")
add_definitions(-DAES_KEY=${AES_KEY})

set(AES_IV "AES_IV")
add_definitions(-DAES_IV=${AES_IV})

configure_file(constants.h.in constants.h.in)

add_library(native-lib SHARED native-lib.cpp)

find_library(log-lib log)

target_link_libraries(native-lib ${log-lib})