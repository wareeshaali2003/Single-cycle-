// package singlecycle
// import chisel3._
// import org.scalatest._
// import chiseltest._

// class data_Memtest extends FreeSpec with ChiselScalatestTester{
//     "data_Mem Test" in { 
//         test(new data_Mem()){ c=>
//         c.io.addr.poke(1.S)
//         c.io. data.poke(1.S)
//         c.io.wen.poke(0.B)
//         c.io.ren.poke(1.B)
//         c.clock.step(1)
        
//         }
//     }
// }