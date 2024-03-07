package singlecycle
import chisel3._ 
import chisel3.util._

class alucntr extends Module {
    val io = IO(new Bundle{
        val func3 = Input(UInt(3.W))
        val func7 = Input(UInt(1.W))
        val aluop= Input(UInt(3.W))
        val alucontrol = Output(UInt(5.W))
    })
   io.alucontrol := 0.U
    // rtype
when(io.aluop === "b000".U) {
    io.alucontrol := Cat("b0".U,io.func3, io.func7)
  }
//   itype
  .elsewhen(io.aluop === "b001".U) { 
     io.alucontrol := Cat("b00".U,io.func3)
  }
// stype
   .elsewhen(io.aluop === "b010".U) { 
     io.alucontrol := Cat("b00".U,io.func3)
  }
// sbtype
   .elsewhen(io.aluop === "b011".U) { 
     io.alucontrol := Cat("b00".U,io.func3)
  }
//   utype
     .elsewhen(io.aluop === "b100".U) { 
     io.alucontrol := Cat("b00000".U)
  }
//   utype
  //    .elsewhen(io.aluop === "b100".U) { 
  //    io.alucontrol := Cat("b00000".U)
  // }
//   ujtype
     .elsewhen(io.aluop === "b101".U) { 
     io.alucontrol := Cat("b00000".U)
  }
    }