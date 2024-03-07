package singlecycle
import chisel3._ 
import chisel3.util._

class Top extends Module {
  val io = IO(new Bundle {
    val out = Output(SInt(32.W))
  })

  val pcmodule = Module(new pc)
  val alumodule = Module(new ALU)
  val alucontolmod = Module(new alucntr)
  val ctrnmod = Module(new control)
  val datamem_mod = Module(new data_Mem)
  val instrmem_mod = Module(new instr_Mem)
  val regfilemod = Module(new RegFile )
  val immgenmod = Module(new ImmdValGen1)
  val branchmod = Module(new BranchControl)
  val jalrmod = Module(new jalr )

  dontTouch(instrmem_mod.io)
  dontTouch(pcmodule.io)
  dontTouch(alumodule.io)
  dontTouch(alucontolmod.io)
  dontTouch(ctrnmod.io)
  dontTouch(datamem_mod.io)
  dontTouch(instrmem_mod.io)
  dontTouch(regfilemod.io)
  dontTouch(immgenmod.io)
  dontTouch(branchmod.io)
  dontTouch(jalrmod.io)

  // pc
  pcmodule.io.in := pcmodule.io.pc4

  // instruction
  instrmem_mod.io.addr := pcmodule.io.pc(11, 2)

  // control
  ctrnmod.io.instr := instrmem_mod.io.output(6, 0)

  // register file
  regfilemod.io.rs1 := instrmem_mod.io.output(19, 15)
  regfilemod.io.rs2 := instrmem_mod.io.output(24, 20)
  regfilemod.io.rd := instrmem_mod.io.output(11, 7)

  // alucontrol
  alucontolmod.io.func3 := instrmem_mod.io.output(14, 12)
  alucontolmod.io.func7 := instrmem_mod.io.output(30)

  // control read and write
  datamem_mod.io.wen := ctrnmod.io.memwrite
  datamem_mod.io.ren := ctrnmod.io.memread

  // regfile write
  regfilemod.io.wen := ctrnmod.io.regwrite
  
  

  // control to alu
  alucontolmod.io.aluop := ctrnmod.io.aluop

  // ALU
  

    // immgen I-type
   immgenmod.io.pc := pcmodule.io.pc
   immgenmod.io.instruction := instrmem_mod.io.output
  
// opA mux
alumodule.io.in_A := MuxCase(0.S, Array(
  (ctrnmod.io.opA === 0.U) -> regfilemod.io.rdata1,
  (ctrnmod.io.opA === 1.U) -> pcmodule.io.pc4.asSInt,
  (ctrnmod.io.opA === 2.U) -> pcmodule.io.pc.asSInt,
  (ctrnmod.io.opA === 3.U) -> regfilemod.io.rdata1
  
))

// branch

branchmod.io.arg_x := 0.S
branchmod.io.arg_y := 0.S

 branchmod.io.branch := ctrnmod.io.Branch
//  branch in_A
// opA mux
branchmod.io.arg_x := MuxCase(0.S, Array(
  (ctrnmod.io.opA === 0.U) -> regfilemod.io.rdata1,
  (ctrnmod.io.opA === 1.U) -> pcmodule.io.pc4.asSInt,
  (ctrnmod.io.opA === 2.U) -> pcmodule.io.pc.asSInt,
  (ctrnmod.io.opA === 3.U) -> regfilemod.io.rdata1
  
))

// branch func3 
branchmod.io.aluop := alucontolmod.io.alucontrol
// Immidiate mux in which there are three types  of instructions , Immidiate output go to MUX of register file input
dontTouch(immgenmod.io.i_imm) 
val immdate  = MuxCase(0.S, Array(
  (ctrnmod.io.extendsel === 0.U) -> immgenmod.io.i_imm,
  (ctrnmod.io.extendsel === 1.U) -> immgenmod.io.s_imm,
  (ctrnmod.io.extendsel === 2.U) -> immgenmod.io.u_imm,
))


// branch in_B
// opB
val mux1_alu = Mux (ctrnmod.io.opB, immdate, regfilemod.io.rdata2 )
     branchmod.io.arg_y:= mux1_alu
  

// opB mux
val mux2_alu = Mux (ctrnmod.io.opB, immdate, regfilemod.io.rdata2 )

    alumodule.io.in_B := mux2_alu
    alumodule.io.alu_Op := alucontolmod.io.alucontrol


// smallmux of branch 
val mux_br = Mux (branchmod.io.br_taken, immgenmod.io.sb_imm.asUInt , pcmodule.io.pc4 )

// main mux 
pcmodule.io.in := MuxCase(0.U, Array(
  (ctrnmod.io.next_pc_sel==="b00".U) -> pcmodule.io.pc4,
  (ctrnmod.io.next_pc_sel==="b01".U) -> mux_br,
  (ctrnmod.io.next_pc_sel==="b10".U) -> immgenmod.io.uj_imm.asUInt,
  (ctrnmod.io.next_pc_sel==="b11".U) -> jalrmod.io.out.asUInt
))

// jalr
jalrmod.io.imm := immdate
jalrmod.io.rs1 := regfilemod.io.rdata1 




// store
datamem_mod.io.data := regfilemod.io.rdata2
datamem_mod.io.addr:= (alumodule.io.out(9,2)).asUInt

// writeback mux
regfilemod.io.wdata := MuxCase(0.S, Array(
  (ctrnmod.io.memtoreg === 0.B) -> alumodule.io.out,
  (ctrnmod.io.memtoreg === 1.B) -> datamem_mod.io.output
))

io.out :=  DontCare
}