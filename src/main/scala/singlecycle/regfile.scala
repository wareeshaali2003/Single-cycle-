package singlecycle
import chisel3._ 
import chisel3.util._
class RegFileIO extends Bundle with Config {
// val RegWrite = Input ( Bool () )
val rs1 = Input ( UInt (5. W ) )
val rs2 = Input ( UInt (5. W ) )
val rd = Input(UInt(5.W))
val rdata1 = Output ( SInt ( WLEN . W ) )
val rdata2 = Output ( SInt ( WLEN . W ) )
val wen = Input ( Bool () )
val wdata = Input ( SInt ( WLEN . W ) )
}
trait Config_ {
  val WLEN = 32
  val REGFILE_LEN = 32 // Set to the desired number of registers
}



class RegFile extends Module with Config_ {
val io = IO (new RegFileIO )
val regs = Reg ( Vec ( REGFILE_LEN , SInt ( WLEN . W ) ) )

io.rdata1 := Mux (( io.rs1.orR ) , regs ( io.rs1 ) , 0. S ).asSInt
io.rdata2 := Mux (( io.rs2.orR ) , regs( io.rs2) , 0. S ).asSInt
when ( io.wen===1.B ) {
regs ( io.rd ) := io.wdata.asSInt
}
}