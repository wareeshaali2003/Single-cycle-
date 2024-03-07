// package singlecycle
// import chisel3._
// import org.scalatest._
// import chiseltest._

// class controltest extends FreeSpec with ChiselScalatestTester{
//     "control Test" in { 
//         test(new control()){ c=>
//         c.io.instr.poke("b0110011".U)
//         c.clock.step(1)
//         c.io.memwrite.expect(0.B)
//         // c.io.Branch.expect(0.B)
//         c.io.memread.expect(0.B)
//         c.io.regwrite.expect(1.B)
//         c.io.memtoreg.expect(0.B)
//         c.io.opA.expect(0.S)
//         c.io.opB.expect(0.S)
//         // c.io.extendsel.expect("b00".U)
//         // c.io.next_pc_sel.expect("b00".U)
//         c.io.aluop.expect("b000".U)
//         }
//     }
// }