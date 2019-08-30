
package dw_600;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.*;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;




public class Decodificador {
    
    Map<String, String> decodeMap;
    LinkedList<Map<String, String>> respuestas;
    LinkedList<?> paquetes;
	private Analizador analizador;
    

    void  iterar(){
        Crudo paquete; 

        paquete =  (Crudo)this.paquetes.poll();
        if(paquete != null){

            this.decodificar(paquete.cadena);
        }
        
        analizador.analizar();
        paquete = null;
    }
    
    public Decodificador (LinkedList<Map<String, String>> respuestas,LinkedList<?> paquetes,String patern, Analizador analizador){
        this.respuestas = respuestas;
        this.paquetes = paquetes;
        this.decodeMap = new HashMap<>();
        this.analizador = analizador;
        
        if(!patern.isEmpty()){
            cargarPatrones(patern);
        }else{
            cargarPatrones();
        }
    }
    
    private void asignarPatrones(String linea){
        
        List<String> items = Arrays.asList(linea.split("\\|"));
        this.decodeMap.put(((items.get(0)).replace("[", "")).replace("]", ""), items.get(1));
        
    }
    private void cargarPatrones(){
        this.asignarPatrones("[21]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR]");
        this.asignarPatrones("[22]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR],[D1:1:CHAR],[D2:1:CHAR],[D3:1:CHAR],[D4:1:CHAR],[D5:1:CHAR],[D6:1:CHAR],[D7:1:CHAR],[D8:1:CHAR],[D9:1:CHAR],[D10:1:CHAR]");
        this.asignarPatrones("[23]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR]");
        this.asignarPatrones("[24]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR]");
        this.asignarPatrones("[2a]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR],[D1:1:CHAR],[D2:1:CHAR],[D3:1:CHAR],[D4:1:CHAR],[D5:1:CHAR],[D6:1:CHAR],[D7:1:CHAR],[D8:1:CHAR],[D9:1:CHAR],[D10:1:CHAR],[ND:1:INT],[TYPE:1:BITARR],(%TYPE.7%==1&&%ND%>=1)[ATM_00:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=2)[ATM_01:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=3)[ATM_02:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=4)[ATM_03:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=5)[ATM_04:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=6)[ATM_05:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=7)[ATM_06:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=8)[ATM_07:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=9)[ATM_08:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=10)[ATM_09:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=11)[ATM_10:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=12)[ATM_11:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=13)[ATM_12:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=14)[ATM_13:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=15)[ATM_14:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=16)[ATM_15:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=17)[ATM_16:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=18)[ATM_17:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=19)[ATM_18:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=20)[ATM_19:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=21)[ATM_20:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=22)[ATM_21:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=23)[ATM_22:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=24)[ATM_23:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=25)[ATM_24:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=26)[ATM_25:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=27)[ATM_26:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=28)[ATM_27:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=29)[ATM_28:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=30)[ATM_29:{%TYPE.1%==0?2??4}:INT],(%TYPE.7%==1&&%ND%>=31)[ATM_30:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=1)[TELLER_00:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=2)[TELLER_01:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=3)[TELLER_02:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=4)[TELLER_03:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=5)[TELLER_04:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=6)[TELLER_05:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=7)[TELLER_06:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=8)[TELLER_07:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=9)[TELLER_08:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=10)[TELLER_09:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=11)[TELLER_10:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=12)[TELLER_11:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=13)[TELLER_12:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=14)[TELLER_13:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=15)[TELLER_14:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=16)[TELLER_15:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=17)[TELLER_16:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=18)[TELLER_17:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=19)[TELLER_18:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=20)[TELLER_19:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=21)[TELLER_20:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=22)[TELLER_21:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=23)[TELLER_22:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=24)[TELLER_23:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=25)[TELLER_24:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=26)[TELLER_25:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=27)[TELLER_26:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=28)[TELLER_27:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=29)[TELLER_28:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=30)[TELLER_29:{%TYPE.1%==0?2??4}:INT],(%TYPE.6%==1&&%ND%>=31)[TELLER_30:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=1)[UNFIT_00:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=2)[UNFIT_01:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=3)[UNFIT_02:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=4)[UNFIT_03:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=5)[UNFIT_04:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=6)[UNFIT_05:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=7)[UNFIT_06:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=8)[UNFIT_07:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=9)[UNFIT_08:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=10)[UNFIT_09:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=11)[UNFIT_10:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=12)[UNFIT_11:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=13)[UNFIT_12:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=14)[UNFIT_13:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=15)[UNFIT_14:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=16)[UNFIT_15:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=17)[UNFIT_16:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=18)[UNFIT_17:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=19)[UNFIT_18:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=20)[UNFIT_19:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=21)[UNFIT_20:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=22)[UNFIT_21:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=23)[UNFIT_22:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=24)[UNFIT_23:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=25)[UNFIT_24:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=26)[UNFIT_25:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=27)[UNFIT_26:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=28)[UNFIT_27:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=29)[UNFIT_28:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=30)[UNFIT_29:{%TYPE.1%==0?2??4}:INT],(%TYPE.5%==1&&%ND%>=31)[UNFIT_30:{%TYPE.1%==0?2??4}:INT]");
        this.asignarPatrones("[2b]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR],[D1:1:CHAR],[D2:1:CHAR],[D3:1:CHAR],[D4:1:CHAR],[D5:1:CHAR],[D6:1:CHAR],[D7:1:CHAR],[D8:1:CHAR],[D9:1:CHAR],[D10:1:CHAR],[ND:1:INT],(%ND%>=1)[DN_CY_000:1:INT],(%ND%>=1)[DN_NO_000:1:INT],(%ND%>=1)[DN_ID_000:1:INT],(%ND%>=1)[DN_VL_000:4:INT],(%ND%>=2)[DN_CY_001:1:INT],(%ND%>=2)[DN_NO_001:1:INT],(%ND%>=2)[DN_ID_001:1:INT],(%ND%>=2)[DN_VL_001:4:INT],(%ND%>=3)[DN_CY_002:1:INT],(%ND%>=3)[DN_NO_002:1:INT],(%ND%>=3)[DN_ID_002:1:INT],(%ND%>=3)[DN_VL_002:4:INT],(%ND%>=4)[DN_CY_003:1:INT],(%ND%>=4)[DN_NO_003:1:INT],(%ND%>=4)[DN_ID_003:1:INT],(%ND%>=4)[DN_VL_003:4:INT],(%ND%>=5)[DN_CY_004:1:INT],(%ND%>=5)[DN_NO_004:1:INT],(%ND%>=5)[DN_ID_004:1:INT],(%ND%>=5)[DN_VL_004:4:INT],(%ND%>=6)[DN_CY_005:1:INT],(%ND%>=6)[DN_NO_005:1:INT],(%ND%>=6)[DN_ID_005:1:INT],(%ND%>=6)[DN_VL_005:4:INT],(%ND%>=7)[DN_CY_006:1:INT],(%ND%>=7)[DN_NO_006:1:INT],(%ND%>=7)[DN_ID_006:1:INT],(%ND%>=7)[DN_VL_006:4:INT],(%ND%>=8)[DN_CY_007:1:INT],(%ND%>=8)[DN_NO_007:1:INT],(%ND%>=8)[DN_ID_007:1:INT],(%ND%>=8)[DN_VL_007:4:INT],(%ND%>=9)[DN_CY_008:1:INT],(%ND%>=9)[DN_NO_008:1:INT],(%ND%>=9)[DN_ID_008:1:INT],(%ND%>=9)[DN_VL_008:4:INT],(%ND%>=10)[DN_CY_009:1:INT],(%ND%>=10)[DN_NO_009:1:INT],(%ND%>=10)[DN_ID_009:1:INT],(%ND%>=10)[DN_VL_009:4:INT],(%ND%>=11)[DN_CY_010:1:INT],(%ND%>=11)[DN_NO_010:1:INT],(%ND%>=11)[DN_ID_010:1:INT],(%ND%>=11)[DN_VL_010:4:INT],(%ND%>=12)[DN_CY_011:1:INT],(%ND%>=12)[DN_NO_011:1:INT],(%ND%>=12)[DN_ID_011:1:INT],(%ND%>=12)[DN_VL_011:4:INT],(%ND%>=13)[DN_CY_012:1:INT],(%ND%>=13)[DN_NO_012:1:INT],(%ND%>=13)[DN_ID_012:1:INT],(%ND%>=13)[DN_VL_012:4:INT],(%ND%>=14)[DN_CY_013:1:INT],(%ND%>=14)[DN_NO_013:1:INT],(%ND%>=14)[DN_ID_013:1:INT],(%ND%>=14)[DN_VL_013:4:INT],(%ND%>=15)[DN_CY_014:1:INT],(%ND%>=15)[DN_NO_014:1:INT],(%ND%>=15)[DN_ID_014:1:INT],(%ND%>=15)[DN_VL_014:4:INT],(%ND%>=16)[DN_CY_015:1:INT],(%ND%>=16)[DN_NO_015:1:INT],(%ND%>=16)[DN_ID_015:1:INT],(%ND%>=16)[DN_VL_015:4:INT],(%ND%>=17)[DN_CY_016:1:INT],(%ND%>=17)[DN_NO_016:1:INT],(%ND%>=17)[DN_ID_016:1:INT],(%ND%>=17)[DN_VL_016:4:INT],(%ND%>=18)[DN_CY_017:1:INT],(%ND%>=18)[DN_NO_017:1:INT],(%ND%>=18)[DN_ID_017:1:INT],(%ND%>=18)[DN_VL_017:4:INT],(%ND%>=19)[DN_CY_018:1:INT],(%ND%>=19)[DN_NO_018:1:INT],(%ND%>=19)[DN_ID_018:1:INT],(%ND%>=19)[DN_VL_018:4:INT],(%ND%>=20)[DN_CY_019:1:INT],(%ND%>=20)[DN_NO_019:1:INT],(%ND%>=20)[DN_ID_019:1:INT],(%ND%>=20)[DN_VL_019:4:INT],(%ND%>=21)[DN_CY_020:1:INT],(%ND%>=21)[DN_NO_020:1:INT],(%ND%>=21)[DN_ID_020:1:INT],(%ND%>=21)[DN_VL_020:4:INT],(%ND%>=22)[DN_CY_021:1:INT],(%ND%>=22)[DN_NO_021:1:INT],(%ND%>=22)[DN_ID_021:1:INT],(%ND%>=22)[DN_VL_021:4:INT],(%ND%>=23)[DN_CY_022:1:INT],(%ND%>=23)[DN_NO_022:1:INT],(%ND%>=23)[DN_ID_022:1:INT],(%ND%>=23)[DN_VL_022:4:INT],(%ND%>=24)[DN_CY_023:1:INT],(%ND%>=24)[DN_NO_023:1:INT],(%ND%>=24)[DN_ID_023:1:INT],(%ND%>=24)[DN_VL_023:4:INT],(%ND%>=25)[DN_CY_024:1:INT],(%ND%>=25)[DN_NO_024:1:INT],(%ND%>=25)[DN_ID_024:1:INT],(%ND%>=25)[DN_VL_024:4:INT],(%ND%>=26)[DN_CY_025:1:INT],(%ND%>=26)[DN_NO_025:1:INT],(%ND%>=26)[DN_ID_025:1:INT],(%ND%>=26)[DN_VL_025:4:INT],(%ND%>=27)[DN_CY_026:1:INT],(%ND%>=27)[DN_NO_026:1:INT],(%ND%>=27)[DN_ID_026:1:INT],(%ND%>=27)[DN_VL_026:4:INT],(%ND%>=28)[DN_CY_027:1:INT],(%ND%>=28)[DN_NO_027:1:INT],(%ND%>=28)[DN_ID_027:1:INT],(%ND%>=28)[DN_VL_027:4:INT],(%ND%>=29)[DN_CY_028:1:INT],(%ND%>=29)[DN_NO_028:1:INT],(%ND%>=29)[DN_ID_028:1:INT],(%ND%>=29)[DN_VL_028:4:INT],(%ND%>=30)[DN_CY_029:1:INT],(%ND%>=30)[DN_NO_029:1:INT],(%ND%>=30)[DN_ID_029:1:INT],(%ND%>=30)[DN_VL_029:4:INT],(%ND%>=31)[DN_CY_030:1:INT],(%ND%>=31)[DN_NO_030:1:INT],(%ND%>=31)[DN_ID_030:1:INT],(%ND%>=31)[DN_VL_030:4:INT],(%ND%>=32)[DN_CY_031:1:INT],(%ND%>=32)[DN_NO_031:1:INT],(%ND%>=32)[DN_ID_031:1:INT],(%ND%>=32)[DN_VL_031:4:INT],(%ND%>=33)[DN_CY_032:1:INT],(%ND%>=33)[DN_NO_032:1:INT],(%ND%>=33)[DN_ID_032:1:INT],(%ND%>=33)[DN_VL_032:4:INT],(%ND%>=34)[DN_CY_033:1:INT],(%ND%>=34)[DN_NO_033:1:INT],(%ND%>=34)[DN_ID_033:1:INT],(%ND%>=34)[DN_VL_033:4:INT],(%ND%>=35)[DN_CY_034:1:INT],(%ND%>=35)[DN_NO_034:1:INT],(%ND%>=35)[DN_ID_034:1:INT],(%ND%>=35)[DN_VL_034:4:INT],(%ND%>=36)[DN_CY_035:1:INT],(%ND%>=36)[DN_NO_035:1:INT],(%ND%>=36)[DN_ID_035:1:INT],(%ND%>=36)[DN_VL_035:4:INT],(%ND%>=37)[DN_CY_036:1:INT],(%ND%>=37)[DN_NO_036:1:INT],(%ND%>=37)[DN_ID_036:1:INT],(%ND%>=37)[DN_VL_036:4:INT],(%ND%>=38)[DN_CY_037:1:INT],(%ND%>=38)[DN_NO_037:1:INT],(%ND%>=38)[DN_ID_037:1:INT],(%ND%>=38)[DN_VL_037:4:INT],(%ND%>=39)[DN_CY_038:1:INT],(%ND%>=39)[DN_NO_038:1:INT],(%ND%>=39)[DN_ID_038:1:INT],(%ND%>=39)[DN_VL_038:4:INT],(%ND%>=40)[DN_CY_039:1:INT],(%ND%>=40)[DN_NO_039:1:INT],(%ND%>=40)[DN_ID_039:1:INT],(%ND%>=40)[DN_VL_039:4:INT],(%ND%>=41)[DN_CY_040:1:INT],(%ND%>=41)[DN_NO_040:1:INT],(%ND%>=41)[DN_ID_040:1:INT],(%ND%>=41)[DN_VL_040:4:INT],(%ND%>=42)[DN_CY_041:1:INT],(%ND%>=42)[DN_NO_041:1:INT],(%ND%>=42)[DN_ID_041:1:INT],(%ND%>=42)[DN_VL_041:4:INT],(%ND%>=43)[DN_CY_042:1:INT],(%ND%>=43)[DN_NO_042:1:INT],(%ND%>=43)[DN_ID_042:1:INT],(%ND%>=43)[DN_VL_042:4:INT],(%ND%>=44)[DN_CY_043:1:INT],(%ND%>=44)[DN_NO_043:1:INT],(%ND%>=44)[DN_ID_043:1:INT],(%ND%>=44)[DN_VL_043:4:INT],(%ND%>=45)[DN_CY_044:1:INT],(%ND%>=45)[DN_NO_044:1:INT],(%ND%>=45)[DN_ID_044:1:INT],(%ND%>=45)[DN_VL_044:4:INT],(%ND%>=46)[DN_CY_045:1:INT],(%ND%>=46)[DN_NO_045:1:INT],(%ND%>=46)[DN_ID_045:1:INT],(%ND%>=46)[DN_VL_045:4:INT],(%ND%>=47)[DN_CY_046:1:INT],(%ND%>=47)[DN_NO_046:1:INT],(%ND%>=47)[DN_ID_046:1:INT],(%ND%>=47)[DN_VL_046:4:INT],(%ND%>=48)[DN_CY_047:1:INT],(%ND%>=48)[DN_NO_047:1:INT],(%ND%>=48)[DN_ID_047:1:INT],(%ND%>=48)[DN_VL_047:4:INT],(%ND%>=49)[DN_CY_048:1:INT],(%ND%>=49)[DN_NO_048:1:INT],(%ND%>=49)[DN_ID_048:1:INT],(%ND%>=49)[DN_VL_048:4:INT],(%ND%>=50)[DN_CY_049:1:INT],(%ND%>=50)[DN_NO_049:1:INT],(%ND%>=50)[DN_ID_049:1:INT],(%ND%>=50)[DN_VL_049:4:INT],(%ND%>=51)[DN_CY_050:1:INT],(%ND%>=51)[DN_NO_050:1:INT],(%ND%>=51)[DN_ID_050:1:INT],(%ND%>=51)[DN_VL_050:4:INT],(%ND%>=52)[DN_CY_051:1:INT],(%ND%>=52)[DN_NO_051:1:INT],(%ND%>=52)[DN_ID_051:1:INT],(%ND%>=52)[DN_VL_051:4:INT],(%ND%>=53)[DN_CY_052:1:INT],(%ND%>=53)[DN_NO_052:1:INT],(%ND%>=53)[DN_ID_052:1:INT],(%ND%>=53)[DN_VL_052:4:INT],(%ND%>=54)[DN_CY_053:1:INT],(%ND%>=54)[DN_NO_053:1:INT],(%ND%>=54)[DN_ID_053:1:INT],(%ND%>=54)[DN_VL_053:4:INT],(%ND%>=55)[DN_CY_054:1:INT],(%ND%>=55)[DN_NO_054:1:INT],(%ND%>=55)[DN_ID_054:1:INT],(%ND%>=55)[DN_VL_054:4:INT],(%ND%>=56)[DN_CY_055:1:INT],(%ND%>=56)[DN_NO_055:1:INT],(%ND%>=56)[DN_ID_055:1:INT],(%ND%>=56)[DN_VL_055:4:INT],(%ND%>=57)[DN_CY_056:1:INT],(%ND%>=57)[DN_NO_056:1:INT],(%ND%>=57)[DN_ID_056:1:INT],(%ND%>=57)[DN_VL_056:4:INT],(%ND%>=58)[DN_CY_057:1:INT],(%ND%>=58)[DN_NO_057:1:INT],(%ND%>=58)[DN_ID_057:1:INT],(%ND%>=58)[DN_VL_057:4:INT],(%ND%>=59)[DN_CY_058:1:INT],(%ND%>=59)[DN_NO_058:1:INT],(%ND%>=59)[DN_ID_058:1:INT],(%ND%>=59)[DN_VL_058:4:INT],(%ND%>=60)[DN_CY_059:1:INT],(%ND%>=60)[DN_NO_059:1:INT],(%ND%>=60)[DN_ID_059:1:INT],(%ND%>=60)[DN_VL_059:4:INT],(%ND%>=61)[DN_CY_060:1:INT],(%ND%>=61)[DN_NO_060:1:INT],(%ND%>=61)[DN_ID_060:1:INT],(%ND%>=61)[DN_VL_060:4:INT],(%ND%>=62)[DN_CY_061:1:INT],(%ND%>=62)[DN_NO_061:1:INT],(%ND%>=62)[DN_ID_061:1:INT],(%ND%>=62)[DN_VL_061:4:INT],(%ND%>=63)[DN_CY_062:1:INT],(%ND%>=63)[DN_NO_062:1:INT],(%ND%>=63)[DN_ID_062:1:INT],(%ND%>=63)[DN_VL_062:4:INT],(%ND%>=64)[DN_CY_063:1:INT],(%ND%>=64)[DN_NO_063:1:INT],(%ND%>=64)[DN_ID_063:1:INT],(%ND%>=64)[DN_VL_063:4:INT],(%ND%>=65)[DN_CY_064:1:INT],(%ND%>=65)[DN_NO_064:1:INT],(%ND%>=65)[DN_ID_064:1:INT],(%ND%>=65)[DN_VL_064:4:INT],(%ND%>=66)[DN_CY_065:1:INT],(%ND%>=66)[DN_NO_065:1:INT],(%ND%>=66)[DN_ID_065:1:INT],(%ND%>=66)[DN_VL_065:4:INT],(%ND%>=67)[DN_CY_066:1:INT],(%ND%>=67)[DN_NO_066:1:INT],(%ND%>=67)[DN_ID_066:1:INT],(%ND%>=67)[DN_VL_066:4:INT],(%ND%>=68)[DN_CY_067:1:INT],(%ND%>=68)[DN_NO_067:1:INT],(%ND%>=68)[DN_ID_067:1:INT],(%ND%>=68)[DN_VL_067:4:INT],(%ND%>=69)[DN_CY_068:1:INT],(%ND%>=69)[DN_NO_068:1:INT],(%ND%>=69)[DN_ID_068:1:INT],(%ND%>=69)[DN_VL_068:4:INT],(%ND%>=70)[DN_CY_069:1:INT],(%ND%>=70)[DN_NO_069:1:INT],(%ND%>=70)[DN_ID_069:1:INT],(%ND%>=70)[DN_VL_069:4:INT],(%ND%>=71)[DN_CY_070:1:INT],(%ND%>=71)[DN_NO_070:1:INT],(%ND%>=71)[DN_ID_070:1:INT],(%ND%>=71)[DN_VL_070:4:INT],(%ND%>=72)[DN_CY_071:1:INT],(%ND%>=72)[DN_NO_071:1:INT],(%ND%>=72)[DN_ID_071:1:INT],(%ND%>=72)[DN_VL_071:4:INT],(%ND%>=73)[DN_CY_072:1:INT],(%ND%>=73)[DN_NO_072:1:INT],(%ND%>=73)[DN_ID_072:1:INT],(%ND%>=73)[DN_VL_072:4:INT],(%ND%>=74)[DN_CY_073:1:INT],(%ND%>=74)[DN_NO_073:1:INT],(%ND%>=74)[DN_ID_073:1:INT],(%ND%>=74)[DN_VL_073:4:INT],(%ND%>=75)[DN_CY_074:1:INT],(%ND%>=75)[DN_NO_074:1:INT],(%ND%>=75)[DN_ID_074:1:INT],(%ND%>=75)[DN_VL_074:4:INT],(%ND%>=76)[DN_CY_075:1:INT],(%ND%>=76)[DN_NO_075:1:INT],(%ND%>=76)[DN_ID_075:1:INT],(%ND%>=76)[DN_VL_075:4:INT],(%ND%>=77)[DN_CY_076:1:INT],(%ND%>=77)[DN_NO_076:1:INT],(%ND%>=77)[DN_ID_076:1:INT],(%ND%>=77)[DN_VL_076:4:INT],(%ND%>=78)[DN_CY_077:1:INT],(%ND%>=78)[DN_NO_077:1:INT],(%ND%>=78)[DN_ID_077:1:INT],(%ND%>=78)[DN_VL_077:4:INT],(%ND%>=79)[DN_CY_078:1:INT],(%ND%>=79)[DN_NO_078:1:INT],(%ND%>=79)[DN_ID_078:1:INT],(%ND%>=79)[DN_VL_078:4:INT],(%ND%>=80)[DN_CY_079:1:INT],(%ND%>=80)[DN_NO_079:1:INT],(%ND%>=80)[DN_ID_079:1:INT],(%ND%>=80)[DN_VL_079:4:INT],(%ND%>=81)[DN_CY_080:1:INT],(%ND%>=81)[DN_NO_080:1:INT],(%ND%>=81)[DN_ID_080:1:INT],(%ND%>=81)[DN_VL_080:4:INT],(%ND%>=82)[DN_CY_081:1:INT],(%ND%>=82)[DN_NO_081:1:INT],(%ND%>=82)[DN_ID_081:1:INT],(%ND%>=82)[DN_VL_081:4:INT],(%ND%>=83)[DN_CY_082:1:INT],(%ND%>=83)[DN_NO_082:1:INT],(%ND%>=83)[DN_ID_082:1:INT],(%ND%>=83)[DN_VL_082:4:INT],(%ND%>=84)[DN_CY_083:1:INT],(%ND%>=84)[DN_NO_083:1:INT],(%ND%>=84)[DN_ID_083:1:INT],(%ND%>=84)[DN_VL_083:4:INT],(%ND%>=85)[DN_CY_084:1:INT],(%ND%>=85)[DN_NO_084:1:INT],(%ND%>=85)[DN_ID_084:1:INT],(%ND%>=85)[DN_VL_084:4:INT],(%ND%>=86)[DN_CY_085:1:INT],(%ND%>=86)[DN_NO_085:1:INT],(%ND%>=86)[DN_ID_085:1:INT],(%ND%>=86)[DN_VL_085:4:INT],(%ND%>=87)[DN_CY_086:1:INT],(%ND%>=87)[DN_NO_086:1:INT],(%ND%>=87)[DN_ID_086:1:INT],(%ND%>=87)[DN_VL_086:4:INT],(%ND%>=88)[DN_CY_087:1:INT],(%ND%>=88)[DN_NO_087:1:INT],(%ND%>=88)[DN_ID_087:1:INT],(%ND%>=88)[DN_VL_087:4:INT],(%ND%>=89)[DN_CY_088:1:INT],(%ND%>=89)[DN_NO_088:1:INT],(%ND%>=89)[DN_ID_088:1:INT],(%ND%>=89)[DN_VL_088:4:INT],(%ND%>=90)[DN_CY_089:1:INT],(%ND%>=90)[DN_NO_089:1:INT],(%ND%>=90)[DN_ID_089:1:INT],(%ND%>=90)[DN_VL_089:4:INT],(%ND%>=91)[DN_CY_090:1:INT],(%ND%>=91)[DN_NO_090:1:INT],(%ND%>=91)[DN_ID_090:1:INT],(%ND%>=91)[DN_VL_090:4:INT],(%ND%>=92)[DN_CY_091:1:INT],(%ND%>=92)[DN_NO_091:1:INT],(%ND%>=92)[DN_ID_091:1:INT],(%ND%>=92)[DN_VL_091:4:INT],(%ND%>=93)[DN_CY_092:1:INT],(%ND%>=93)[DN_NO_092:1:INT],(%ND%>=93)[DN_ID_092:1:INT],(%ND%>=93)[DN_VL_092:4:INT],(%ND%>=94)[DN_CY_093:1:INT],(%ND%>=94)[DN_NO_093:1:INT],(%ND%>=94)[DN_ID_093:1:INT],(%ND%>=94)[DN_VL_093:4:INT],(%ND%>=95)[DN_CY_094:1:INT],(%ND%>=95)[DN_NO_094:1:INT],(%ND%>=95)[DN_ID_094:1:INT],(%ND%>=95)[DN_VL_094:4:INT],(%ND%>=96)[DN_CY_095:1:INT],(%ND%>=96)[DN_NO_095:1:INT],(%ND%>=96)[DN_ID_095:1:INT],(%ND%>=96)[DN_VL_095:4:INT],(%ND%>=97)[DN_CY_096:1:INT],(%ND%>=97)[DN_NO_096:1:INT],(%ND%>=97)[DN_ID_096:1:INT],(%ND%>=97)[DN_VL_096:4:INT],(%ND%>=98)[DN_CY_097:1:INT],(%ND%>=98)[DN_NO_097:1:INT],(%ND%>=98)[DN_ID_097:1:INT],(%ND%>=98)[DN_VL_097:4:INT],(%ND%>=99)[DN_CY_098:1:INT],(%ND%>=99)[DN_NO_098:1:INT],(%ND%>=99)[DN_ID_098:1:INT],(%ND%>=99)[DN_VL_098:4:INT],(%ND%>=100)[DN_CY_099:1:INT],(%ND%>=100)[DN_NO_099:1:INT],(%ND%>=100)[DN_ID_099:1:INT],(%ND%>=100)[DN_VL_099:4:INT],(%ND%>=101)[DN_CY_100:1:INT],(%ND%>=101)[DN_NO_100:1:INT],(%ND%>=101)[DN_ID_100:1:INT],(%ND%>=101)[DN_VL_100:4:INT],(%ND%>=102)[DN_CY_101:1:INT],(%ND%>=102)[DN_NO_101:1:INT],(%ND%>=102)[DN_ID_101:1:INT],(%ND%>=102)[DN_VL_101:4:INT],(%ND%>=103)[DN_CY_102:1:INT],(%ND%>=103)[DN_NO_102:1:INT],(%ND%>=103)[DN_ID_102:1:INT],(%ND%>=103)[DN_VL_102:4:INT],(%ND%>=104)[DN_CY_103:1:INT],(%ND%>=104)[DN_NO_103:1:INT],(%ND%>=104)[DN_ID_103:1:INT],(%ND%>=104)[DN_VL_103:4:INT],(%ND%>=105)[DN_CY_104:1:INT],(%ND%>=105)[DN_NO_104:1:INT],(%ND%>=105)[DN_ID_104:1:INT],(%ND%>=105)[DN_VL_104:4:INT],(%ND%>=106)[DN_CY_105:1:INT],(%ND%>=106)[DN_NO_105:1:INT],(%ND%>=106)[DN_ID_105:1:INT],(%ND%>=106)[DN_VL_105:4:INT],(%ND%>=107)[DN_CY_106:1:INT],(%ND%>=107)[DN_NO_106:1:INT],(%ND%>=107)[DN_ID_106:1:INT],(%ND%>=107)[DN_VL_106:4:INT],(%ND%>=108)[DN_CY_107:1:INT],(%ND%>=108)[DN_NO_107:1:INT],(%ND%>=108)[DN_ID_107:1:INT],(%ND%>=108)[DN_VL_107:4:INT],(%ND%>=109)[DN_CY_108:1:INT],(%ND%>=109)[DN_NO_108:1:INT],(%ND%>=109)[DN_ID_108:1:INT],(%ND%>=109)[DN_VL_108:4:INT],(%ND%>=110)[DN_CY_109:1:INT],(%ND%>=110)[DN_NO_109:1:INT],(%ND%>=110)[DN_ID_109:1:INT],(%ND%>=110)[DN_VL_109:4:INT],(%ND%>=111)[DN_CY_110:1:INT],(%ND%>=111)[DN_NO_110:1:INT],(%ND%>=111)[DN_ID_110:1:INT],(%ND%>=111)[DN_VL_110:4:INT],(%ND%>=112)[DN_CY_111:1:INT],(%ND%>=112)[DN_NO_111:1:INT],(%ND%>=112)[DN_ID_111:1:INT],(%ND%>=112)[DN_VL_111:4:INT],(%ND%>=113)[DN_CY_112:1:INT],(%ND%>=113)[DN_NO_112:1:INT],(%ND%>=113)[DN_ID_112:1:INT],(%ND%>=113)[DN_VL_112:4:INT],(%ND%>=114)[DN_CY_113:1:INT],(%ND%>=114)[DN_NO_113:1:INT],(%ND%>=114)[DN_ID_113:1:INT],(%ND%>=114)[DN_VL_113:4:INT],(%ND%>=115)[DN_CY_114:1:INT],(%ND%>=115)[DN_NO_114:1:INT],(%ND%>=115)[DN_ID_114:1:INT],(%ND%>=115)[DN_VL_114:4:INT],(%ND%>=116)[DN_CY_115:1:INT],(%ND%>=116)[DN_NO_115:1:INT],(%ND%>=116)[DN_ID_115:1:INT],(%ND%>=116)[DN_VL_115:4:INT],(%ND%>=117)[DN_CY_116:1:INT],(%ND%>=117)[DN_NO_116:1:INT],(%ND%>=117)[DN_ID_116:1:INT],(%ND%>=117)[DN_VL_116:4:INT],(%ND%>=118)[DN_CY_117:1:INT],(%ND%>=118)[DN_NO_117:1:INT],(%ND%>=118)[DN_ID_117:1:INT],(%ND%>=118)[DN_VL_117:4:INT],(%ND%>=119)[DN_CY_118:1:INT],(%ND%>=119)[DN_NO_118:1:INT],(%ND%>=119)[DN_ID_118:1:INT],(%ND%>=119)[DN_VL_118:4:INT],(%ND%>=120)[DN_CY_119:1:INT],(%ND%>=120)[DN_NO_119:1:INT],(%ND%>=120)[DN_ID_119:1:INT],(%ND%>=120)[DN_VL_119:4:INT],(%ND%>=121)[DN_CY_120:1:INT],(%ND%>=121)[DN_NO_120:1:INT],(%ND%>=121)[DN_ID_120:1:INT],(%ND%>=121)[DN_VL_120:4:INT],(%ND%>=122)[DN_CY_121:1:INT],(%ND%>=122)[DN_NO_121:1:INT],(%ND%>=122)[DN_ID_121:1:INT],(%ND%>=122)[DN_VL_121:4:INT],(%ND%>=123)[DN_CY_122:1:INT],(%ND%>=123)[DN_NO_122:1:INT],(%ND%>=123)[DN_ID_122:1:INT],(%ND%>=123)[DN_VL_122:4:INT],(%ND%>=124)[DN_CY_123:1:INT],(%ND%>=124)[DN_NO_123:1:INT],(%ND%>=124)[DN_ID_123:1:INT],(%ND%>=124)[DN_VL_123:4:INT],(%ND%>=125)[DN_CY_124:1:INT],(%ND%>=125)[DN_NO_124:1:INT],(%ND%>=125)[DN_ID_124:1:INT],(%ND%>=125)[DN_VL_124:4:INT],(%ND%>=126)[DN_CY_125:1:INT],(%ND%>=126)[DN_NO_125:1:INT],(%ND%>=126)[DN_ID_125:1:INT],(%ND%>=126)[DN_VL_125:4:INT],(%ND%>=127)[DN_CY_126:1:INT],(%ND%>=127)[DN_NO_126:1:INT],(%ND%>=127)[DN_ID_126:1:INT],(%ND%>=127)[DN_VL_126:4:INT],(%ND%>=128)[DN_CY_127:1:INT],(%ND%>=128)[DN_NO_127:1:INT],(%ND%>=128)[DN_ID_127:1:INT],(%ND%>=128)[DN_VL_127:4:INT]");
        this.asignarPatrones("[2c]|[SR1:1:CHAR],[SR2:1:CHAR],[SR3:1:CHAR],[D1:1:CHAR],[D2:1:CHAR],[D3:1:CHAR],[D4:1:CHAR],[D5:1:CHAR],[D6:1:CHAR],[D7:1:CHAR],[D8:1:CHAR],[D9:1:CHAR],[D10:1:CHAR]");
    }    
    
    private void cargarPatrones(String patern){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
          final String dir = System.getProperty("user.dir");
           archivo = new File (dir+"/patern.dat");
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);


           String linea;
           while((linea=br.readLine())!=null)
               this.asignarPatrones(linea);
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{

           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
    
    }
    
    void decodificar(byte[] cadena){
        Map<String,String> datos = this.analizar(cadena);
        if(datos != null) {
        	this.respuestas.offer(datos);
        }	
    }
    
    Map<String, String> analizar(byte[] cadena){
        Map<String,String> datos = new HashMap<String, String>();
        Cadena c = new Cadena();

        
        c.cadena = Arrays.copyOfRange(cadena, 1, cadena.length);
        if(
                Integer.toHexString( cadena[0]).equals("2a")||
                Integer.toHexString( cadena[0]).equals("2b")
                
                ){
        datos.put("RECORD_TYPE", Integer.toHexString((int) cadena[0]));
        procesar_patron(datos,c, decodeMap.get(Integer.toHexString((int) cadena[0])));
//        System.out.println(String.format("%x", new BigInteger(1, cadena)));
        
        	return datos;

        }
		
        return null;
	}
    
    void procesar_patron(Map<String,String> datos,Cadena cadena,String pat){
        
        List<String> patArr = Arrays.asList( pat.split(","));
        patArr.forEach((temp) -> {
            if(cadena.cadena.length<1){return;}
            if(!temp.startsWith("[")){
            
                if(temp.startsWith("(")){
                    
                    temp = temp.substring(1);
                    List<String> tempArr = Arrays.asList( temp.split("\\)"));
                    
                    if(tempArr.get(0).contains("%")){
                        tempArr.set(0,reemplazarReferencia(datos, tempArr.get(0)));
                    }
                    try{
                    if(evaluar(tempArr.get(0))){
                        temp = tempArr.get(1);
                        procesar_item(datos,cadena, (temp.replace("[", "")).replace("]", ""),"");
                    }
                    }catch(Exception e){
                        System.out.println("Error procesar_patron");
                    }
                }
                
            }else{
                procesar_item(datos,cadena, (temp.replace("[", "")).replace("]", ""),"");
            }


	});
    }
    
    void procesar_item(Map<String,String> datos,Cadena cadena, String item,String iter){

        String n,l,t,v;

        List<String> tempArr = Arrays.asList( item.split(":"));
        
        for(Integer i = 0;i<3;i++){
            if(tempArr.get(i).contains("%")){
               tempArr.set(i,reemplazarReferencia(datos, tempArr.get(i)));
            }
            if(tempArr.get(i).contains("{")){
                try{
                    tempArr.set(i,evaluarExpresion( tempArr.get(i).replace("{", "").replace("}", "")));
                }catch(Exception e){
                    System.out.println("Error procesar_item");
                }
            }
        }

        n = tempArr.get(0);
        l = tempArr.get(1);
        t = tempArr.get(2);

        v = this.getVal(cadena, l,t);
        datos.put(n, v);

        
    }
    
    private String getVal(Cadena c,String l,String t){
        byte[] s;
        Integer p=1;
        byte[] bytes;
        String str;
        switch( t){
            case "CHAR":
            	s = Arrays.copyOfRange(c.cadena, 0, Integer.parseInt(l));
                c.cadena = Arrays.copyOfRange(c.cadena, Integer.parseInt(l), c.cadena.length);
                bytes = s;
                str="";
                for(Integer ii=0;ii<bytes.length;ii++){   
                   str+=  Bytes.ConvertByteAString(bytes[ii]);
                }
                return str;

            case "INT":
                s = Arrays.copyOfRange(c.cadena, 0, Integer.parseInt(l));
                c.cadena = Arrays.copyOfRange(c.cadena, Integer.parseInt(l), c.cadena.length);

                bytes = s;
                str="";

                if(bytes.length < 4) {
                  for (int i = 0; i < bytes.length; i++) {
                	str+=  Bytes.ConvertByteAString(bytes[i]);
                  }
                
                  p = Integer.parseUnsignedInt(str, 2);
                  
                  
                } else {
                	ByteBuffer buffer = ByteBuffer.wrap(bytes);
                    p = buffer.getInt();
                    
                    if(p > 2000) {
                    	System.out.println("error int WRAP");
                    }
                }
                
                return p.toString();
                
                

            case "BITARR":
                s = Arrays.copyOfRange(c.cadena, 0, Integer.parseInt(l));
                c.cadena = Arrays.copyOfRange(c.cadena, Integer.parseInt(l), c.cadena.length);
                bytes = s;
                str="";
                for(Integer ii=0;ii<bytes.length;ii++){
                   str+=  Bytes.ConvertByteAString(bytes[ii]);
                }
                return str;
        
        }
        return "";
    }
    
    
    String reemplazarReferencia(Map<String,String> datos,String referencia){
        
        String ref = "";
        String temp;
        String s = "";
        boolean in = false;
        
        temp = referencia;
        
        for (int i = 0, n = referencia.length(); i < n; i++) {
            if(!in){
                if(referencia. charAt(i) == '%'){
                    in = true;

                }
            }else{
 
                if(referencia. charAt(i)=='%'){
                    in = false;
                    if(ref.contains(".")){
                        List<String> tempArr = Arrays.asList( ref.split("\\."));

                        temp = temp.replace("%"+ref+"%", s +datos.get(tempArr.get(0)).charAt(Integer.parseInt(tempArr.get(1))));

                    }else{
                        temp = temp.replace("%"+ref+"%", datos.get(ref));
                    }
                    ref = "";
                }else{
                    ref +=referencia. charAt(i);                
                }
            }
        }

        return temp;
    }
    
    boolean evaluar(String e)throws ScriptException{
    
    
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");

                   return (boolean) engine.eval(e.replace("??", ":"));
    }

    String evaluarExpresion(String e)throws ScriptException{
    
    String r;
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
        e= e.replace("??", ":");
        
                   r=  engine.eval(e).toString();
                   return r;
    }
   
    
}

class Cadena{
    public byte[] cadena;
    
    public void Cadena(byte[] c){
        this.cadena = c;
    }
} 
