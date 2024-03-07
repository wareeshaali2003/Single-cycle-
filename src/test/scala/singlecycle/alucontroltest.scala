package singlecycle
import chisel3._
import org.scalatest._
import chiseltest._

class alucntrtest extends FreeSpec with ChiselScalatestTester{
    "alucntr Test" in { 
        test(new alucntr()){ c=>
        c.io.aluop.poke("b000".U)
        c.io.func3.poke("b001".U)
        c.io.func7.poke("b0".U)
        c.clock.step(1) } }
}