#include <iostream>
#include <random>
#include <vector>
#include <verilated.h>
#include <verilated_vcd_c.h>
#include "VAdder.h"

#define VTOP VAdder
uint64_t sim_time = 0;

typedef struct {
    uint8_t a;
    uint8_t b;
    uint8_t sum;
} TestCase;

std::random_device rd;
std::mt19937 gen(rd());
std::uniform_int_distribution<uint8_t> dis(0, 255);

std::vector<TestCase> test_cases;

uint8_t generate_random_uint8() { return dis(gen); }

void generate_test_cases(int num_cases) {
    for (int i = 0; i < num_cases; ++i) {
        TestCase tc;
        tc.a = generate_random_uint8();
        tc.b = generate_random_uint8();
        tc.sum = tc.a + tc.b;
        test_cases.push_back(tc);
    }
}

int main(int argc, char** argv, char** env) {
    Verilated::commandArgs(argc, argv);
    Verilated::traceEverOn(true);

    VTOP* top = new VTOP;
    auto* tfp = new VerilatedVcdC;
    top->trace(tfp, 99);
    tfp->open("waveform.vcd");

    generate_test_cases(10); // 生成10个测试用例

    bool failed = false;
    while (sim_time < test_cases.size()) {
        auto idx = sim_time;
        auto& tc = test_cases[idx];
        top->io_a = tc.a;
        top->io_b = tc.b;
        top->eval();
        tfp->dump(sim_time);
        if (top->io_sum != tc.sum) {
            std::cerr << "Test failed! Expected: " << (int)tc.a << " + " << (int)tc.b << " = " << (int)tc.sum
                      << ", but got: " << (int)top->io_sum << '\n';
            failed = true;
        }
        sim_time++;
    }

    if (!failed)
        std::cout << "All tests passed!\n";

    top->final();
    tfp->close();
    delete top;
    delete tfp;
    return failed;
}
