package singlecycle
import chisel3._
import org.scalatest._
import chiseltest._

class pctest extends FreeSpec with ChiselScalatestTester {
  "pc Test" in {
    test(new pc()) { a =>
      a.io. in.poke(0.U)
      a.clock.step(20)
     
    }
  }
}
