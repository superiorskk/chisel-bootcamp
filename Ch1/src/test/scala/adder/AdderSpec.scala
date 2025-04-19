package adder

import chisel3._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.flatspec.AnyFlatSpec

class AdderSpec extends AnyFlatSpec {
	behavior of "Adder"
	it should "add two numbers" in {
		val testCases = Seq(
			(3.U, 5.U, 8.U),
			(0.U, 0.U, 0.U),
			(255.U, 1.U, 0.U), // Overflow case
		)

		simulate(new Adder) { dut =>
			testCases.foreach { case (a, b, e) => 
				dut.io.a.poke(a)
				dut.io.b.poke(b)
				dut.clock.step()
				dut.io.sum.expect(e)
				println(s"Testing: $a + $b = ${dut.io.sum.peek().litValue}")
			}
			println("All test cases passed!")
		}
	}
}