package singlecycle
import chisel3._
import org.scalatest._
import chiseltest._
class ALUtest extends FreeSpec with ChiselScalatestTester{
    "ALU test" in {
        test(new ALU){ c =>
        c.io.alu_Op.poke("b0000".U)
        c.io.in_A.poke(7.S)
        c.io.in_B.poke(2.S)
        c.clock.step(20)
        // c.io.output.expect(9.S)
       
        }
    }
}