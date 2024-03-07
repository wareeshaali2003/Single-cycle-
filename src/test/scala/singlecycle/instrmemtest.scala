package singlecycle
import chisel3._
import org.scalatest._
import chiseltest._

class instr_Memtest extends FreeSpec with ChiselScalatestTester{
    "instr_Mem Test" in { 
        test(new instr_Mem()){ c=>{
        c.io.addr.poke(0.U)
        c.clock.step(1)
        }
        }
    }
}