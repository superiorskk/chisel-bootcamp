cmake_minimum_required(VERSION 3.20)
project(Testbench)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON) # 生成compile_commands.json给clangd索引

cmake_policy(SET CMP0144 NEW)
find_package(verilator HINTS $ENV{VERILATOR_ROOT} REQUIRED)

set(VERILOG_SRC ${CMAKE_CURRENT_SOURCE_DIR}/../generated/Adder.sv)
set(CPP_SRC ${CMAKE_CURRENT_SOURCE_DIR}/main.cpp)


add_executable(tb ${CPP_SRC})

verilate(tb
        TRACE
        TOP_MODULE Adder
        SOURCES ${VERILOG_SRC}
        VERILATOR_ARGS -O2
)

add_custom_target(
        sim
        COMMAND ${CMAKE_CURRENT_BINARY_DIR}/tb
        DEPENDS tb
        COMMENT "Running simulation..."
)

add_custom_target(
        show
        WORKING_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}
        DEPENDS sim
        COMMAND gtkwave waveform.vcd
        COMMENT "Running simulation and opening gtkwave..."
)