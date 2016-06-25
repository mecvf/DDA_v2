/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Euge
 */
public class Apuesta {
    private int oid;
    private int monto;
    private JugadorRuleta jugador;
    private String num;
    private Numero numero;
    private Ronda ronda;
    private final Date fechaHora;
    private int montoGanado;

    // <editor-fold defaultstate="collapsed" desc="Constructor"> 

    public Apuesta(int monto, JugadorRuleta jugador, Numero numero,String num, Ronda ronda, Date fechaHora) {
        this.monto = monto;
        this.jugador = jugador;
        this.num=num;
        this.numero = numero;
        this.ronda = ronda;
        this.fechaHora = fechaHora;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters y setters">       
    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getMontoGanado() {
        return montoGanado;
    }

    public void setMontoGanado(int montoGanado) {
        this.montoGanado = montoGanado;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public Ronda getRonda() {
        return ronda;
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }
    
    public JugadorRuleta getJugador() {
        return jugador;
    }

    public void setJugador(JugadorRuleta jugador) {
        this.jugador = jugador;
    }

    public Numero getNumero() {
        return numero;
    }

    public void setNumero(Numero numero) {
        this.numero = numero;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos"> 
    public boolean validar() {
        return monto > 0 && jugador != null && numero != null; 
    }
    
    // </editor-fold>

    @Override
    public String toString() {
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
            return "Mesa: " + this.getRonda().getMesa().getNombre() + "; Fecha: " +
                    sdf.format(this.getFechaHora().getTime()) + "; Hora: " +
                    sdf1.format(this.getFechaHora().getTime())
                    + "; Numero: " + this.getNumero().getValor() + 
                    "; Num: "+this.getNum()+"Monto ganado: " + this.getMontoGanado();
    }
    
    
}