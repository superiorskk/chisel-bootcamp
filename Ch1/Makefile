TB_DIR := sim
TB_EXE := tb
WAVE_FILE := waveform.vcd

.PHONY: all compile sim run show clean

all: run

compile:
	@echo "//---------- Compiling Chisel To Verilog ----------//"
	@sbt run
	@if [ $$? -eq 0 ]; then \
		echo "//---------- Compilation Success ----------//"; \
	else \
		echo "//---------- Compilation Fail ----------//"; \
	fi

sim:
	@echo
	@echo "//---------- Compiling Verilog To C++ ----------//"
	@if [ -d $(TB_DIR)/build ]; then \
  		echo "Build directory exists, recompiling ..."; \
  		cd $(TB_DIR)/build && \
  		cmake --build . -j; \
	else \
		echo "Creating build directory and configuring CMake..."; \
		mkdir -p $(TB_DIR)/build; \
		cd $(TB_DIR)/build && \
		cmake .. && \
		cmake --build . -j; \
	fi
	@if [ $$? -eq 0 ]; then \
		echo "//---------- C++ Compilation Success ----------//"; \
	else \
		echo "//---------- C++ Compilation Fail ----------//"; \
	fi
	@echo
	@echo "//---------- Starting Simulation ----------//"
	@cd ./$(TB_DIR) && ./build/$(TB_EXE)
	@if [ $$? -eq 0 ]; then \
		echo "//---------- Simulation Success ----------//"; \
	else \
		echo "//---------- Simulation Fail ----------//"; \
	fi

run:
	@$(MAKE) compile
	@$(MAKE) sim

show:
	@echo
	@gtkwave $(TB_DIR)/$(WAVE_FILE)

clean:
	@echo "Cleaning build directory..."
	@rm -rf $(TB_DIR)/build
	@rm -rf $(TB_DIR)/$(WAVE_FILE)