package adder

import chisel3._
import circt.stage.ChiselStage._
import chisel3.util._


class Adder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))
    val sum = Output(UInt(8.W))
  })

  io.sum := io.a + io.b
}

object Main extends App {
  emitSystemVerilogFile(new Adder, args = Array("--target-dir", "generated"))
}