package singlecycle
import chisel3._ 
import chisel3.util._
class data_Mem (val size : Int = 32 , val width : Int = 32) extends
Module {
val io = IO (new Bundle {
val addr = Input ( UInt ( width . W ) )
val data = Input ( SInt ( width . W ) )
val wen = Input ( Bool() )
val ren = Input ( Bool() )
val output = Output(SInt(32.W))

})
io.output:=0.S
val memory = Mem (32 , SInt (32. W ) )

 when(io.wen === 1.B) {
   memory.write ( io.addr.asUInt , io.data )
  }
  .elsewhen(io.ren === 1.B) {
           io.output := memory.read( io.addr.asUInt)
        }
.otherwise {
    // Provide a default assignment for io.output
    io.output := 0.S
  }

}