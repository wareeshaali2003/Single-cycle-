package singlecycle
import chisel3._
import org.scalatest._
import chiseltest._

class  RegFiletest extends FreeSpec with ChiselScalatestTester{
    " RegFile Test" in { 
        test(new  RegFile()){ c=>
        c.clock.step(18)
        // c.io.RegWrite.poke(1.B)
        
        c.io.rs1.poke(4.U)
        c.io.rs2.poke(4.U)
        c.io.rd.poke(4.U)
        c.io.wen.poke(1.B)
        c.io.wdata.poke(4.S)

        
        }
    }
}