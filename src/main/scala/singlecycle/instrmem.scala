package singlecycle
import chisel3._ 
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
class instr_Mem(val size : Int = 32 , val width : Int = 32) extends
Module {
val io = IO (new Bundle {
val addr = Input ( UInt ( 32 . W ) )
val output = Output(UInt(32.W))

})

val memory = Mem (1024 , UInt (32. W ) )

loadMemoryFromFile(memory,"D:/Scala-Chisel-Learning-Journey/src/main/scala/singlecycle/textfile.txt")

io.output := memory.read( io.addr)

}